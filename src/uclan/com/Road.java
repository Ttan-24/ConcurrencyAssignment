package uclan.com;

import java.util.ArrayList;

// road sections acts as a passive buffer
// acts as queue data structure
public class Road {

	public Clock roadClock;
	// array first on first out
	private Vehicle[] roadArray;
	private final int front;
	public int back;
	public final int maxSize;

	Road(int _maxSize) {
		front = 0;
		back = -1;
		maxSize = _maxSize;
		roadArray = new Vehicle[maxSize]; // making a new array to specify the maxSize
	}
	// add

	public synchronized void add(Vehicle car) throws InterruptedException {
		// check if the array is full
		// if (back == maxSize) {
		// System.out.println("Road: Array is full");
		// wait();
		// Thread.sleep(500);
		// notifyAll();
		// } else {
		// Push
		roadArray[back + 1] = car;
		back++;
		System.out.println("Time: " + roadClock.time() + " - Road: Added Car " + car.id);
		// Thread.sleep(500);
		// }
	}

	public synchronized Vehicle remove() throws InterruptedException {

		// if (IsEmpty()) {
		// do not remove if there is nothing there to remove
		// wait();
		// return null;
		// } else {
		Vehicle car = roadArray[front];
		for (int i = 0; i < back; i++) {
			roadArray[i] = roadArray[i + 1];

			// Thread.sleep(500);
		}
		System.out.println("Time: " + roadClock.time() + " - Road: Removed Car " + car.id);
		back--;
		return car;
		// }

		// buffer is not full, notify all threads that the buffer is empty

	}

	public synchronized boolean IsEmpty() {
		return back == -1; // the back has come all over to the front
	}

	public synchronized boolean IsFull() {
		return back == maxSize - 1; // the back has come all over to the front
	}

	public synchronized void display() {
		for (int i = 0; i <= back; i++) {
			System.out.println(roadArray[i]);
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
