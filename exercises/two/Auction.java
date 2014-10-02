package exercisetwo;

public class Auction {

	private int id;
	private double currentBid;
	private String owner;
	private String name;
	private double startingBid;

	public Auction(int id, String name, double startingBid) {
		this.setId(id);
		this.setName(name);
		this.setCurrentBid(startingBid);
		this.setStartingBid(startingBid);
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

	public double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(double currentBid) {
		if (currentBid > this.getCurrentBid()) {
			this.currentBid = currentBid;
		}
		// else throw an exception. The value of the item shouldn't be going
		// down as the number of bids increases
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public double getStartingBid() {
		return startingBid;
	}

	private void setStartingBid(double startingBid) {
		this.startingBid = startingBid;
	}

	@Override
	public boolean equals(Object o) {
		try {
			Auction other = (Auction) o;
			if (other.getId() == this.getId()) {
				return true;
			} else {
				return false;
			}
		} catch (ClassCastException e) {
			return false;
		}

	}

	@Override
	public String toString() {
		return "Id = " + this.getId() + "\n" + "Name = " + this.getName()
				+ "\n" + "Owner = " + this.getOwner() + "\n"
				+ "Current Bid = $" + this.getCurrentBid() + "\n";
	}
}
