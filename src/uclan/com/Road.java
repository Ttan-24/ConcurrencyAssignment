package uclan.com;

import java.util.ArrayList;

// road sections acts as a passive buffer
// acts as queue data structure
public class Road {

	// array first on first out
	private static int[] roadArray;
	private final int front;
	private static int back;
	private final int maxSize;

	Road(int _maxSize) {
		front = 0;
		back = 0;
		maxSize = _maxSize;
		roadArray = new int[maxSize]; // making a new array to specify the maxSize
	}
	// add

	public void add(int car) {
		// check if the array is full
		if (back == maxSize) {
			System.out.print("Array is full");
		} else {
			// Push
			roadArray[back + 1] = car;
			back++;
		}
	}

	public void remove() {
		for (int i = 0; i < back - 1; i++) {
			roadArray[i] = roadArray[i + 1];
		}
		back--;
	}

	public void display() {
		for (int i = 0; i < back; i++) {
			System.out.print(roadArray[i]);
		}
	}

	// remove

	// create an array
	// store the car objects in the array as they pass in and out (capacity
	// according
	// to the configuration of the simulation)

	ArrayList<Vehicle> CarList = new ArrayList<Vehicle>();

	// circular buffer enforcing a strict ordering to the addition and removal of
	// cars
	// check if the CarList is empty by the junction and the entrypoint
	public Boolean hasSpace() { // isFull
		// is carList size less than the max number
		return false;
	}

	// if there is hasspace true then add the car to make the size equal to the max
	// number
	// if not then keep adding cars

	public Boolean hasCar() { // to check if there is anything on the road to come into the junction or the
								// car park
		return false;
	}

	// thread-safe

	// check if there is space and then gonna call the below function addCar
	public void addCar(Vehicle car) {

	}
}
