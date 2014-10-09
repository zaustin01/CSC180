package exercisethree;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;


public class AuctionTest {
	
	@Test
	public void constructorTest(){
		Object o;
		o = new Auction(0, "Toaster", 0);
		assertTrue("After calling the constructor for an Auction item, our object is an instance of Auction", (o instanceof Auction));
		o = new Auction(0, "Toaster", 0, new HashMap<String, Object>());
		assertTrue("The 0ther constructor also works", (o instanceof Auction));
		
	}
	
	@Test
	public void getPropertyTest(){
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("Height", new Integer(10));
		Auction a = new Auction(0, "Toaster", 0, props);
		assertEquals(a.getProperty("Height"), 10);
	}
	
	
	

}
