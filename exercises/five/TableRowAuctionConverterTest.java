package mafia;

import static org.junit.Assert.*;
import labs.two.Auction;

import org.junit.Test;

public class TableRowAuctionConverterTest {

	TableRowAuctionConverter trac = new TableRowAuctionConverter();
	
	@Test
	public void testParse() {
		
	}

	@Test
	public void testFormat() {
		Auction a = new Auction(1, "Item1", "A cool item!", 10);
		a.setOwner("Zack");
		Auction b = new Auction(1234, "Item1234", "An even cooler item", 20);
		Auction c = new Auction(1337, "leetItem", "The coolest item you have ever seen ever", 42);
		c.setOwner("Mr. Norris");
		assertTrue(trac.format(a).length() == trac.format(b).length());
		assertTrue(trac.format(a).length() == trac.format(c).length());
	}

}
