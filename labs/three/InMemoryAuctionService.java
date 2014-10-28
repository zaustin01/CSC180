package labs.three;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import labs.three.exceptions.ExpiredBidException;
import labs.three.exceptions.IdMismatchException;
import labs.three.exceptions.ObjectNotFoundException;
import labs.three.predicates.AndPredicate;
import labs.three.predicates.ContainsPredicate;
import labs.three.predicates.ExpiredPredicate;
import labs.three.predicates.OrPredicate;
import labs.three.predicates.Predicate;


public class InMemoryAuctionService implements AuctionService {

	private Map<Long, Auction> items = new HashMap<Long, Auction>();
	private Long idIncrementer = 0l;
	public InMemoryAuctionService(){
		
	}
	
	public InMemoryAuctionService(File file) throws FileNotFoundException{
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
				items.put(a.getId(), a);
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
		if (criteria == null)
			return new Auction[0];
		String[] words = criteria.split(" ");
		Predicate<Auction> predicate = null;
		if (words.length == 0 || words.length == 1){
			predicate = new ContainsPredicate(criteria);
		} else {
			predicate = generatePredicate(words);
		}
		predicate = new ExpiredPredicate(predicate);
		Collection<Auction> matches = CollectionUtils.filter(items.values(), predicate);
		return matches.toArray(new Auction[matches.size()]);
		
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
		for (Auction item : items.values()) {
			if (item.getId() == itemId) {
				if (item.getTimeLeftInMillis() < 0)
					throw new ExpiredBidException();
				item.setCurrentBid(item.getCurrentBid() + 1);
				item.setOwner(username);
				itemFound = true;
			}
		}

		if (!itemFound)
			throw new ObjectNotFoundException("No such object.");

	}

	@Override
	public Auction create(Auction auction) {
		while (items.containsKey(idIncrementer)){
			idIncrementer++;
		}
		Auction auction2 = new Auction(idIncrementer, auction.getName(), auction.getDescription(), auction.getCurrentBid(), auction.getTimeLeftInMillis(), auction.getProperties());
		items.put(idIncrementer, auction2);
		idIncrementer++;
		return auction2;
	}

	@Override
	public Auction retrieve(Long id) {
		if (items.get(id) == null)
			throw new ObjectNotFoundException();
		return items.get(id);
	}

	@Override
	public Auction update(Auction auction, Long id) {
		if (auction.getId() != id)
			throw new IdMismatchException();
		items.put(id, auction);
		return auction;
	}

	@Override
	public void delete(Long id) {
		items.remove(id);

	}

}
