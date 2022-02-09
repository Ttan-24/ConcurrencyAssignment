package uclan.com;

import java.util.ArrayList;

public class Clock extends Thread {

	private int count = 0;
	public int displaySpaceInterval = 10;
	public int nextDisplaySpace = 10;
	public int nextEndDisplay = 120;

	ArrayList<CarPark> CarParkArrayList = new ArrayList<CarPark>();
	ArrayList<EntryPoint> EntryPointArrayList = new ArrayList<EntryPoint>();
	ArrayList<Road> RoadArrayList = new ArrayList<Road>();

	Clock(ArrayList<CarPark> _CarParkArrayList, ArrayList<EntryPoint> _EntryPointArrayList,
			ArrayList<Road> _RoadArrayList) {
		CarParkArrayList = _CarParkArrayList;
		EntryPointArrayList = _EntryPointArrayList;
		RoadArrayList = _RoadArrayList;
	}

	/// public CarPark carPark;
	ArrayList<CarPark> carParkList = new ArrayList<CarPark>();

	public void addCarPark(CarPark carPark) {
		carParkList.add(carPark);
	}

	public void run() {
		while (true) {
			count++;
			if (count == nextDisplaySpace) {
				System.out.println("Time: " + time() + " - University: " + carParkList.get(0).carSpaces() + " Spaces"
						+ "\n                " + "Station: " + carParkList.get(1).carSpaces() + " Spaces"
						+ "\n                " + "Shopping Centre: " + carParkList.get(2).carSpaces() + " Spaces"
						+ "\n                " + "Industrial Park: " + carParkList.get(3).carSpaces() + " Spaces"
						+ "\n                ");
				nextDisplaySpace += displaySpaceInterval;
			}

			if (count == nextEndDisplay) {
				int totalCarsCreated = 0;
				int totalCarsParked = 0;
				int totalCarsCurrentlyQueuedOnRoad = 0;

				for (int i = 0; i < CarParkArrayList.size(); i++) {
					System.out.println("CarPark " + CarParkArrayList.get(i).name + " - Cars parked: "
							+ CarParkArrayList.get(i).getCarCount());
					totalCarsParked += CarParkArrayList.get(i).getCarCount();
				}
				for (int i = 0; i < EntryPointArrayList.size(); i++) {
					System.out.println("EntryPoint " + EntryPointArrayList.get(i).name + " - Cars created: "
							+ EntryPointArrayList.get(i).getCarsCreated());
					totalCarsCreated += EntryPointArrayList.get(i).getCarsCreated();
				}
				for (int i = 0; i < RoadArrayList.size(); i++) {
					System.out.println("Road " + RoadArrayList.get(i).name + " - Cars Currently Queued: "
							+ RoadArrayList.get(i).carsQueued());
					totalCarsCurrentlyQueuedOnRoad += RoadArrayList.get(i).carsQueued();
				}
				System.out.println("Total Number of Cars created: " + totalCarsCreated);
				System.out.println("Total Number of Cars parked: " + totalCarsParked);
				System.out.println("Total Cars currently queued on road: " + totalCarsCurrentlyQueuedOnRoad);

				if (totalCarsCreated == totalCarsParked + totalCarsCurrentlyQueuedOnRoad) {
					System.out.println("No Data has been lost!");
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String time() {
		String stringTimer;

		int minutes = (int) Math.floor(count / 60);
		int seconds = (int) count % 60;
		stringTimer = String.valueOf(minutes) + "m, " + String.valueOf(seconds) + "s";

		return stringTimer;
	}

	public int getCount() {
		return count;
	}

}
