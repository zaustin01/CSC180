package auctionlab;

import java.util.LinkedList;
import java.util.Queue;

public class EventLoop {

	private Queue<Event> active;
	private Queue<Event> expired;
	private InMemoryAuctionService imas;

	public EventLoop() {

		this.imas = new InMemoryAuctionService();
		imas.create(new Auction(0, "Toaster", 20));
		imas.create(new Auction(1, "Lovely Toaster", 35));
		imas.create(new Auction(2, "Peace, Love, Pi", 30));
		this.active = new LinkedList<Event>();
		active.offer(new User(imas));
		this.expired = new LinkedList<Event>();
	}

	public void begin() {
		while (true) {
			Event e = active.poll();
			if (e == null) {
				Queue<Event> temp = expired;
				expired = active;
				active = temp;
				e = active.poll();
			}
			e.show();
			State s = e.next();
			if (s != State.DefaultState && expired.isEmpty()){
				expired.offer(new User(imas));
			}
			expired.offer(e);

		}

	}

}
