package uclan.com;

public class Vehicle { // creating the cars in the entrypoint

	private CarPark destination;
	private String entryTime;
	public String parkedTime;
	public int id;

	public Vehicle() {
	}

	public Vehicle(int _id, String _entryTime) {
		id = _id;
		entryTime = _entryTime;
	}

	// no deconstructor as such but can use .finialise

	// allows the junction to read the vehicle's destination
	public CarPark getDestination() {
		return destination;
	}
}
