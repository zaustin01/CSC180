package labs.two;

import java.util.Scanner;

public class UserHomeState extends SuperState {
	
	private int menuSelection = 0;
	
	public UserHomeState(String username, AuctionService service, Scanner scan) {
		super(service, scan);
		this.setUsername(username);
	}

	@Override
	public State next() {
		State returnState;
		if (menuSelection == 0){
			returnState = this;
		} else if (menuSelection == 1){
			System.out.println(this.getUsername() + ", what would you like to search for?");
			String input = scan.nextLine();
			this.setSearchResults(this.getService().search(input));
			returnState = new SearchResultsState(this.getUsername(), this.getService(), this.getSearchResults(), this.scan);
		} else{
			returnState = new AuctionCreateState(this.getService(), scan, this.getUsername());
		}
		return returnState;
	}

	@Override
	public void show() {
		boolean inputIsNotValid = true;
		while(inputIsNotValid){
			System.out.println(this.getUsername() + ", select the number of the action you wish to perform:");
			System.out.println("0) Skip turn (you can also hit enter to skip turn)");
			System.out.println("1) Search for an item");
			System.out.println("2) Create a new item");
			try{
				String s = scan.nextLine();
				if (s.length() != 0){
					menuSelection = Integer.parseInt(s);
				}
				inputIsNotValid = false;
			} catch (NumberFormatException e){
				inputIsNotValid = true;
			}
		}
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
