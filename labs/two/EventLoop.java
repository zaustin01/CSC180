package labs.two;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EventLoop {

	private Queue<State> active;
	private Queue<State> expired;
	private InMemoryAuctionService imas;
	private Scanner scan = new Scanner(System.in);

	public EventLoop() {

		this.imas = new InMemoryAuctionService();
		imas.create(new Auction(0, "Toaster", 20));
		imas.create(new Auction(1, "Lovely Toaster", 35));
		imas.create(new Auction(2, "Peace, Love, Pi", 30));
		this.active = new LinkedList<State>();
		this.expired = new LinkedList<State>();
	}

	
	public void begin() {
		
	active.offer(new DefaultState(imas, scan));
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
		if (!(newState instanceof DefaultState)){
			expired.offer(newState);
		}
		

	}

}

}
