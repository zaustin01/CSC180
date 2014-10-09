package exercisethree;

import static org.junit.Assert.*;

import org.junit.*;

public class AuctionServiceTest {
	
	private AuctionService inMem;
	
	@Before
	public void startup(){
		this.inMem = new InMemoryAuctionService();
	}
	
	@Test
	public void bidTest(){
		inMem.create(new Auction(0, "Toaster", 10));
		inMem.bid("Zack", 0);
		assertEquals("The currentBid on the Auction item with id = 0 will be 11 dollars", 11, inMem.retrieve(0).getCurrentBid());
		assertEquals("The startingBid on the Auction item with id = 0 will be 10 dollars", 10 , inMem.retrieve(0).getStartingBid());
	}
	
	@Test
	public void searchTest(){
		inMem.create(new Auction(0, "Toaster", 10));
		inMem.create(new Auction(1, "Lovely Toaster", 30));
		inMem.create(new Auction(2, "Peace, Love, Pi", 20));
		assertEquals(inMem.search(null).length, 0);
		assertEquals(inMem.search("").length, 3);
		assertEquals(inMem.search("Love").length, 2);
	}
	
	@Test
	public void createTest(){
		Auction a = new Auction(13, "Toaster", 10);
		
		//these wont be the same because the id of a is not what will be stored in the AuctionService
		assertNotSame(a, inMem.create(a));
	}
	
	@Test
	public void retrieveTest(){
		Auction a = inMem.create(new Auction(0, "Toaster", 10));
		assertSame("After creating an object I can recieve that same object", a, inMem.retrieve(0));
	}
	
	@Test
	public void updateTest(){
		inMem.create(new Auction(0, "Toaster", 10));
		Auction a1 = inMem.retrieve(0);
		inMem.update(new Auction(0, "Toaster Oven", 10), 0);
		Auction a2 = inMem.retrieve(0);
		
		assertNotSame("Even though both a1 and a2 were retrieved from index 0, they are not the same because the update succeeded", a1, a2);	
	}
	
	@Test (expected = ObjectNotFoundException.class)
	public void deleteTest(){
		inMem.create(new Auction(0, "Toaster", 10));
		inMem.delete(0);
		inMem.retrieve(0);
	}
}
