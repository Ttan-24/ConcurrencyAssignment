package uclan.com;

public class Vehicle { // creating the cars in the entrypoint

	// member variables
	private String destination;
	public int entryTime;
	public int parkedTime;
	public String id;

	// default constructor
	public Vehicle() {
	}

	// parameterised constructor
	public Vehicle(String _id, int _entryTime, String _destination) {
		id = _id;
		entryTime = _entryTime;
		destination = _destination;
	}

	// allows the junction to read the vehicle's destination
	public String getDestination() {
		return destination;
	}

	// to get average time to end
	public int getCarParkedTime() {
		int carParkedTime = parkedTime - entryTime;
		return carParkedTime;
	}
}
