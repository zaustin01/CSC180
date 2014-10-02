package exercisetwo;

public class InMemoryAuctionService implements AuctionService{
	
	private Auction[] items = {new Auction(1111, "Toaster", 40.0), new Auction(2222, "Refrigerator", 400.0), new Auction(3333, "Used Car", 4000.0)};

	public Auction[] getItems() {
		return items;
	}

	public void setItems(Auction[] items) {
		this.items = items;
	}

	@Override
	public Auction[] search(String criteria) {
		int count = 0;
		for (Auction item : items){
			if (item.getName().contains(criteria)){
				count++;
			}
		}
		Auction[] matches = new Auction[count];
		int next = 0;
		for (Auction item : items){
			if (item.getName().contains(criteria)){
				matches[next] = item;
				next++;
			}
		}
		return matches;
	}

	@Override
	public void bid(String username, int itemId) {
		for (Auction item : items){
			if (item.getId() == itemId){
				item.setCurrentBid(item.getCurrentBid() + 1);
				item.setOwner(username);
			}
		}
		
	}

}
