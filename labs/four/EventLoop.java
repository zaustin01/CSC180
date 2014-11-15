package labs.four;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import labs.four.states.DefaultState;
import labs.four.states.State;
import labs.four.states.UserHomeState;

public class EventLoop {

	private Queue<State> active;
	private Queue<State> expired;
	private AuctionService as;
	private Scanner scan = new Scanner(System.in);

	public EventLoop() {
		File f = new File("AuctionItemListHTML.txt");
		try {
			//this.as = new InMemoryAuctionService(f);//old
			this.as = new DatasourceAuctionService(f);//new
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.active = new LinkedList<State>();
		this.expired = new LinkedList<State>();
	}

	public void begin() {
		active.offer(new DefaultState(as, scan));
		while (true) {
			if (active.isEmpty()) {
				State newTurn = new DefaultState(as, scan);
				newTurn.show();
				State newUser = newTurn.next();
				if (newUser instanceof UserHomeState)
					expired.offer(newUser);
				Queue<State> temp = expired;
				expired = active;
				active = temp;
			}
			State currentState = active.poll();
			currentState.show();
			State newState = currentState.next();
			if (!(newState instanceof DefaultState)) {
				expired.offer(newState);
			}

		}

	}

}
