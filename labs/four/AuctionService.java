package labs.four;

import labs.four.exceptions.IdMismatchException;
import labs.four.exceptions.ObjectNotFoundException;

public interface AuctionService {
	
	/**
	 * examples, given an internal collection of Auction items ["Toaster", "Lovely Toaster", and "Peace, Love, Pi"]
	 * -- search(null) -> []
	 * -- search("") -> [ Auction("Toaster"), Auction("Lovely Toaster"), Auction("Peace, Love, Pi") ]
	 * -- search("asdf") -> []
	 * -- search("Toaster") -> [ Auction("Toaster"), Auction("Lovely Toaster") ]
	 * -- search("Love") -> [ Auction("Lovely Toaster"), Auction("Peace, Love, Pi") ]
	 * @param criteria - - if null, will be ignored
	 * @return an array that is the size of the number of results found; contains results found
	 */
	Auction[] search(String criteria);
	
	/**
	 * Increases the current bid amount for item #number by $1. examples, given an internal collection of Auction items [ (1234, $15), (2345, $16), (3456, $20) ]
	 * -- bid("dave", 1234) -> Auction(1234, $16, "dave")
	 * -- bid(null, 1234) -> throws IllegalArgumentException
	 * -- bid("dave", null) -> throws IllegalArgumentException
	 * -- bid("bill", 2345) followed by bid("john", 2345) -> Auction(2345, $18)
	 * -- bid("bill", 4567) -> throws ObjectNotFoundException
	 * @param username - must be non-null
	 * @param itemId - must be valid auction item number
	 * @throws java.lang.IllegalArgumentException - if username or number are null
	 * @throws ObjectNotFoundException - if number is invalid
	 */
	void bid(String username, Long itemId);
	
	/**
	 * Creates auction item. Note that the id in the Auction passed in is ignored. The Auction returned has the new id.
	 * @param auction
	 * @return The auction item created
	 */
	Auction create(Auction auction);
	
	/**
	 * Retrieves the auction item of id id. If the item does not exist, this method will throw an ObjectNotFoundException
	 * @param id 
	 * @return The auction item requested
	 * @throws ObjectNotFoundException - if the object cannot be found
	 */
	Auction retrieve(Long id);
	
	/**
	 * Updates an auction item of id id. If auction.getId != id then this method will throw an IdMismatchException
	 * @param auction
	 * @param id
	 * @return - The updated auction
	 * @throws IdMismatchException - if the auction id and the id passed are not the same
	 */
	Auction update(Auction auction, Long id);
	
	/**
	 * Removes auction item of id id. If item does not exist, this method has no side-effects.
	 * @param id
	 */
	void delete(Long id);
}
