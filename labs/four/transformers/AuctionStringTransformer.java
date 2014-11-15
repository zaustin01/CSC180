package labs.four.transformers;

import labs.four.Auction;


public class AuctionStringTransformer implements Transformer<Auction, String> {

	@Override
	public String transform(Auction element) {
		return element.getName();
	}

}
