package labs.four.states;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import labs.four.Auction;
import labs.four.AuctionService;

public class SearchResultsState extends AbstractState {


	public SearchResultsState(String username, AuctionService as, Auction[] search, Scanner scanner) {
		super(username, as, search, scanner);
	}
	
	public SearchResultsState(String username, AuctionService as, Auction[] search, InputStream is, OutputStream os) {
		super(username, as, search, is, os);
	}

	@Override
	public State next() {
		State returnState;
		String input = scan.nextLine();
		if (input.length() == 0){
			returnState = new UserHomeState(this.getUsername(), this.getService(), this.scan);
		} else {
			try{
				long itemId = Long.parseLong(input);
				this.getService().bid(this.getUsername(), itemId);
				returnState = new UserHomeState(this.getUsername(), this.getService(), this.scan);
				os.println("Bid successful!");
			} catch(NumberFormatException e){
				this.setSearchResults(this.getService().search(input));
				returnState = this;
			} catch (Exception ex){
				ex.printStackTrace(os);
				returnState = new DefaultState(this.getService(), this.scan);
			}
		}
		return returnState;
	}

	@Override
	public void show() {
		String results = (this.getSearchResultsAsString().equals(""))? "Your search returned no results\n" : this.getSearchResultsAsString();
		os.println(this.getUsername() + ", here are your search results:\n" + results +
				"perform another search, or bid on an item by typing its Id number. Type your response, or hit enter to decline.");

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
