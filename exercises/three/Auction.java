package exercisethree;

import java.util.HashMap;
import java.util.Map;

public class Auction {

	private final int id;
	private int currentBid;
	private String owner;
	private String name;
	private int startingBid;
	private Map<String, Object> properties;

	public Auction(Integer id, String name, Integer startingBid) {
		this.id = id;
		this.setName(name);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Integer id, String name, Integer startingBid, Map<String, Object> properties){
		this.id = id;
		this.setName(name);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.properties = properties;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (this.getCurrentBid() > this.getStartingBid()) {
			// I'm not really sure what this method should do if the user is not
			// allowed to change the name
		} else {
			this.name = name;
		}
	}

	public int getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(int currentBid) {
		if (currentBid > this.getCurrentBid()) {
			this.currentBid = currentBid;
		}
		// else throw an exception. The value of the item shouldn't be going
		// down as the number of bids increases
	}

	public int getId() {
		return id;
	}

	public int getStartingBid() {
		return startingBid;
	}

	private void setStartingBid(int startingBid) {
		this.startingBid = startingBid;
	}

	@Override
	public boolean equals(Object o) {
		try {
			Auction other = (Auction) o;
			return other.getId() == this.getId();
		} catch (ClassCastException e) {
			return false;
		}

	}

	@Override
	public String toString() {
		String propertiesString = this.properties.toString();
		return "Id = " + this.getId() + "\n" + "Name = " + this.getName()
				+ "\n" + "Owner = " + this.getOwner() + "\n"
				+ "Current Bid = $" + this.getCurrentBid() + "\n"
				+ "Properties:" + propertiesString;
	}
	
	@Override
	public int hashCode(){
		return this.id * 13 * 61;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public Object getProperty(String propertyName){
		return properties.get(propertyName);
	}
	
	public <T> T getProperty(String propertyName, Class<T> propertyType){
		return (T)properties.get(propertyName);
	}
	
	public void setProperty(String propertyName, Object propertyValue){
		properties.put(propertyName, propertyValue);
	}
}
