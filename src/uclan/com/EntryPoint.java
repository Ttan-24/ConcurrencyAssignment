package uclan.com;

import java.util.ArrayList;

public class EntryPoint extends Thread { // aka producer

	public Clock entryPointClock;
	Road road;
	public int idCount = 1;
	public String name;

	EntryPoint(String _name) {
		name = _name;
	}

	public void run() {
		while (true) {
			synchronized (road) {
				while (road.back == road.maxSize - 1) {
					try {
						System.out.println("Queue is full, " + "Producer thread waiting for "
								+ "consumer to take something from queue");
						road.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					// System.out.println("Produced at: " + entryPointClock.time());
					produce();
					road.notify();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

//		for (int i = 0; i < 10; i++) {
//
//			// System.out.println("Hi I am a EntryPoint!");
//			try {
//				produce();
//				System.out.println();
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	public void produce() throws InterruptedException {
		Vehicle car = new Vehicle(idCount++, entryPointClock.time());
		System.out.println(
				"Time: " + entryPointClock.time() + " - EntryPoint: Car " + car.id + " produced by EntryPoint " + name);
		road.add(car);
	}

	ArrayList<CarPark> destinationList = new ArrayList<CarPark>(); //////// dont use arraylist or any java collection
																	//////// library

	int preDeterminedRate;
	// array of vehicles generated

	void generateVehicleObjects() {
		// generate Vehicle objects with destinations at a pre-determined rate, and feed
		// them into the road network as long as space is available on the road.
	}

	// give the car a timestamp when it entered the town
	void carTimestamp() {

	}

	// destinations of cars should be weighted randomly
	CarPark getRandomDestination() {
		return null;
	}
}
