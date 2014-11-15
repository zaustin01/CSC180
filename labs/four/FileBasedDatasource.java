package labs.four;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;

public class FileBasedDatasource {
	
	private boolean idIndependant;
	private static final String DELETED = "$$tombstone$$";
	private static final int ID = 13;
	private static final int CREATOR = 20;
	private static final int NAME = 90;
	private static final int DESCRIPTION = 200;
	private static final int BID = 12;
	private static final int OWNER = 20;
	private static final int NUMBIDS = 5;
	private static final int TIMELEFT = 13;
	private static final int AUCTION_LENGTH = ID + CREATOR + NAME + DESCRIPTION + BID*2 + OWNER + NUMBIDS + TIMELEFT;
	
	private RandomAccessFile raf;
	private Padding pad;
	
	public FileBasedDatasource(String filePath, boolean idIndependant){
		this.idIndependant = idIndependant;
		pad = new Padding();
		try {
			raf = new RandomAccessFile(filePath, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void put(Long id, Auction a){
		byte[] data = convertAuctionToPaddedString(a).getBytes();
		try {
			if (idIndependant){
				if (!containsKey(id)){
					raf.seek(raf.length());
					raf.write(data);
				}
			} else {
				raf.seek(id*AUCTION_LENGTH);
				raf.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private String convertAuctionToPaddedString(Auction a){
		StringBuilder sb = new StringBuilder();
		sb.append(pad.pad(a.getId(), ID));
		sb.append(pad.pad(a.getCreator(), CREATOR));
		sb.append(pad.pad(a.getName(), NAME));
		sb.append(pad.pad(a.getDescription(), DESCRIPTION));
		sb.append(pad.pad(a.getStartingBid(), BID));
		sb.append(pad.pad(a.getCurrentBid(), BID));
		sb.append(pad.pad(a.getOwner(), OWNER));
		sb.append(pad.pad(a.getNumBids(), NUMBIDS));
		sb.append(pad.pad(a.getTimeLeftInMillis(), TIMELEFT));
		return sb.toString();
	}
	
	public Auction get(Long id){
		try {
			if (idIndependant){
				Collection<Auction> items = values();
				for (Auction a : items){
					if (a != null && a.getId() == id){
						return a;
					}
				}
			} else {
				raf.seek(id*AUCTION_LENGTH);
				byte[] b = new byte[AUCTION_LENGTH];
				raf.read(b);
				Auction a = convertByteArrayToAuction(b);
				return a;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private  Auction convertByteArrayToAuction(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes){
			if (b != 0)
				sb.append((char)b);
		}
		String s = sb.toString();
		if (s.startsWith(String.valueOf((char)31)) || s.substring(0, DELETED.length()).equals(DELETED))
			return null;
		Long id = pad.unpad(s.substring(0, ID), Long.class);
		String creator = pad.unpad(s.substring(ID, ID + CREATOR), String.class);
		String name = pad.unpad(s.substring(ID + CREATOR, ID + CREATOR +NAME), String.class);
		String description = pad.unpad(s.substring(ID + CREATOR + NAME, ID + CREATOR + NAME + DESCRIPTION), String.class);
		Integer currentbid = pad.unpad(s.substring(ID + CREATOR + NAME + DESCRIPTION, ID + CREATOR + NAME + DESCRIPTION + BID), Integer.class);
		Integer startingbid = pad.unpad(s.substring(ID + CREATOR + NAME + DESCRIPTION + BID, ID + CREATOR + NAME + DESCRIPTION + BID + BID), Integer.class);
		String owner = pad.unpad(s.substring(ID + CREATOR + NAME + DESCRIPTION + BID + BID, ID + CREATOR + NAME + DESCRIPTION + BID + BID + OWNER), String.class);
		Integer numbids = pad.unpad(s.substring(ID + CREATOR + NAME + DESCRIPTION + BID + BID + OWNER, ID + CREATOR + NAME + DESCRIPTION + BID + BID + OWNER + NUMBIDS), Integer.class);
		Long timeleft = pad.unpad(s.substring(ID + CREATOR + NAME + DESCRIPTION + BID + BID + OWNER + NUMBIDS), Long.class);
		return new Auction(id, creator, name, description, currentbid, startingbid, owner, numbids, timeleft);
	}

	public void remove(Long id){
		try {
			if (idIndependant){
				int incrementer = 0;
				Collection<Auction> items = values();
				for (Auction a : items){
					if (a != null && a.getId() == id){
						raf.seek(incrementer*AUCTION_LENGTH);
						String s = pad.pad(DELETED, AUCTION_LENGTH);
						raf.writeChars(s);
					}
					incrementer++;
				}
			} else {
				raf.seek(id*AUCTION_LENGTH);
				String s = pad.pad(DELETED, AUCTION_LENGTH);
				raf.writeChars(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Auction> values(){
		Collection<Auction> toReturn = new ArrayList<>();
		try {
			raf.seek(0);
			while (raf.length() > raf.getFilePointer()){
				toReturn.add(getNext());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	private Auction getNext(){
		Auction a = null;
		try{
			byte[] b = new byte[AUCTION_LENGTH];
			raf.read(b);
			a = convertByteArrayToAuction(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public boolean containsKey(Long id){
		return get(id) != null;
	}

}
