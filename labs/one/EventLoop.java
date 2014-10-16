package auctionlab;

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
//		State startup = new DefaultState(imas, scan);
//		while(startup instanceof DefaultState){
//			startup.show();
//			startup = startup.next();
//		}
//		active.offer(startup);
//		active.offer(new DefaultState(imas, scan));
		while (true) {
			State currentState = active.poll();
			if (currentState == null) {
				Queue<State> temp = expired;
				expired = active;
				active = temp;
				currentState = active.poll();
			}
			currentState.show();
			State newState = currentState.next();
			if (expired.isEmpty() && !(newState instanceof DefaultState)){
				expired.offer(new DefaultState(imas, scan));
			}
			expired.offer(newState);

		}

	}

}
