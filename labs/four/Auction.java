package labs.four;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import labs.four.exceptions.InvalidEditException;

public class Auction {

	private final long MILLIS_IN_WEEK = 604800000l;
	private static final String DEFAULT_CREATOR = "system";
	private long externalId;
	private final long id; //13
	private final String creator; //20
	private String name; //90
	private String description; //200
	private int currentBid; //12
	private int startingBid; //12
	private String owner; //20
	private int numBids; // 5
	private long timeLeftInMillis; //13
	private Map<String, Object> properties;

	public Auction(Long id, String name, Integer startingBid) {
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription("");
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.numBids = 0;
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, Integer startingBid, Map<String, Object> properties){
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription("");
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.numBids = 0;
		this.properties = properties;
	}
	
	public Auction(Long id, String name, String description, Integer startingBid) {
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.numBids = 0;
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Map<String, Object> properties){
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.numBids = 0;
		this.properties = properties;
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Long timeLeftInMillis) {
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.numBids = 0;
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String name, String description, Integer startingBid, Long timeLeftInMillis, Map<String, Object> properties) {
		this.id = id;
		this.creator = DEFAULT_CREATOR;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.numBids = 0;
		this.properties = properties;
	}
	
	public Auction(Long id, String creator, String name, String description, Integer startingBid){
		this.id = id;
		this.creator = creator;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(MILLIS_IN_WEEK);
		this.numBids = 0;
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String creator, String name, String description, Integer startingBid, Long timeLeftInMillis){
		this.id = id;
		this.creator = creator;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.numBids = 0;
		this.properties = new HashMap<String, Object>();
	}
	
	public Auction(Long id, String creator, String name, String description, Integer startingBid, Long timeLeftInMillis, Map<String, Object> properties){
		this.id = id;
		this.creator = creator;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.numBids = 0;
		this.properties = properties;
	}
	
	public Auction(Long id, String creator, String name, String description, Integer currentBid, Integer startingBid, String owner, Integer numBids, Long timeLeftInMillis){
		this.id = id;
		this.creator = creator;
		this.owner = owner;
		this.setName(name);
		this.setDescription(description);
		this.setCurrentBid(currentBid);
		this.setStartingBid(startingBid);
		this.setTimeLeftInMillis(timeLeftInMillis);
		this.numBids = numBids;
		this.properties = new HashMap<String, Object>();
	}

	public String getCreator() {
		return creator;
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
			throw new InvalidEditException();
		} else {
			this.name = name;
		}
	}

	public int getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(int currentBid) {
		if (numBids > 0){
			throw new InvalidEditException();
		} else {
			this.currentBid = currentBid;
			this.startingBid = currentBid;
		}

	}

	public long getId() {
		return id;
	}

	public long getExternalId() {
		return externalId;
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
		if (currentBid > startingBid){
			throw new InvalidEditException();
		} else {
			this.timeLeftInMillis = timeLeftInMillis;
		}
	}
	
	public Date endsBy() {
		Date d = new Date(System.currentTimeMillis() + timeLeftInMillis);
		return d;
	}

	public int getNumBids() {
		return numBids;
	}
	
	public void setNumBids(int num){
		this.numBids = num;
	}

	public void bid(String owner) {
		this.numBids++;
		this.setOwner(owner);
		this.currentBid ++;
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
