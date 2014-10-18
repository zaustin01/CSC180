package labs.two;

import java.util.Scanner;

public class DefaultState extends SuperState {

	public DefaultState(AuctionService service, Scanner scan) {
		super(service, scan);
	}

	@Override
	public State next() {
		State returnState;
		String input = scan.nextLine();
		if (input.length() == 0){
			returnState = this;
		} else {
			this.setUsername(input);
			returnState = new UserHomeState(this.getUsername(), this.getService(), this.scan);
		}
		return returnState;
	}
	
	@Override
	public void show() {
		System.out.println("New User, would you Like to log in? Type your response, or hit enter to decline.");

	}
}
