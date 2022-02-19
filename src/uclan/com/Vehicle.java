package uclan.com;

public class Vehicle { // creating the cars in the entrypoint

	private String destination;
	public int entryTime;
	public int parkedTime;
	public String id;

	public Vehicle() {
	}

	public Vehicle(String _id, int _entryTime, String _destination) {
		id = _id;
		entryTime = _entryTime;
		destination = _destination;
	}

	// no deconstructor as such but can use .finialise

	// allows the junction to read the vehicle's destination
	public String getDestination() {
		return destination;
	}

	public int getCarParkedTime() {
		int carParkedTime = parkedTime - entryTime;
		return carParkedTime;
	}
}
