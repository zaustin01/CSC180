package labs.two;

import java.util.Scanner;

public abstract class SuperState implements State {

	private String username;
	private AuctionService service;
	private Auction[] searchResults;
	protected Scanner scan;
	
	public SuperState(AuctionService service, Scanner scan){
		this.setService(service);
		this.scan = scan;
	}
	
	public SuperState(String username, AuctionService as, Auction[] results, Scanner scan){
		this.setUsername(username);
		this.setService(as);
		this.setSearchResults(results);
		this.scan = scan;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Auction[] getSearchResults() {
		return searchResults;
	}
	
	public String getSearchResultsAsString(){
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

	public AuctionService getService() {
		return service;
	}

	public void setService(AuctionService service) {
		this.service = service;
	}

}
