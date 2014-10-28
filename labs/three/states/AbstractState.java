package labs.three.states;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import labs.three.Auction;
import labs.three.AuctionService;
import labs.three.converters.TableRowAuctionConverter;

public abstract class AbstractState implements State {

	private String username;
	private AuctionService service;
	private Auction[] searchResults;
	protected Scanner scan;
	protected PrintStream os;
	
	public AbstractState(AuctionService service, Scanner scan){
		this.setService(service);
		this.scan = scan;
		this.os = System.out;
	}
	
	public AbstractState(AuctionService service, InputStream is, OutputStream os){
		this.setService(service);
		this.scan = new Scanner(is);
		this.os = new PrintStream(os);
	}
	
	public AbstractState(String username, AuctionService as, Auction[] results, Scanner scan){
		this.setUsername(username);
		this.setService(as);
		this.setSearchResults(results);
		this.scan = scan;
		this.os = System.out;
	}
	
	public AbstractState(String username, AuctionService as, Auction[] results, InputStream is, OutputStream os){
		this.setUsername(username);
		this.setService(as);
		this.setSearchResults(results);
		this.scan = new Scanner(is);
		this.os = new PrintStream(os);
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
		TableRowAuctionConverter trac = new TableRowAuctionConverter();
		results.append(trac.tableHeader);
		results.append('\n');
		for (Auction a : searchResults){
			results.append(trac.format(a));
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
