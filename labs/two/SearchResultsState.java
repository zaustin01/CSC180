package labs.two;

import java.util.Scanner;

public class SearchResultsState extends SuperState {


	public SearchResultsState(String username, AuctionService as,
			Auction[] search, Scanner scanner) {
		super(as, scanner);
		this.setUsername(username);
		this.setSearchResults(search);
	}

	@Override
	public State next() {
		State returnState;
		String input = scan.nextLine();
		if (input.length() == 0){
			returnState = new UserHomeState(this.getUsername(), this.getService(), this.scan);
		} else {
			try{
				int itemId = Integer.parseInt(input);
				this.getService().bid(this.getUsername(), itemId);
				returnState = new UserHomeState(this.getUsername(), this.getService(), this.scan);
				System.out.println("Bid successful!");
			} catch(NumberFormatException e){
				this.setSearchResults(this.getService().search(input));
				returnState = this;
			} catch (Exception ex){
				ex.printStackTrace(System.out);
				returnState = new DefaultState(this.getService(), this.scan);
			}
		}
		return returnState;
	}

	@Override
	public void show() {
		System.out.println(this.getUsername() + ", here are your search results:\n" + this.getSearchResultsAsString() +
				"\nperform another search, or bid on an item by typing its Id number. Type your response, or hit enter to decline.");

	}
	
	@Override
	public boolean equals(Object o){
		boolean isEqual = false;
		if (o instanceof SearchResultsState){
			isEqual = this.getUsername() == ((SearchResultsState) o).getUsername();
		}
		return isEqual;
	}
	
	@Override
	public int hashCode(){
		return this.getUsername().hashCode();
	}

}
