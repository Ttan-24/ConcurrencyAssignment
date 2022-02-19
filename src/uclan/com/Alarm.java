package uclan.com;

public class Alarm extends Thread {
	// counts down from a given number and when it reaches 0 it sends a message to
	// the fucking object that owns it

	int count;
	Junction junction;
	boolean hasEnded = false;

	Alarm(int _count) {
		count = _count;
	}

	public void run() {
		while (true) {
			try {
				count--;
				Thread.sleep(100);

				if (count == 0) {
					// send message
					// System.out.println("Alarm ended");
					hasEnded = true;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
