package uclan.com;

import java.util.ArrayList;

public class CarPark extends Thread { // aka consumer

	Road road;
	// private final int maxSize;
	private Vehicle[] carParkArray;
	private final int front;
	private static int back;
	private final int maxSize;
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

	void consume() throws InterruptedException {
		// remove
		Vehicle car = road.remove();
		if (car == null) {
			System.out.println("CarPark cannot find car...");
			// Thread.sleep(500);
		} else {
			System.out.println("CarPark: Car " + car.id + " removed from the entry road");
			// check if the array is full
			if (back == maxSize) {
				System.out.print("CarPark: Array is full");
			} else {
				// Push
				carParkArray[back + 1] = car;
				back++;
				System.out.println("CarPark: Car " + car.id + " added to the CarPark Industrial Space");
			}
		}

		// carParkArray.add();
		System.out.println();
		// System.out.println("CarPark: Total cars in the Industrial carpark " + (back +
		// 1) + " out of 1000 spaces");
		System.out.println();
	}

	public void run() {
		for (int i = 0; i < 10; i++) {

			// System.out.println("Hi I am a Junction!");
			try {
				consume();
				System.out.println();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
