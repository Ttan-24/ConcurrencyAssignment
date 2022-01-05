package uclan.com;

import java.util.ArrayList;

public class EntryPoint extends Thread { // aka producer

	Road road;
	public int idCount = 1;
	public String name;

	EntryPoint(String _name) {
		name = _name;
	}

	void produce() throws InterruptedException {
		Vehicle car = new Vehicle(idCount++);
		road.add(car);
		System.out.println("EntryPoint: Car " + car.id + " produced by EntryPoint " + name);
	}

	public void run() {
		for (int i = 0; i < 10; i++) {

			// System.out.println("Hi I am a EntryPoint!");
			try {
				produce();
				System.out.println();
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
