package uclan.com;

public class Junction extends Thread { // aka producer and consumer. takes in consumer and than sends out as a producer

	public Clock junctionClock;
	Road[] entryRoadArray = new Road[2];
	Road[] exitRoadArray = new Road[2];
	Road entryRoad; // consumer
	Road exitRoad; // producer
	int carHold = 0;
	public int idCount = 1;
	public int entryIndex = 0;
	public int exitIndex = 0;

	// taking the car from the entry road and giving that car to the exit road
	void takeVehicle() throws InterruptedException {
		// carHold = car.id;

		Vehicle car = entryRoadArray[entryIndex].remove();
		System.out.println(
				"Time: " + junctionClock.time() + " - Junction: Car " + car.id + " consumed from the entryRoad");
		if (car == null) {
			System.out.println("Junction cannot find car...");
		} else {
			exitRoadArray[exitIndex].add(car);
			System.out.println("Time: " + junctionClock.time() + " - Junction: Car " + car.id
					+ " taken from the entry road and sending it to the exit road");
		}
	}

	public void run() {
		while (true) {

			synchronized (entryRoadArray[entryIndex]) {
				synchronized (exitRoadArray[exitIndex]) {
					while (entryRoadArray[entryIndex].IsEmpty() || exitRoadArray[exitIndex].IsFull()) {
						System.out.println("Waiting for entry road to have car and exit road to have space");
						try {
							entryRoadArray[entryIndex].wait();
							exitRoadArray[exitIndex].wait();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					try {
						takeVehicle();

						entryRoadArray[entryIndex].notify();
						exitRoadArray[exitIndex].notify();

						entryIndex++;
						exitIndex++;

						if (entryIndex > entryRoadArray.length - 1) {
							entryIndex = 0;
						}
						if (exitIndex > exitRoadArray.length - 1) {
							exitIndex = 0;
						}

						Thread.sleep(1000);
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
