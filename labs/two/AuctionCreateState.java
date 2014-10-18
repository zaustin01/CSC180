package labs.two;

import java.util.Scanner;

public class AuctionCreateState extends SuperState {

	public AuctionCreateState(AuctionService service, Scanner scan, String username) {
		super(service, scan);
		this.setUsername(username);
	}

	@Override
	public State next() {
		return new UserHomeState(this.getUsername(), this.getService(), scan);
	}

	@Override
	public void show() {
		System.out.println(this.getUsername() + ", let's create a new item.");
		String name = null;
		while(name == null || name.length() == 0){
		System.out.println("Enter the name of your new auction item:");
		name = scan.nextLine();
		}
		String description;
		System.out.println("Enter an optional description for your item:");
		description = scan.nextLine();
		int startingBid = 1;
		System.out.println("Enter the starting bid for this item, or hit enter to default to $1.");
		String bidString = scan.nextLine();
		try{
			startingBid = Integer.parseInt(bidString);
		} catch (NumberFormatException ex){
			System.out.println("Defaulting to $1.");
		}
		this.getService().create(new Auction(1, name, description, startingBid));
		System.out.println("Item created!");
	}

}
