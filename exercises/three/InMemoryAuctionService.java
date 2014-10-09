package exercisethree;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuctionService implements AuctionService {

	private Map<Integer, Auction> items = new HashMap<Integer, Auction>();
	private int idIncrementer = 0;

	@Override
	public Auction[] search(String criteria) {
		if (criteria == null)
			return new Auction[0];
		int count = 0;
		for (Auction item : items.values()) {
			if (item.getName().contains(criteria)) {
				count++;
			}
		}
		Auction[] matches = new Auction[count];
		int next = 0;
		for (Auction item : items.values()) {
			if (item.getName().contains(criteria)) {
				matches[next] = item;
				next++;
			}
		}
		return matches;
	}

	@Override
	public void bid(String username, Integer itemId) {
		if (username == null || itemId == null)
			throw new IllegalArgumentException();
		boolean itemFound = false;
		for (Auction item : items.values()) {
			if (item.getId() == itemId) {
				item.setCurrentBid(item.getCurrentBid() + 1);
				item.setOwner(username);
				itemFound = true;
			}
		}

		if (!itemFound)
			throw new ObjectNotFoundException();

	}

	@Override
	public Auction create(Auction auction) {
		Auction auction2 = new Auction(idIncrementer, auction.getName(), auction.getCurrentBid());
		items.put(new Integer(idIncrementer), auction2);
		idIncrementer++;
		return auction2;
	}

	@Override
	public Auction retrieve(Integer id) {
		if (items.get(id) == null)
			throw new ObjectNotFoundException();
		return items.get(id);
	}

	@Override
	public Auction update(Auction auction, Integer id) {
		if (auction.getId() != id)
			throw new IdMismatchException();
		items.put(id, auction);
		return auction;
	}

	@Override
	public void delete(Integer id) {
		items.remove(id);

	}

}
