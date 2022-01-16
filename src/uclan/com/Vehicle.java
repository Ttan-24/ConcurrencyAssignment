package uclan.com;

public class Vehicle { // creating the cars in the entrypoint

	private String destination;
	private String entryTime;
	public String parkedTime;
	public String id;

	public Vehicle() {
	}

	public Vehicle(String _id, String _entryTime, String _destination) {
		id = _id;
		entryTime = _entryTime;
		destination = _destination;
	}

	// no deconstructor as such but can use .finialise

	// allows the junction to read the vehicle's destination
	public String getDestination() {
		return destination;
	}
}
