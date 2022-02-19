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
	public String name;
	public int greenLightTime;

	Junction(String _name, int _greenLightTime) { // needs to take time
		name = _name;
		greenLightTime = _greenLightTime;
	}

	private HashMap<String, Integer> destinationMap = new HashMap<String, Integer>();

	// taking the car from the entry road and giving that car to the exit road
	void takeVehicle() throws InterruptedException {
		// carHold = car.id;

		car = entryRoadArray[entryIndex].remove();
		System.out.println("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
				+ " consumed from the entryRoad " + entryRoadArray[entryIndex].name);

		LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
				+ " consumed from the entryRoad " + entryRoadArray[entryIndex].name);

		String destination = car.getDestination();

		exitIndex = destinationMap.get(destination);

		if (destinationMap.get(destination) == null) {
			System.out.println("Oh no!!!!!!!");
			LogFileManager.writeToLog("oh no!!!!!");
		}
	}

	void sendVehicle() throws InterruptedException {
		String destination = car.getDestination();
		if (car == null) {
			System.out.println("Junction cannot find car...");
		} else {

			exitRoadArray[exitIndex].add(car);
			System.out.println("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
					+ " sending it to the exit road: " + exitRoadArray[exitIndex].name + " for destination: "
					+ destination);

			LogFileManager.writeToLog("Time: " + junctionClock.time() + " - Junction " + name + " : Car " + car.id
					+ " sending it to the exit road: " + exitRoadArray[exitIndex].name + " for destination: "
					+ destination);
		}
	}

	public void addDestinationMapping(String destination, int destinationRoadIndex) {
		destinationMap.put(destination, destinationRoadIndex);
	}

	private void printLockedResource(Object resource) {
		System.out.println(Thread.currentThread().getName() + ": locked resource -> " + resource);
	}

	public void run() {
//		set alarm (first time)
//		while (true)
//		{
//		    lock entry road
//		    if (!entryRoadIsEmpty)
//		    {
//		        lock exit road
//		        if (exitRoadHasSpace)
//		        {
//		            move car
//		        }
//		        unlock exit road
//		    }
//		    unlock entry road
//
//		    if (alarmEnded)
//		    {
//		        change entry road
//		        reset alarm
//		    }
//		}

		// create alarm
		Alarm myLightAlarm = new Alarm(greenLightTime);
		myLightAlarm.start();

		Alarm myCarAlarm = new Alarm(60 / 12); // seconds/minute
		myCarAlarm.start();

		// main loop
		while (junctionClock.getCount() < 119) {
			// lock entry road
			synchronized (entryRoadArray[entryIndex]) {
				if (entryRoadArray[entryIndex].hasCar() && myCarAlarm.hasEnded) {
					myCarAlarm.hasEnded = false;
					myCarAlarm.count = 60 / 12;
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
						if (exitRoadArray[exitIndex].hasSpace()) {
							try {
								takeVehicle();
								sendVehicle();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// System.out.println("exit road array notify");
						exitRoadArray[exitIndex].notify();
					}
					// unlock exit road
				}
			}
			// unlock entry road

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
				System.out.println("Time: " + junctionClock.time() + " - Junction " + name + " : Switched from road"
						+ previousRoad + "to " + nextRoad);

				myLightAlarm.hasEnded = false;
				myLightAlarm.count = greenLightTime;
			}

		}

	}

//	public void run() {
//		while (true) {
//			//
//			// if (IsAlarmEnded) {
//
//			// }
//
//			// keep consuming until there is nothing to consume from the road or the timer
//			// has stopped
//
//			System.out.println(name + " is is waiting to lock entry " + entryRoadArray[entryIndex].name);
//
//			synchronized (entryRoadArray[entryIndex]) {
//				System.out.println(name + " has locked entry " + entryRoadArray[entryIndex].name);
//				// Only entry code here
//				Alarm myAlarm = new Alarm(10, this);
//				myAlarm.start();
//				while (entryRoadArray[entryIndex].IsEmpty() && !IsAlarmEnded) { // && !IsAlarmEnded
//					System.out.println(
//							name + ": Waiting for entry road " + entryRoadArray[entryIndex].name + " to have car");
//
//					LogFileManager.writeToLog(name + ": Waiting for entry road to have car");
//
//					try {
//						entryRoadArray[entryIndex].wait();
//						// Thread.sleep(100);
//					} catch (Exception ex) {
//						ex.printStackTrace();
//						LogFileManager.logWarning("Exception thrown in Junction run while entryRoadArray");
//						LogFileManager.logError(ex.getMessage());
//					}
//				}
//				try {
//					takeVehicle();
//					entryRoadArray[entryIndex].notify();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					LogFileManager.logWarning("Exception thrown in Junction run takeVehicle entryRoadArray");
//					LogFileManager.logError(e.getMessage());
//				}
//
//				System.out.println(name + " is waiting to lock exit " + exitRoadArray[exitIndex].name);
//				synchronized (exitRoadArray[exitIndex]) {
//					System.out.println(name + " has locked exit " + exitRoadArray[exitIndex].name);
//					while (exitRoadArray[exitIndex].IsFull()) {
//						System.out.println(
//								name + ": Waiting for exit road " + exitRoadArray[exitIndex].name + " to have space");
//
//						LogFileManager.writeToLog(name + ": Waiting for exit road to have space");
//
//						try {
//							// entryRoadArray[entryIndex].wait();
//							exitRoadArray[exitIndex].wait();
//						} catch (Exception ex) {
//							ex.printStackTrace();
//							LogFileManager.logWarning("Exception thrown in Junction run exitRoadArray");
//							LogFileManager.logError(ex.getMessage());
//						}
//					}
//					try {
//						sendVehicle();
//
//						exitRoadArray[exitIndex].notify();
//
//						if (junctionClock.getCount() > lastTime + trafficLightDelay) {
//							if (IsAlarmEnded) {
//								entryIndex++;
//								lastTime = junctionClock.getCount();
//								System.out.println("Switching Green the entry Road");
//								System.out.println("My alarm ended in this Junction: " + name);
//								IsAlarmEnded = false;
//								LogFileManager.writeToLog("Switching Green the entry Road");
//							}
//
//							// fix index when out of bounds
//							if (entryIndex > entryRoadArray.length - 1) {
//								entryIndex = 0;
//							}
//
//							// fix index when null
//							if (entryRoadArray[entryIndex] == null) {
//								entryIndex = 0;
//							}
//						}
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						LogFileManager.logWarning("Interrupted exception thrown in Junction run");
//						LogFileManager.logError(e.getMessage());
//					}
//					printLockedResource(exitRoadArray[exitIndex]);
//
//				}
//				System.out.println(name + " is unlocking exit: " + exitRoadArray[exitIndex].name);
//				printLockedResource(entryRoadArray[entryIndex]);
//			}
//
//			System.out.println(name + " is unlocking entry: " + entryRoadArray[entryIndex].name);
//			try {
//				Thread.sleep(1000 / carsPerMinute);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}

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
