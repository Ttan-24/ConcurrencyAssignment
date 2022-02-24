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
	Thread owner;
	boolean IsInterrupt = false;

	// constructor
	Alarm(int _maxCount, Thread thread, boolean _IsInterrupt) {
		maxCount = _maxCount;
		count = maxCount;
		owner = thread;
		IsInterrupt = _IsInterrupt;
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
				// wake if alarm ended
				if (count <= 0 && IsInterrupt) {
					owner.interrupt();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
