package uclan.com;

import java.util.ArrayList;

public class EntryPoint extends Thread { // aka producer

	Road road;

	void Produce() {
		Vehicle car = new Vehicle();

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
