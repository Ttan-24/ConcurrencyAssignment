package uclan.com;

import java.util.HashMap;

public class Junction extends Thread { // aka producer and consumer. takes in consumer and than sends out as a producer

	// member variables
	public Clock junctionClock;
	Road[] entryRoadArray = new Road[4];
	Road[] exitRoadArray = new Road[4];
	int carHold = 0;
	public int idCount = 1;
	public int entryIndex = 0;
	public int exitIndex;
	public int lastTime = 0;
	private Vehicle car;
	public String name;
	public int greenLightTime;
	public int vehicleSentCount = 0;
	int carAlarmTime = 60 / 12; // seconds/minute
	int notifyDelay = 5;
	private HashMap<String, Integer> destinationMap = new HashMap<String, Integer>();

	// constructors
	Junction(String _name, int _greenLightTime) { // needs to take time
		name = _name;
		greenLightTime = _greenLightTime;
	}

	// takeVehicle - taking the car from the entry road to later be sent to the exit
	// road
	void takeVehicle() {
		car = entryRoadArray[entryIndex].remove();

		LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
				+ " consumed from the entryRoad " + entryRoadArray[entryIndex].name);

		String destination = car.getDestination();

		exitIndex = destinationMap.get(destination);

		if (destinationMap.get(destination) == null) {
			LogFileManager.writeToLog("oh no!!!!!");
		}
	}

	// sendVehicle - sending the car to the exit road as per the destination of that
	// car
	void sendVehicle() {
		String destination = car.getDestination();
		if (car == null) {
			LogFileManager.writeToLog("Junction cannot find car...");
		} else {

			exitRoadArray[exitIndex].add(car);

			LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
					+ " sending it to the exit road: " + exitRoadArray[exitIndex].name + " for destination: "
					+ destination);
		}
	}

	// addDestinationMapping - adds destination(key) and the road index(value) for
	// that destination
	public void addDestinationMapping(String destination, int destinationRoadIndex) {
		destinationMap.put(destination, destinationRoadIndex);
	}

	// run method - produces and consumes cars by having an entry and exit road
	// array
	public void run() {
		// create alarms
		Alarm myLightAlarm = new Alarm(greenLightTime, this, true);
		myLightAlarm.start();

		Alarm myCarAlarm = new Alarm(carAlarmTime, this, false);
		myCarAlarm.start();

		// Main loop
		while (junctionClock.getCount() < junctionClock.simulationTime) {
			Road entryRoad = entryRoadArray[entryIndex];
			// lock entry road
			synchronized (entryRoad) {
				// Keep consuming until entry road is empty or green light switch
				while (!entryRoad.IsEmpty() && myLightAlarm.count >= 0) {

					while (!myCarAlarm.hasEnded) {
						// wait for previous car to cross
						System.out.print("");
					}

					// need to get correct exit
					Vehicle frontCar = entryRoadArray[entryIndex].getFrontCar();

					exitIndex = destinationMap.get(frontCar.getDestination());
					Road exitRoad = exitRoadArray[exitIndex];
					// lock exit road
					synchronized (exitRoad) {
						// send car
						if (exitRoad.hasSpace()) {
							takeVehicle();
							sendVehicle();
							vehicleSentCount++;
							myCarAlarm.reset();
						}
						exitRoad.notify();
					}
					// unlock exit road
					entryRoad.notify();
					try {
						entryRoad.wait(notifyDelay); // Give a chance for entry point to produce
					} catch (InterruptedException e) {

					}
				}

				// If alarm has not ended, keep waiting
				if (entryRoad.IsEmpty()) {
					// Wait for it to have a car
					try {
						entryRoad.wait();
					} catch (Exception e) { // Interrupted by alarm
					}

				}

				// change entry road if light alarm has ended
				if (myLightAlarm.count <= 0) {

					// record cars that went through
					String previousRoad = entryRoad.name;

					// Move to next entry index
					entryIndex++;
					if (entryIndex == entryRoadArray.length - 1) {
						entryIndex = 0;
					}

					if (entryRoadArray[entryIndex] == null) {
						entryIndex = 0;
					}

					String nextRoad = entryRoadArray[entryIndex].name;

					LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name
							+ " : Switched from road " + previousRoad + " to " + nextRoad);

					LogFileManager.writeToLogWithoutEndLine("Time: " + junctionClock.time() + " - Junction " + name
							+ " : " + vehicleSentCount + " cars through from " + previousRoad + ", "
							+ entryRoadArray[entryIndex].carsQueued() + " cars waiting.");

					if (entryRoadArray[entryIndex].carsQueued() > 0 && vehicleSentCount == 0) {
						LogFileManager.writeToLogWithoutEndLine(" GRIDLOCK");
					}
					LogFileManager.writeToLog("\n");
					vehicleSentCount = 0;

					// Reset alarm
					myLightAlarm.reset();
				}
			}
			// unlock entry road
		}
	}
}
