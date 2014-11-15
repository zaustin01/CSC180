package labs.four;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Scanner;
import java.util.Stack;

import labs.four.exceptions.ExpiredBidException;
import labs.four.exceptions.IdMismatchException;
import labs.four.exceptions.ObjectNotFoundException;
import labs.four.predicates.AndPredicate;
import labs.four.predicates.ContainsPredicate;
import labs.four.predicates.EditablePredicate;
import labs.four.predicates.ExpiredPredicate;
import labs.four.predicates.OrPredicate;
import labs.four.predicates.Predicate;

public class DatasourceAuctionService implements AuctionService {

	private FileBasedDatasource userItems = new FileBasedDatasource("auction.dat", false);
	private FileBasedDatasource ebayItems = new FileBasedDatasource("ebayAuction.dat", true);
	private Long idIncrementer = 0l;
	
	public DatasourceAuctionService(){
		
	}
	
	public DatasourceAuctionService(File file) throws FileNotFoundException{
		InputStream is = new FileInputStream(file);
		Scanner scan = new Scanner(is);
		StringBuilder sb = new StringBuilder();
		while (scan.hasNextLine()){
			String next = scan.nextLine();
			next = next.replaceAll("[\\t\\r\\n]", "");
			sb.append(next);
			sb.append('\n');
		}
		String fileAsText = sb.toString();
		String regex = "<li id=.* _sp=.* listingid=.* class=.* r=.*>";
		String[] itemsFromFile = fileAsText.split(regex);
		for (String string : itemsFromFile){
			try {
				Auction a = parseAuction(string);
				ebayItems.put(a.getId(), a);
			} catch (Exception e){
				
			}
		}
		scan.close();
	}
	
	private Auction parseAuction(String string){
		string = string.replaceAll("\\n", " ");
		String temp = string.replaceFirst(".*iid=\\\"(\\d+)\\\"\\s?.*", "$1");
		long id = Long.parseLong(temp);
		temp = string.replaceFirst(".*<a .+ class=\\\"vip\\\".*>(.+)</a>\\s?.*", "$1");
		String name = temp;
		temp = string.replaceFirst(".*<span class=\\\"g-b\\\">\\s*(\\$[\\d\\,]+\\.\\d{2})</span>\\s?.*", "$1");
		temp = temp.replaceAll("\\,", "");
		temp = temp.replaceFirst("\\s*\\$(\\d+)\\.\\d+", "$1");
		int startingbid = Integer.parseInt(temp);
		Auction a = new Auction(id, name, startingbid);
		temp = string.replaceFirst(".*<span>\\s*(\\d+) bids</span>\\s?.*", "$1");
		if (temp.length() < 5)
			a.setNumBids(Integer.parseInt(temp));
		temp = string.replaceFirst(".*timems=\\\"(\\d+)\\\"\\s?.*", "$1");
		try {
			long timeLeft = Long.parseLong(temp) - System.currentTimeMillis();
			a.setTimeLeftInMillis(timeLeft);
		} catch (NumberFormatException e) {
			//If it fails, don't do anything so timeLeft remains default
		}
		return a;
	}

	@Override
	public Auction[] search(String criteria) {
		Predicate<Auction> predicate = null;
		if (criteria == null){
			return new Auction[0];
		}else if (criteria.startsWith("EDITABLE")){
			predicate = new EditablePredicate(criteria.substring(8));
		}else{
			String[] words = criteria.split(" ");
			if (words.length == 0 || words.length == 1){
				predicate = new ContainsPredicate(criteria);
			} else {
				predicate = generatePredicate(words);
			}
		}
		predicate = new ExpiredPredicate(predicate);
		Collection<Auction> matches = CollectionUtils.filter(userItems.values(), predicate);
		Collection<Auction> ebayMatches = CollectionUtils.filter(ebayItems.values(), predicate);
		Auction[] toReturn = matches.toArray(new Auction[matches.size() + ebayMatches.size()]);
		for (int i = matches.size(); i < toReturn.length; i++){
			toReturn[i] = ebayMatches.toArray(new Auction[ebayMatches.size()])[i-matches.size()];
		}
		return toReturn;
		
	}

	private Predicate<Auction> generatePredicate(String[] criteria){
		Stack<String> operators = new Stack<String>();
		Stack<Predicate<Auction>> predicates = new Stack<Predicate<Auction>>();
		for (int i = 0; i < criteria.length; i++){
			if (isLogicalOperator(criteria[i])){
				if (isLogicalOr(criteria[i]) && !operators.isEmpty() && !isLogicalOr(operators.peek())){
					operators.pop();
					Predicate<Auction> newAnd = new AndPredicate(predicates.pop(), predicates.pop());
					predicates.push(newAnd);
					i--;
				} else {
					operators.push(criteria[i]);
				}
			} else {
				predicates.push(new ContainsPredicate(criteria[i].toUpperCase()));
			}
		}
		while(!operators.isEmpty()){
			if (isLogicalOr(operators.peek())){
				operators.pop();
				Predicate<Auction> newOr = new OrPredicate(predicates.pop(), predicates.pop());
				predicates.push(newOr);
			} else {
				operators.pop();
				Predicate<Auction> newAnd = new AndPredicate(predicates.pop(), predicates.pop());
				predicates.push(newAnd);
			}
		}
		return predicates.pop();
	}
	
	private boolean isLogicalOperator(String s){
		return s.toUpperCase().equals("AND") || s.toUpperCase().equals("OR");
	}
	
	private boolean isLogicalOr(String s){
		return s.toUpperCase().equals("OR");
	}
	
	@Override
	public void bid(String username, Long itemId) {
		if (username == null || itemId == null)
			throw new IllegalArgumentException();
		boolean itemFound = false;
		for (Auction item : userItems.values()) {
			if (item.getId() == itemId) {
				if (item.getTimeLeftInMillis() < 0)
					throw new ExpiredBidException();
				item.bid(username);
				update(item, itemId);
				itemFound = true;
			}
		}
		if (!itemFound){
			for (Auction item : ebayItems.values()) {
				if (item.getId() == itemId) {
					if (item.getTimeLeftInMillis() < 0)
						throw new ExpiredBidException();
					item.bid(username);
					update(item, itemId);
					itemFound = true;
				}
			}
		}

		if (!itemFound)
			throw new ObjectNotFoundException("No such object.");

	}

	@Override
	public Auction create(Auction auction) {
		while (userItems.containsKey(idIncrementer)){
			idIncrementer++;
		}
		Auction auction2 = new Auction(idIncrementer, auction.getCreator(), auction.getName(), auction.getDescription(), auction.getCurrentBid(), auction.getTimeLeftInMillis(), auction.getProperties());
		userItems.put(idIncrementer, auction2);
		idIncrementer++;
		return auction2;
	}

	@Override
	public Auction retrieve(Long id) {
		if (userItems.get(id) == null)
			throw new ObjectNotFoundException();
		return userItems.get(id);
	}

	@Override
	public Auction update(Auction auction, Long id) {
		if (auction.getId() != id)
			throw new IdMismatchException();
		if (userItems.containsKey(id))
			userItems.put(id, auction);
		if (ebayItems.containsKey(id))
			ebayItems.put(id, auction);
		return auction;
	}

	@Override
	public void delete(Long id) {
		if (userItems.containsKey(id))
			userItems.remove(id);
		if (ebayItems.containsKey(id))
			ebayItems.remove(id);

	}

}
