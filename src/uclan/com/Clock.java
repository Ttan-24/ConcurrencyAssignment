package uclan.com;

import java.util.ArrayList;

public class Clock extends Thread {

	// member variables
	private int count = 0;
	public int displaySpaceInterval = 600;
	public int nextDisplaySpace = 600;
	public int simulationTime = 3600;

	ArrayList<CarPark> CarParkArrayList = new ArrayList<CarPark>();
	ArrayList<EntryPoint> EntryPointArrayList = new ArrayList<EntryPoint>();
	ArrayList<Road> RoadArrayList = new ArrayList<Road>();
	ArrayList<CarPark> carParkList = new ArrayList<CarPark>();

	// constructor
	Clock(ArrayList<CarPark> _CarParkArrayList, ArrayList<EntryPoint> _EntryPointArrayList,
			ArrayList<Road> _RoadArrayList) {
		CarParkArrayList = _CarParkArrayList;
		EntryPointArrayList = _EntryPointArrayList;
		RoadArrayList = _RoadArrayList;
	}

	// add car park to the carPark ArrayList
	public void addCarPark(CarPark carPark) {
		carParkList.add(carPark);
	}

	// run method - displays the spaces available in the car park and the total
	// display when the simulation ends
	public void run() {
		while (true) {
			// display available spaces in the car park
			if (count == nextDisplaySpace && count < simulationTime) {
				LogFileManager.writeToLog("\n" + "Time: " + time() + " - University: " + carParkList.get(0).carSpaces()
						+ " Spaces" + "\n                " + "Station: " + carParkList.get(1).carSpaces() + " Spaces"
						+ "\n                " + "Shopping Centre: " + carParkList.get(2).carSpaces() + " Spaces"
						+ "\n                " + "Industrial Park: " + carParkList.get(3).carSpaces() + " Spaces"
						+ "\n                ");
				nextDisplaySpace += displaySpaceInterval;
			}
			// end report - total display of cars when parked, created and queued and
			// checking if there is no data loss at the end of the simulation
			if (count == simulationTime + 100) {
				LogFileManager.writeToLog("\n"
						+ "-----------------------------------------------------------------------------------------------------------------");
				int totalCarsCreated = 0;
				int totalCarsParked = 0;
				int totalCarsCurrentlyQueuedOnRoad = 0;
				// Car Park - cars parked
				for (int i = 0; i < CarParkArrayList.size(); i++) {
					LogFileManager.writeToLog("CarPark " + CarParkArrayList.get(i).name + " - Cars parked: "
							+ CarParkArrayList.get(i).getCarCount() + "               average journey time: "
							+ CarParkArrayList.get(i).getAverageTime());

					totalCarsParked += CarParkArrayList.get(i).getCarCount();
				}
				// EntryPoint - cars created
				for (int i = 0; i < EntryPointArrayList.size(); i++) {

					LogFileManager.writeToLog("EntryPoint " + EntryPointArrayList.get(i).name + " - Cars created: "
							+ EntryPointArrayList.get(i).getCarsCreated());

					totalCarsCreated += EntryPointArrayList.get(i).getCarsCreated();
				}
				// Roads - cars queued
				for (int i = 0; i < RoadArrayList.size(); i++) {

					LogFileManager.writeToLog("Road " + RoadArrayList.get(i).name + " - Cars Currently Queued: "
							+ RoadArrayList.get(i).carsQueued());

					totalCarsCurrentlyQueuedOnRoad += RoadArrayList.get(i).carsQueued();
				}

				LogFileManager.writeToLog("Total Number of Cars created: " + totalCarsCreated);
				LogFileManager.writeToLog("Total Number of Cars parked: " + totalCarsParked);
				LogFileManager.writeToLog("Total Cars currently queued on road: " + totalCarsCurrentlyQueuedOnRoad);

				// check data loss
				if (totalCarsCreated == totalCarsParked + totalCarsCurrentlyQueuedOnRoad) {
					LogFileManager.writeToLog("No Data has been lost!");
				} else {
					int carsLost = (totalCarsParked + totalCarsCurrentlyQueuedOnRoad) - totalCarsCreated;
					LogFileManager.writeToLog("Data has been lost!" + carsLost);
				}
				LogFileManager.writeToLog("\n"
						+ "-----------------------------------------------------------------------------------------------------------------");
			}
			try {
				// increment time
				count++;
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LogFileManager.logWarning("Interrupted exception thrown in Clock run");
				LogFileManager.logError(e.getMessage());
			}
		}
	}

	// time - formats time into string
	public String time() {
		String stringTimer;

		int minutes = (int) Math.floor(count / 60);
		int seconds = (int) count % 60;
		stringTimer = String.valueOf(minutes) + "m, " + String.valueOf(seconds) + "s";

		return stringTimer;
	}

	// get count
	public int getCount() {
		return count;
	}

}
