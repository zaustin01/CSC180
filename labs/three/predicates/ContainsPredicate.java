package labs.three.predicates;

import labs.three.Auction;


public class ContainsPredicate implements Predicate<Auction> {

	private String criteria;
	
	public ContainsPredicate(String criteria){
		this.setCriteria(criteria.toUpperCase());
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return (t.getName().toUpperCase().contains(getCriteria()) || t.getDescription().toUpperCase().contains(getCriteria()));
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString(){
		return criteria;
	}
}
