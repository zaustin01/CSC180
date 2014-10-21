package mafia;

import labs.two.Auction;

public class TableRowAuctionConverter implements Converter<Auction> {

	@Override
	public Auction parse(String fromString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(Auction fromT) {
		StringBuilder auctionString = new StringBuilder("|");
		auctionString.append(xCharString("" + fromT.getId(), 5));
		auctionString.append('|');
		auctionString.append(xCharString(fromT.getName(), 10));
		auctionString.append('|');
		auctionString.append(xCharString(fromT.getDescription(), 20));
		auctionString.append('|');
		auctionString.append(xCharString("$" + fromT.getCurrentBid(), 10));
		auctionString.append('|');
		auctionString.append(xCharString(fromT.getOwner(), 10));
		auctionString.append('|');
		return auctionString.toString();
	}
	
	/**
	 * Converts the given string into a formatted string with x number of characters
	 * @param string -  the string to convert
	 * @param x - the length of the returned string
	 * @return a formatted String
	 */
	private String xCharString(String string, int x){
		string = string == null ? "" : string;
		StringBuilder sb = new StringBuilder();
		if (string.length() < x || string.length() == x){
			sb.append(string);
			for (int i = 0; i < (x - string.length());i++){
				sb.append(' ');
			}
		} else {
			sb.append(string.substring(0, x-3));
			sb.append("...");
		}
		return sb.toString();
	}

}
