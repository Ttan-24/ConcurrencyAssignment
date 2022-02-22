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
	private HashMap<String, Integer> destinationMap = new HashMap<String, Integer>();

	// constructors
	Junction(String _name, int _greenLightTime) { // needs to take time
		name = _name;
		greenLightTime = _greenLightTime;
	}

	// takeVehicle - taking the car from the entry road to later be sent to the exit
	// road
	void takeVehicle() throws InterruptedException {
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
	void sendVehicle() throws InterruptedException {
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
		Alarm myLightAlarm = new Alarm(greenLightTime);
		myLightAlarm.start();

		Alarm myCarAlarm = new Alarm(carAlarmTime);
		myCarAlarm.start();

		// main loop
		while (junctionClock.getCount() < junctionClock.simulationTime) {
			// remember if vehicle was sent to notify entry road
			boolean sentVehicle = false;
			// lock entry road
			synchronized (entryRoadArray[entryIndex]) {
				// send car if entry road has one
				if (entryRoadArray[entryIndex].hasCar() && myCarAlarm.hasEnded) {
					myCarAlarm.hasEnded = false;
					myCarAlarm.reset();
					// need to get correct exit
					Vehicle frontCar;
					try {
						frontCar = entryRoadArray[entryIndex].getFrontCar();
						if (destinationMap.containsKey(frontCar.getDestination())) {
							exitIndex = destinationMap.get(frontCar.getDestination());
						}
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// lock exit road
					synchronized (exitRoadArray[exitIndex]) {
						// send car
						if (exitRoadArray[exitIndex].hasSpace()) {
							try {
								takeVehicle();
								sendVehicle();
								vehicleSentCount++;
								sentVehicle = true;
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						exitRoadArray[exitIndex].notify();
					}
					// unlock exit road
				}
				if (sentVehicle == true) {
					entryRoadArray[entryIndex].notify();
					sentVehicle = false;
				}
			}

			// unlock entry road

			// change entry road if light alarm has ended
			if (myLightAlarm.hasEnded) {
				String previousRoad = entryRoadArray[entryIndex].name;

				entryIndex++;

				if (entryIndex > entryRoadArray.length - 1) {
					entryIndex = 0;
				}

				if (entryRoadArray[entryIndex] == null) {
					entryIndex = 0;
				}
				String nextRoad = entryRoadArray[entryIndex].name;

				LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name
						+ " : Switched from road " + previousRoad + " to " + nextRoad);

				LogFileManager.writeToLogWithoutEndLine("Time: " + junctionClock.time() + " - Junction " + name + " : "
						+ vehicleSentCount + " cars through from " + previousRoad + ", "
						+ entryRoadArray[entryIndex].carsQueued() + " cars waiting.");

				if (entryRoadArray[entryIndex].carsQueued() > 0 && vehicleSentCount == 0) {
					LogFileManager.writeToLogWithoutEndLine(" GRIDLOCK");
				}
				LogFileManager.writeToLog("\n");
				vehicleSentCount = 0;
				myLightAlarm.hasEnded = false;
				myLightAlarm.reset();

			}

		}

	}
}
