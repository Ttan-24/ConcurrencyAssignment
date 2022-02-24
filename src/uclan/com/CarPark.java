package uclan.com;

import java.util.ArrayList;

public class CarPark extends Thread { // aka consumer

	// member variables
	public Clock carParkClock;
	Road road;
	private Vehicle[] carParkArray;
	private final int front;
	private int back;
	private final int maxSize;
	private boolean isFullCarPark;
	public String name;
	int carAdmissionTime = 12;
	ArrayList<Vehicle> Cars = new ArrayList<Vehicle>();

	// constructor
	CarPark(int _maxSize, String _name) {
		front = 0;
		back = -1;
		maxSize = _maxSize;
		name = _name;
		carParkArray = new Vehicle[maxSize]; // making a new array to specify the maxSize
	}

	// run method - Checks and consumes cars
	public void run() {
		LogFileManager.writeToLog(name + " Started");

		// create alarm
		Alarm myAlarm = new Alarm(carAdmissionTime, this, false);
		myAlarm.start();

		// main loop
		while (carParkClock.getCount() < carParkClock.simulationTime) {
			// locking road
			synchronized (road) {
				// wait for road to be empty
				while (road.IsEmpty()) {
					LogFileManager.writeToLog("Time: " + carParkClock.time() + " - CarPark: " + name + " is waiting"
							+ " for road " + road.name + " to have a car");
					try {
						road.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					// consume and reset alarm
					if (isFullCarPark == false && myAlarm.hasEnded) {
						consume();
						road.notify();
						myAlarm.reset();
						LogFileManager
								.writeToLog("Time: " + carParkClock.time() + " - CarPark - Resetted alarm " + name);
						myAlarm.hasEnded = false;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogFileManager.logWarning("Interrupted exception thrown in CarPark run");
					LogFileManager.logError(e.getMessage());
				}
			}
		}
	}

	// consume method - consumes car from the road and pushes it into the car park
	// array
	public void consume() throws InterruptedException {
		// remove car from the road
		Vehicle car = road.remove();
		car.parkedTime = carParkClock.getCount();
		LogFileManager.writeToLog(
				"Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " removed from the entry road");

		// check if the array is full
		if (back == maxSize - 1) {
			LogFileManager.writeToLog("Time: " + carParkClock.time() + " - CarPark: Array is full");
			isFullCarPark = true;
		} else {
			// push in the car park array
			carParkArray[back + 1] = car;
			back++;
			LogFileManager.writeToLog(
					"Time: " + carParkClock.time() + " - CarPark: Car " + car.id + " added to the CarPark " + name);
		}
	}

	// carSpaces - available space in the car park array
	public String carSpaces() {
		// spaces available = total array size - spaces taken
		String spacesAvailable = String.valueOf(carParkArray.length - back);
		return spacesAvailable;
	}

	// getCarCount - cars parked
	public int getCarCount() {
		return back + 1;
	}

	// getAverageTime - average journey time for all cars to get to a car
	// park
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
