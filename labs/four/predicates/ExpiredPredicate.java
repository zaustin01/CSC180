package labs.four.predicates;

import labs.four.Auction;

public class ExpiredPredicate implements Predicate<Auction> {

	private Predicate<Auction> predicate;
	public ExpiredPredicate(Predicate<Auction> p){
		predicate = p;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return predicate.evaluate(t) && t.getTimeLeftInMillis() > 0;
	}

}
