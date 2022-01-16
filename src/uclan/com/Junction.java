package uclan.com;

import java.util.HashMap;

public class Junction extends Thread { // aka producer and consumer. takes in consumer and than sends out as a producer

	public Clock junctionClock;
	Road[] entryRoadArray = new Road[4];
	Road[] exitRoadArray = new Road[4];
	Road entryRoad; // consumer
	Road exitRoad; // producer
	int carHold = 0;
	public int idCount = 1;
	public int entryIndex = 0;
	public int exitIndex;
	public int lastTime = 0;
	private int carsPerMinute = 12;
	private int trafficLightDelay = 60;
	private Vehicle car;

	private HashMap<String, Integer> destinationMap = new HashMap<String, Integer>();

	// taking the car from the entry road and giving that car to the exit road
	void takeVehicle() throws InterruptedException {
		// carHold = car.id;

		car = entryRoadArray[entryIndex].remove();
		System.out.println(
				"Time: " + junctionClock.time() + " - Junction: Car " + car.id + " consumed from the entryRoad");

		String destination = car.getDestination();

		exitIndex = destinationMap.get(destination);

	}

	void sendVehicle() throws InterruptedException {
		String destination = car.getDestination();
		if (car == null) {
			System.out.println("Junction cannot find car...");
		} else {

			exitRoadArray[exitIndex].add(car);
			System.out.println("Time: " + junctionClock.time() + " - Junction: Car " + car.id
					+ " taken from the entry road and sending it to the exit road: " + exitIndex + " for destination: "
					+ destination);
		}
	}

	public void addDestinationMapping(String destination, int destinationRoadIndex) {
		destinationMap.put(destination, destinationRoadIndex);
	}

	public void run() {
		while (true) {

			synchronized (entryRoadArray[entryIndex]) {
				// Only entry code here
				while (entryRoadArray[entryIndex].IsEmpty()) {
					System.out.println("Waiting for entry road to have car and exit road to have space");
					try {
						entryRoadArray[entryIndex].wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					takeVehicle();
					entryRoadArray[entryIndex].notify();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				synchronized (exitRoadArray[exitIndex]) {
					while (exitRoadArray[exitIndex].IsFull()) {
						System.out.println("Waiting for entry road to have car and exit road to have space");
						try {
							// entryRoadArray[entryIndex].wait();
							exitRoadArray[exitIndex].wait();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					try {
						sendVehicle();

						exitRoadArray[exitIndex].notify();

						if (junctionClock.getCount() > lastTime + trafficLightDelay) {
							entryIndex++;
							lastTime = junctionClock.getCount();
							System.out.println("Switching Green the entry Road");
						}

						// fix index when out of bounds
						if (entryIndex > entryRoadArray.length - 1) {
							entryIndex = 0;
						}

						// fix index when null
						if (entryRoadArray[entryIndex] == null) {
							entryIndex = 0;
						}

						Thread.sleep(1000 / carsPerMinute);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}

	// ArrayList<Road> roadList = new ArrayList<Road>();

	// Boolean Route;
	// array of route
	// array of 4 entrances
	// array of 4 exits
	// boolean array of 4 enables - flags and toggle
	// int entranceIndex;

//array of four different durations for green lights

	void simulateCars() {
// wait
//ask
//sleep
	}
}
