package labs.three.states;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import labs.three.Auction;
import labs.three.AuctionService;
import labs.three.converters.SimpleDateFormatConverter;

public class AuctionCreateState extends AbstractState {

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
		boolean done = false;
		Long timeLeftInMillis = null;
		Calendar c = null;
		Date date = null;
		System.out.println("On which date does this Auction end? Hit enter to default to one week from now.");
		while (!done){
			String dateString = scan.nextLine();
			if (dateString.equals("")){
				done = true;
			} else {
				SimpleDateFormatConverter sdfc = new SimpleDateFormatConverter(new SimpleDateFormat("MM/dd/yyyy"));
				date = sdfc.parse(dateString);
				if (date == null){
					sdfc = new SimpleDateFormatConverter(new SimpleDateFormat("MM-dd-yyyy"));
					date = sdfc.parse(dateString);
					if (date == null){
						System.out.println("Invalid format, try again.");
					} else {
						done = true;
					}
				} else {
					done = true;
				}
			}	
		}
		done = false;
		if (date != null){
			int t = 0;
			System.out.println("At what time does this Auction end? Use the 12-hour clock, and indicate AM or PM with an 'a' or a 'p', respectively.");
			while (!done){
				String time = scan.nextLine();
				String subst = null;
				if (time.length() == 2)
					subst = time.substring(0,1);
				else if (time.length() > 2)
					subst = time.substring(0,2);
				try{
					t = Integer.parseInt(subst);
					if (time.charAt(1) == 'p')
						t += 12;
					done = true;
				} catch (Exception e) {
					System.out.println("Invalid format, try again.");
				}
				
			}
			c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, t);
			timeLeftInMillis = c.getTimeInMillis() - System.currentTimeMillis();
		}
		if (timeLeftInMillis != null)
			this.getService().create(new Auction(1l, name, description, startingBid, timeLeftInMillis));
		else 
			this.getService().create(new Auction(1l, name, description, startingBid));
		System.out.println("Item created!");
	}

}
