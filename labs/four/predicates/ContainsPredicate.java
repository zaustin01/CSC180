package labs.four.predicates;

import labs.four.Auction;


public class ContainsPredicate implements Predicate<Auction> {

	private String criteria;
	
	public ContainsPredicate(String criteria){
		this.criteria = criteria.toUpperCase();
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return (t.getName().toUpperCase().contains(criteria) || t.getDescription().toUpperCase().contains(criteria));
	}


	@Override
	public String toString(){
		return criteria;
	}
}
