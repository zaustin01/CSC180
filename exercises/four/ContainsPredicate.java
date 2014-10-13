package exercisefour;

import exercisethree.Auction;

public class ContainsPredicate implements Predicate<Auction> {

	private String criteria;
	
	public ContainsPredicate(String criteria){
		this.setCriteria(criteria);
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return t.getName().contains(getCriteria());
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

}
