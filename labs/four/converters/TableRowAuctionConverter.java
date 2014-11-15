package labs.four.converters;

import java.text.SimpleDateFormat;

import labs.four.Auction;


public class TableRowAuctionConverter implements Converter<Auction> {

	
	private SimpleDateFormatConverter sdfc = new SimpleDateFormatConverter(null, new SimpleDateFormat("EE MMM dd @ hha"));
	public final String tableHeader;
	
	public TableRowAuctionConverter(){
		StringBuilder sb = new StringBuilder();
		sb.append(xCharString("ID", 14));
		sb.append(xCharString("Name", 92));
		sb.append(xCharString("Current Bid", 12));
		sb.append(xCharString("Bids", 5));
		sb.append(xCharString("Owner", 10));
		sb.append(xCharString("Ends By" ,17));
		tableHeader = sb.toString();
	}
	
	@Override
	public Auction parse(String fromString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(Auction fromT) {
		
		StringBuilder auctionString = new StringBuilder();
		auctionString.append(xCharString("" + fromT.getId(), 14));
		auctionString.append(xCharString(fromT.getName(), 92));
		auctionString.append(xCharString("$" + fromT.getCurrentBid(), 12));
		auctionString.append(xCharString("" + fromT.getNumBids(), 5));
		auctionString.append(xCharString(fromT.getOwner(), 10));
		auctionString.append(xCharString(sdfc.format(fromT.endsBy()) ,17));
		return auctionString.toString();
	}
	
	
	/**
	 * Converts the given string into a formatted string with x number of characters
	 * @param string -  the string to convert
	 * @param x - the length of the returned string
	 * @return a formatted String
	 */
	private String xCharString(String string, int x){
		string = string == null ? "-" : string;
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
