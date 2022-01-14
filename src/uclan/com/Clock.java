package uclan.com;

public class Clock extends Thread {

	public int count = 0;

	public void run() {
		while (true) {
			count++;
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

}
