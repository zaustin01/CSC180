package labs.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import labs.three.states.DefaultState;
import labs.three.states.State;
import labs.three.states.UserHomeState;

public class EventLoop {

	private Queue<State> active;
	private Queue<State> expired;
	private InMemoryAuctionService imas;
	private Scanner scan = new Scanner(System.in);

	public EventLoop() {
		File f = new File("AuctionItemListHTML.txt");
		try {
			this.imas = new InMemoryAuctionService(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.active = new LinkedList<State>();
		this.expired = new LinkedList<State>();
	}

	public void begin() {
		active.offer(new DefaultState(imas, scan));
		while (true) {
			if (active.isEmpty()) {
				State newTurn = new DefaultState(imas, scan);
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
