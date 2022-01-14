package uclan.com;

import java.util.ArrayList;

public class CarPark extends Thread { // aka consumer

	Road road;
	// private final int maxSize;
	private Vehicle[] carParkArray;
	private final int front;
	private static int back;
	private final int maxSize;
	private boolean isFullCarPark;
	// private static int size = 0;

	CarPark(int _maxSize) {
		front = 0;
		back = -1;
		maxSize = _maxSize;
		carParkArray = new Vehicle[maxSize]; // making a new array to specify the maxSize
	}
	// add

	// void add()

	int timestamp;
	ArrayList<Vehicle> Cars = new ArrayList<Vehicle>();
	Vehicle car;

	public void run() {

		while (true) {
			synchronized (road) {
				while (road.IsEmpty()) {
					System.out.println("Queue is empty," + "Consumer thread is waiting"
							+ " for producer thread to put something in queue");
					try {
						road.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				try {
					if (isFullCarPark == false) {
						consume();
						road.notify();
						Thread.sleep(1000);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void consume() throws InterruptedException {
		// remove

		Vehicle car = road.remove();
		System.out.println("CarPark: Car " + car.id + " removed from the entry road");
		// if (car == null) {
		// System.out.println("CarPark cannot find car...");
		// Thread.sleep(500);
		// } else {

		// check if the array is full
		if (back == maxSize - 1) {
			System.out.print("CarPark: Array is full");
			isFullCarPark = true;
		} else {
			// Push
			carParkArray[back + 1] = car;
			back++;
			System.out.println("CarPark: Car " + car.id + " added to the CarPark Industrial Space");
		}
		// }

		// carParkArray.add();
		System.out.println();
		// System.out.println("CarPark: Total cars in the Industrial carpark " + (back +
		// 1) + " out of 1000 spaces");
		System.out.println();
	}

}
