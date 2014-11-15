package labs.four.states;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import labs.four.Auction;
import labs.four.AuctionService;
import labs.four.converters.SimpleDateFormatConverter;

public class AuctionEditState extends AbstractState {


	public AuctionEditState(String username, AuctionService as, Auction[] results, Scanner scan) {
		super(username, as, results, scan);
	}

	public AuctionEditState(String username, AuctionService as, Auction[] results, InputStream is, OutputStream os) {
		super(username, as, results, is, os);
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
					edit(id);
					inputIsBad = false;
				} catch (NumberFormatException e){
					os.println("Invalid input, try again.");
				}
			}				
		}
		
		return new UserHomeState(this.getUsername(), this.getService(), this.scan);
	}
	
	private void edit(Long id){
		Auction a = this.getService().retrieve(id);
		if (a.getNumBids() <= 0){
			os.println("Edit the name of this item? Type a new name or hit enter to leave as-is.");
			String s = scan.nextLine();
			if (s.length() > 0)
				a.setName(s);
			Date d = null;
			do {
				os.println("Edit the end date of this item? Type a new end date or hit enter to leave as-is.");
				s = scan.nextLine();
				SimpleDateFormatConverter sdfc = new SimpleDateFormatConverter(new SimpleDateFormat(""));
				d = sdfc.parse(s);
				if (s.length() == 0){
					
				} else if (d == null){
					os.println("Invalid format, try again");
				} else {
					a.setTimeLeftInMillis(d.getTime() - System.currentTimeMillis());
				}
			} while (d == null && s.length() > 0);
			os.println("Edit the starting bid of this item? Type a new value or hit enter to leave as-is.");
			s = scan.nextLine();
			if (s.length() > 0)
				a.setCurrentBid(Integer.parseInt(s));
		
		} else {
			os.println("You may not edit the name, starting bid, or end-date of this item because people have already bid on it.");
		}
		os.println("Edit the description of this item? Type a new description or hit enter to leave as-is.");
		String s = scan.nextLine();
		if (s.length() > 0)
			a.setDescription(s);
		this.getService().update(a, id);
	}

	@Override
	public void show() {
		String results = this.getSearchResultsAsString();
		String s = String.format("%s, here are your editable items:\n%s", this.getUsername(), results);
		os.println(s + (results.equals("")?"You have no editable items.\nPress enter to return to the main menu":"Select the item you wish to edit by typing its id number, or hit enter to return to the main menu"));

	}

}
