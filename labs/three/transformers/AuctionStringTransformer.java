package labs.three.transformers;

import labs.three.Auction;


public class AuctionStringTransformer implements Transformer<Auction, String> {

	@Override
	public String transform(Auction element) {
		return element.getName();
	}

}
