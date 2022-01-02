package uclan.com;

public class Vehicle { // creating the cars in the entrypoint

	private CarPark destination;
	private int mGridlockTime;
	private int parkedTime;

	public Vehicle() {
	}

	public Vehicle(int gridlockTime) {
		mGridlockTime = gridlockTime;
	}

	// no deconstructor as such but can use .finialise

	// allows the junction to read the vehicle's destination
	public CarPark getDestination() {
		return destination;
	}
}
