package exercisetwo;

public class Tester {

	public static void main(String[] args) {
		InMemoryAuctionService inMem = new InMemoryAuctionService();
		
		//A successful search for a toaster
		displaySearchResults(inMem.search("Toaster"));
		//A successful search for the item that contains the letters "Use"
		displaySearchResults(inMem.search("Use"));
		//A search that returns no results
		displaySearchResults(inMem.search("Oven"));
		//Changing the name of "Used car" to "Slightly Used Car"
		inMem.getItems()[2].setName("Slightly Used Car");
		displaySearchResults(inMem.search("Use"));
		//Placing a bid on the Refrigerator
		inMem.bid("zack", 2222);
		displaySearchResults(inMem.search("Ref"));
		//Failing to change the name of the refrigerator, as it already has an owner
		inMem.getItems()[1].setName("Oven");
		displaySearchResults(inMem.search("Ref"));
		displaySearchResults(inMem.search("Oven"));
	}
	private static void displaySearchResults(Auction[] items){
		for (Auction item : items){
			System.out.println(item);
		}
		if (items.length == 0){
			System.out.println("There were no items matching your search\n");
		}
	}

}
