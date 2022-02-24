package uclan.com;

// road sections acts as a passive buffer
// acts as queue data structure
public class Road {

	// member variables
	public Clock roadClock;
	// array first on first out
	private Vehicle[] roadArray;
	private final int front;
	public int back;
	public final int maxSize;
	public String name;

	// constructor
	Road(int _maxSize, String _name) {
		front = 0;
		back = -1;
		maxSize = _maxSize;
		roadArray = new Vehicle[maxSize]; // making a new array to specify the maxSize
		name = _name;
	}

	// add vehicle to the road array
	public synchronized void add(Vehicle car) {
		roadArray[back + 1] = car;
		back++;
		LogFileManager.writeToLog("Time: " + roadClock.time() + " - Road " + name + " : Added Car " + car.id);
	}

	// remove vehicle from the road array
	public synchronized Vehicle remove() {

		Vehicle car = roadArray[front];
		for (int i = 0; i < back; i++) {
			roadArray[i] = roadArray[i + 1];
		}
		LogFileManager.writeToLog("Time: " + roadClock.time() + " - Road " + name + " : Removed Car " + car.id);
		back--;
		return car;
	}

	// get first car - for consuming in the Junction
	public synchronized Vehicle getFrontCar() {
		Vehicle car = roadArray[front];
		return car;
	}

	// if the road is empty
	public synchronized boolean IsEmpty() {
		return back == -1; // the back has come all over to the front
	}

	// if the road has car
	public synchronized boolean hasCar() {
		return !IsEmpty(); // the back has come all over to the front
	}

	// if the road is full
	public synchronized boolean IsFull() {
		return back == maxSize - 1; // the back has come all over to the front
	}

	// if the road has space
	public synchronized boolean hasSpace() {
		return !IsFull();
	}

	// to get cars queued in the road
	public int carsQueued() {
		return back + 1;
	}

	// to display in logs
	public synchronized void display() {
		for (int i = 0; i <= back; i++) {
			System.out.println(roadArray[i]);
		}
	}
}
