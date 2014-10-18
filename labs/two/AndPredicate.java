package labs.two;

public class AndPredicate implements Predicate<Auction> {

	private Predicate<Auction> predicateOne;
	private Predicate<Auction> predicateTwo;
	
	public AndPredicate(Predicate<Auction> arg1, Predicate<Auction> arg2){
		predicateOne = arg1;
		predicateTwo = arg2;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return (predicateOne.evaluate(t) && predicateTwo.evaluate(t));
	}
	
	public Predicate<Auction> getPredicateOne() {
		return predicateOne;
	}
	public void setPredicateOne(Predicate<Auction> predicateOne) {
		this.predicateOne = predicateOne;
	}
	public Predicate<Auction> getPredicateTwo() {
		return predicateTwo;
	}
	public void setPredicate2(Predicate<Auction> predicateTwo) {
		this.predicateTwo = predicateTwo;
	}
	
	@Override
	public String toString(){
		return "(" + predicateOne.toString() + " AND " + predicateTwo.toString() + ")";
	}

}
