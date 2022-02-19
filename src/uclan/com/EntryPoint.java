package uclan.com;

import java.util.Random;

public class EntryPoint extends Thread { // aka producer

	public Clock entryPointClock;
	Road road;
	public int idCount = 1;
	public String name;
	public int carsPerHour;
	private String[] DestinationArray = { "University", "Station", "Station", "Shopping Centre", "Shopping Centre",
			"Shopping Centre", "Industrial Park", "Industrial Park", "Industrial Park", "Industrial Park" };
	private Random rand = new Random();

	EntryPoint(String _name, int _carsPerHour) {
		name = _name;
		carsPerHour = _carsPerHour;
	}

	public int getCarsCreated() {
		return idCount - 1;
	}

	public void run() {

		Alarm myAlarm = new Alarm((60 * 60) / carsPerHour);
		myAlarm.start();

		while (entryPointClock.getCount() < 119) {
			// System.out.println(name + " is waiting to lock road: " + road.name);
			synchronized (road) {
				// System.out.println(name + " has locked road: " + road.name);
				while (road.back == road.maxSize - 1) {
					try {
						System.out.println(name + ": " + road.name + "'s buffer is full, " + " waiting for "
								+ "consumer to take something from queue");

						LogFileManager.writeToLog((name + ": " + road.name + "'s buffer is full, " + " waiting for "
								+ "consumer to take something from queue"));

						road.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					// System.out.println("Produced at: " + entryPointClock.time());
					if (myAlarm.hasEnded) {
						produce();
						road.notify();
						myAlarm.count = ((60 * 60) / carsPerHour); // reset
						System.out
								.println("Time: " + entryPointClock.time() + " - EntryPoint - Resetted alarm " + name);
						myAlarm.hasEnded = false;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogFileManager.logWarning("Interrupted exception thrown in EntryPoint run");
					LogFileManager.logError(e.getMessage());
				}
			}
			// System.out.println(name + " is unlocking road: " + road.name);
		}

	}

	public void produce() throws InterruptedException {
		String destination = chooseRandomDestinations();
		String id = "{" + name + ", " + Integer.toString(idCount) + ", " + destination + "}";
		Vehicle car = new Vehicle(id, entryPointClock.getCount(), destination);
		idCount++;

		System.out.println(
				"Time: " + entryPointClock.time() + " - EntryPoint: Car " + car.id + " produced by EntryPoint " + name);

		LogFileManager.writeToLog(
				"Time: " + entryPointClock.time() + " - EntryPoint: Car " + car.id + " produced by EntryPoint " + name);

		road.add(car);
	}

	public String chooseRandomDestinations() {
		int randomDestination = rand.nextInt(DestinationArray.length);

		return DestinationArray[randomDestination];
	}

	int preDeterminedRate;
	// array of vehicles generated

	void generateVehicleObjects() {
		// generate Vehicle objects with destinations at a pre-determined rate, and feed
		// them into the road network as long as space is available on the road.
	}

	// give the car a timestamp when it entered the town
	void carTimestamp() {

	}

	// destinations of cars should be weighted randomly
	CarPark getRandomDestination() {
		return null;
	}
}
