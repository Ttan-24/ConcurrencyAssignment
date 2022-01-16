package uclan.com;

public class Clock extends Thread {

	private int count = 0;
	public int displayNumber = 10;

	public CarPark carPark;

	public void run() {
		while (true) {
			count++;
			if (count == displayNumber) {
				System.out.println("Time: " + time() + " - Shopping Centre: " + carPark.carSpaces() + "\n              "
						+ "Industrial Park: " + carPark.carSpaces());
				displayNumber += 10;
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
