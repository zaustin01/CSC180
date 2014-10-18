package labs.two;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class InMemoryAuctionService implements AuctionService {

	private Map<Integer, Auction> items = new HashMap<Integer, Auction>();
	private int idIncrementer = 0;

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
				predicates.push(new ContainsPredicate(criteria[i]));
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
	public void bid(String username, Integer itemId) {
		if (username == null || itemId == null)
			throw new IllegalArgumentException();
		boolean itemFound = false;
		for (Auction item : items.values()) {
			if (item.getId() == itemId) {
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
		Auction auction2 = new Auction(idIncrementer, auction.getName(), auction.getCurrentBid());
		items.put(new Integer(idIncrementer), auction2);
		idIncrementer++;
		return auction2;
	}

	@Override
	public Auction retrieve(Integer id) {
		if (items.get(id) == null)
			throw new ObjectNotFoundException();
		return items.get(id);
	}

	@Override
	public Auction update(Auction auction, Integer id) {
		if (auction.getId() != id)
			throw new IdMismatchException();
		items.put(id, auction);
		return auction;
	}

	@Override
	public void delete(Integer id) {
		items.remove(id);

	}

}
