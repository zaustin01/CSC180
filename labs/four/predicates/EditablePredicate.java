package labs.four.predicates;

import labs.four.Auction;

public class EditablePredicate implements Predicate<Auction> {

	private String username;
	
	public EditablePredicate(String username){
		this.username = username;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return t.getCreator().equals(username);
	}
	
	@Override
	public String toString(){
		return "Editable by " + username + "?";
	}

}
