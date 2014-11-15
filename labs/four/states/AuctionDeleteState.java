package labs.four.states;

import java.util.Scanner;

import labs.four.Auction;
import labs.four.AuctionService;

public class AuctionDeleteState extends AbstractState {

	public AuctionDeleteState(String username, AuctionService service, Auction[] searchResults, Scanner scan) {
		super(username, service, searchResults, scan);
	}

	@Override
	public State next() {
		boolean inputIsBad = true;
		while (inputIsBad){
			String input = scan.nextLine();
			Long id = null;
			if (input.length() == 0){
				inputIsBad = false;
			} else {
				try{
					id = Long.parseLong(input);
					if (this.getService().retrieve(id).getNumBids() <= 0){
						this.getService().delete(id);
					} else {
						os.println("You can not delete this item, because people have bid on it. Returning to the main menu");
					}
					inputIsBad = false;
				} catch (NumberFormatException e){
					os.println("Invalid input, try again.");
				}
			}				
		}
		
		return new UserHomeState(this.getUsername(), this.getService(), this.scan);
	}

	@Override
	public void show() {
		String results = this.getSearchResultsAsString();
		String s = String.format("%s, here are your items that may be deleted:\n%s", this.getUsername(), results);
		os.println(s + (results.equals("")?"You have no items that may be deleted.\nPress enter to return to the main menu":"Select the item you wish to delete by typing its id number, or hit enter to return to the main menu"));


	}

}
