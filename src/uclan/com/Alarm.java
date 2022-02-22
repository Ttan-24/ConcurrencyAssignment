package uclan.com;

public class Alarm extends Thread {
	// <summary>
	// Counts down from a given number and when it reaches 0 it sends a message to
	// the object that owns it
	// </summary>

	int count;
	int maxCount;
	Junction junction;
	boolean hasEnded = false;

	// constructor
	Alarm(int _maxCount) {
		maxCount = _maxCount;
		count = maxCount;
	}

	// reset the alarm
	public void reset() {
		count = maxCount;
		hasEnded = false;
	}

	// run method - decrements count until it reaches zero and then ends
	public void run() {
		while (true) {
			try {
				count--;
				Thread.sleep(10);

				if (count == 0) {
					// send message
					hasEnded = true;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
