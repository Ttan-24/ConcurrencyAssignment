package uclan.com;

import java.util.ArrayList;

public class Junction extends Thread { // aka producer and consumer. takes in consumer and than sends out as a producer

	Road entryRoad; // consumer
	Road exitRoad; // producer
	int carHold = 0;

	// taking the car from the entry road and giving that car to the exit road
	void takeVehicle() throws InterruptedException {
		// carHold = car.id;

		Vehicle car = entryRoad.remove();
		if (car == null) {
			System.out.println("Junction cannot find car...");
		} else {
			exitRoad.add(car);
			System.out
					.println("Junction: Car " + car.id + " taken from the entry road and sending it to the exit road");
		}
	}

	public void run() {
		for (int i = 0; i < 10; i++) {

			// System.out.println("Hi I am a Junction!");
			try {
				takeVehicle();
				System.out.println();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	ArrayList<Road> roadList = new ArrayList<Road>();

	Boolean Route;
	// array of route
	// array of 4 entrances
	// array of 4 exits
	// boolean array of 4 enables - flags and toggle
	int entranceIndex;

//array of four different durations for green lights

	void simulateCars() {
// wait
//ask
//sleep
	}
}
