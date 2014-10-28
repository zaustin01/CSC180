package labs.three;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Auction {

	private final long MILLIS_IN_WEEK = 604800000l;
	private final long id;
	private int currentBid;
	private String owner;
	private String name;
	private String description;
	private int startingBid;
	private long timeLeftInMillis;
	private int numBids;
	private Map<String, Object> properties;

	public Auction(Long id, String name, Integer startingBid) {
		this.id = id;
		this.setName(name);
		this.setDescription("");
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.setNumBids(0);
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, Integer startingBid, Map<String, Object> properties){
		this.id = id;
		this.setName(name);
		this.setDescription("");
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.setNumBids(0);
		this.properties = properties;
	}
	
	public Auction(Long id, String name, String description, Integer startingBid) {
		this.id = id;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.setNumBids(0);
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Map<String, Object> properties){
		this.id = id;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.setNumBids(0);
		this.properties = properties;
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Long timeLeftInMillis) {
		this.id = id;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.setNumBids(0);
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Long timeLeftInMillis, Map<String, Object> properties) {
		this.id = id;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.setNumBids(0);
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

	}

	public long getId() {
		return id;
	}

	public int getStartingBid() {
		return startingBid;
	}

	private void setStartingBid(int startingBid) {
		this.startingBid = startingBid;
	}
	
	public long getTimeLeftInMillis() {
		return timeLeftInMillis;
	}

	public void setTimeLeftInMillis(long timeLeftInMillis) {
		this.timeLeftInMillis = timeLeftInMillis;
	}
	
	public Date endsBy() {
		Date d = new Date(System.currentTimeMillis() + timeLeftInMillis);
		return d;
	}

	public int getNumBids() {
		return numBids;
	}

	public void setNumBids(int numBids) {
		this.numBids = numBids;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public Object getProperty(String propertyName){
		return properties.get(propertyName);
	}
	
	
	public void setProperty(String propertyName, Object propertyValue){
		properties.put(propertyName, propertyValue);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "Id = " + this.getId() + "\n"
				+ "Name = " + this.getName() + "\n"
				+ "Description = " + this.getDescription() + "\n"
				+ "Owner = " + ((this.getOwner() == null) ? " " : this.getOwner()) + "\n"
				+ "Current Bid = $" + this.getCurrentBid() + "\n";
	}
	
	@Override
	public int hashCode(){
		return this.name.length() * 13 * 61;
	}

	
}
