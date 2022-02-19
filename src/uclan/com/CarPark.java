package uclan.com;

import java.util.ArrayList;

public class CarPark extends Thread { // aka consumer

	public Clock carParkClock;
	Road road;
	// private final int maxSize;
	private Vehicle[] carParkArray;
	private final int front;
	private int back;
	private final int maxSize;
	private boolean isFullCarPark;
	public String name;

	// private static int size = 0;

	CarPark(int _maxSize, String _name) {
		front = 0;
		back = -1;
		maxSize = _maxSize;
		name = _name;
		carParkArray = new Vehicle[maxSize]; // making a new array to specify the maxSize
	}
	// add

	// void add()

	int timestamp;
	ArrayList<Vehicle> Cars = new ArrayList<Vehicle>();
	// Vehicle car;

	public void run() {
		System.out.println(name + "Started");

		Alarm myAlarm = new Alarm(12);
		myAlarm.start();

		while (carParkClock.getCount() < 119) {
			// locking road
			// System.out.println("Locking road: " + road.name);

			synchronized (road) {
				while (road.IsEmpty()) {
					System.out.println("Time: " + carParkClock.time() + " - CarPark: " + name + " is waiting"
							+ " for road " + road.name + " to have a car");
					LogFileManager.writeToLog("Time: " + carParkClock.time() + " - CarPark: " + name + " is waiting"
							+ " for road " + road.name + " to have a car");
					try {
						road.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					if (isFullCarPark == false && myAlarm.hasEnded) {
						consume();
						road.notify();
						myAlarm.count = 12; // reset
						System.out.println("Time: " + carParkClock.time() + " - CarPark - Resetted alarm " + name);
						myAlarm.hasEnded = false;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogFileManager.logWarning("Interrupted exception thrown in CarPark run");
					LogFileManager.logError(e.getMessage());
				}
			}
			// unlocking road
//			if (myAlarm.hasEnded) {
//				myAlarm.count = 12; // reset
//				System.out.println("Resetted alarm " + name);
//				myAlarm.hasEnded = false;
//			}

		}
	}

	public void consume() throws InterruptedException {
		// remove
		Vehicle car = road.remove();
		car.parkedTime = carParkClock.getCount();
		System.out
				.println("Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " removed from the entry road");
		LogFileManager.writeToLog(
				"Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " removed from the entry road");
		// if (car == null) {
		// System.out.println("CarPark cannot find car...");
		// Thread.sleep(500);
		// } else {

		// check if the array is full
		if (back == maxSize - 1) {
			System.out.print("Time: " + carParkClock.time() + " - CarPark: Array is full");
			LogFileManager.writeToLog("Time: " + carParkClock.time() + " - CarPark: Array is full");
			isFullCarPark = true;
		} else {
			// Push
			carParkArray[back + 1] = car;
			back++;
			System.out.println(
					"Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " added to the CarPark " + name);
			LogFileManager.writeToLog(
					"Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " added to the CarPark " + name);
		}
		// }

		// carParkArray.add();
		// System.out.println();
		// System.out.println("CarPark: Total cars in the Industrial carpark " + (back +
		// 1) + " out of 1000 spaces");
		// System.out.println();
	}

	public String carSpaces() {
		// spaces available = total array size - spaces taken
		String spacesAvailable = String.valueOf(carParkArray.length - back);
		return spacesAvailable;
	}

	public int getCarCount() {
		return back + 1;
	}

	public int getAverageTime() {
		if (getCarCount() == 0) {
			return 0;
		}
		int averageTime = 0;
		for (int i = 0; i <= back; i++) {
			Vehicle car = carParkArray[i];
			averageTime += car.getCarParkedTime();
		}
		averageTime /= getCarCount();
		return averageTime;
	}

}
