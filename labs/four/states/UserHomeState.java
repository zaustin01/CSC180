package labs.four.states;

import java.util.Scanner;

import labs.four.AuctionService;

public class UserHomeState extends AbstractState {
	
	private int menuSelection = 0;
	
	public UserHomeState(String username, AuctionService service, Scanner scan) {
		super(service, scan);
		this.setUsername(username);
	}

	@Override
	public State next() {
		State returnState;
		switch (menuSelection){
		case 0:
			returnState = this;
			break;
		case 1:
			System.out.println(this.getUsername() + ", what would you like to search for?");
			String input = scan.nextLine();
			this.setSearchResults(this.getService().search(input));
			returnState = new SearchResultsState(this.getUsername(), this.getService(), this.getSearchResults(), this.scan);
			break;
		case 2:
			returnState = new AuctionCreateState(this.getUsername(), this.getService(), this.scan);
			break;
		case 3:
			StringBuilder sb = new StringBuilder("EDITABLE");
			sb.append(this.getUsername());
			this.setSearchResults(this.getService().search(sb.toString()));
			returnState = new AuctionEditState(this.getUsername(), this.getService(), this.getSearchResults(), this.scan);
			break;
		case 4:
			StringBuilder sb2 = new StringBuilder("EDITABLE");
			sb2.append(this.getUsername());
			this.setSearchResults(this.getService().search(sb2.toString()));
			returnState = new AuctionDeleteState(this.getUsername(), this.getService(), this.getSearchResults(), this.scan);
			break;
		default:
			returnState = this;
			break;
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
			System.out.println("3) Edit an item");
			System.out.println("4) Delete an item");
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
