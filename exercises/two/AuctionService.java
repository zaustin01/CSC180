package exercisetwo;

public interface AuctionService {
	
	//Searches all Auction items and returns the items that match the given search criteria.  If none match, return an empty array.
	Auction[] search(String criteria);
	
	//Adds one dollar to currentBid for item itemIdand changes the owner to username.  If the itemId is incorrect, then ignore it.
	void bid(String username, int itemId);
}
