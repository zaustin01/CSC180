package auctionlab;

import java.util.Scanner;

public class User implements Event{
	
	private String username;
	private State state;
	private AuctionService service;
	private Auction[] searchResults;
	
	public User(AuctionService service){
		this.state = State.DefaultState;
		this.service = service;
	}
	
	@Override
	public State next() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		if (input.length() == 0){
			
		} else if (this.state == State.DefaultState){
			this.setUsername(input);
			this.setState(State.UserHomeState);
		} else if(this.state == State.UserHomeState){
			this.setSearchResults(service.search(input));
			this.setState(State.SearchResultsState);
		} else {
			try{
				int itemId = Integer.parseInt(input);
				service.bid(getUsername(), itemId);
				this.setState(State.UserHomeState);
			} catch(NumberFormatException e){
				this.setSearchResults(service.search(input));
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		return this.getState();
	}

	@Override
	public void show() {
		String showString = null;
		String optionString = "Type your response, or hit enter to decline.";
		switch(this.state){
		case DefaultState:
			showString = "New User, would you Like to log in? " + optionString;
			break;
		case SearchResultsState:
			showString = this.getUsername() + ", here are your search results:\n" + this.getSearchResults() +
				"\n perform another search, or bid on an item by typing its Id number. " + optionString;
			break;
		case UserHomeState:
			showString = this.getUsername() + ", what would you like to search for? " + optionString;
			break;
		default:
			break;
		
		}
		System.out.println(showString);
		
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}


	public String getSearchResults() {
		StringBuilder results = new StringBuilder();
		for (Auction a : searchResults){
			results.append(a.toString());
			results.append("\n");
		}
		return results.toString();
	}

	public void setSearchResults(Auction[] searchResults) {
		this.searchResults = searchResults;
	}
	



}
