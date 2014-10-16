package auctionlab;

import java.util.Scanner;

public class UserHomeState extends SuperState {
	
	
	public UserHomeState(String username, AuctionService service, Scanner scan) {
		super(service, scan);
		this.setUsername(username);
	}

	@Override
	public State next() {
		State returnState;
		String input = scan.nextLine();
		if (input.length() == 0){
			returnState = this;
		} else {
			this.setSearchResults(this.getService().search(input));
			returnState = new SearchResultsState(this.getUsername(), this.getService(), this.getSearchResults(), this.scan);
		}
		return returnState;
	}

	@Override
	public void show() {
		System.out.println(this.getUsername() + ", what would you like to search for? Type your response, or hit enter to decline.");

	}
	
	@Override
	public boolean equals(Object o){
		boolean isEqual = false;
		if (o instanceof UserHomeState){
			isEqual = this.getUsername() == ((UserHomeState) o).getUsername();
		}
		return isEqual;
	}
	
	@Override
	public int hashCode(){
		return this.getUsername().hashCode();
	}
	
}
