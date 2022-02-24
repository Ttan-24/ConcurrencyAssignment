package uclan.com;

import java.util.Random;

public class EntryPoint extends Thread { // aka producer

	// member variables
	public Clock entryPointClock;
	Road road;
	public int idCount = 1;
	public String name;
	public int carsPerHour;

	// weighting
	private String[] DestinationArray = { "University", "Station", "Station", "Shopping Centre", "Shopping Centre",
			"Shopping Centre", "Industrial Park", "Industrial Park", "Industrial Park", "Industrial Park" };
	private Random rand = new Random();

	// constructor
	EntryPoint(String _name, int _carsPerHour) {
		name = _name;
		carsPerHour = _carsPerHour;
	}

	// get how many cars created
	public int getCarsCreated() {
		return idCount - 1;
	}

	// run method - checks and produces cars until the road is full
	public void run() {

		// create alarm
		Alarm myAlarm = new Alarm((60 * 60) / carsPerHour, this, false);
		myAlarm.start();

		// main loop
		while (entryPointClock.getCount() < entryPointClock.simulationTime) {
			// locking road
			synchronized (road) {
				// if road is full wait and notify till the cars are consumed
				while (road.IsFull()) {
					try {
						road.notify();
						road.wait(100);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					// produce and reset alarm
					if (myAlarm.hasEnded) {
						produce();
						road.notify();
						myAlarm.reset();
						myAlarm.hasEnded = false;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogFileManager.logWarning("Interrupted exception thrown in EntryPoint run");
					LogFileManager.logError(e.getMessage());
				}
			}
		}

	}

	// produce - produce cars on the road as per the weightings with random
	// destinations
	public void produce() throws InterruptedException {
		String destination = chooseRandomDestinations();
		String id = "{" + name + ", " + Integer.toString(idCount) + ", " + destination + "}";
		Vehicle car = new Vehicle(id, entryPointClock.getCount(), destination);
		idCount++;

		LogFileManager.writeToLog(
				"Time: " + entryPointClock.time() + " - EntryPoint: Car " + car.id + " produced by EntryPoint " + name);

		road.add(car);
	}

	// chooseRandomDestinations - chooses random destinations for the cars as per
	// the weighting required
	public String chooseRandomDestinations() {
		int randomDestination = rand.nextInt(DestinationArray.length);

		return DestinationArray[randomDestination];
	}
}
