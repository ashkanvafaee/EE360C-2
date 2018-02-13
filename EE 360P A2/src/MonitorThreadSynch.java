import java.util.concurrent.locks.*;

/*
 * //UT-EID1= av28837
 * //UT-EID2= rg36763
 * 
 */

public class MonitorThreadSynch {

	public int count;
	public int size;

	public MonitorThreadSynch(int parties) {
		count = parties;
		size = parties;
	}

	public int await() throws InterruptedException {

		synchronized (this) {
			count--;

			if (count != 0) {
;

				this.wait();

				return count;

			} else {

				this.notifyAll();

				// Resetting variables
				count = size;

				return (0);
			}
		}
	}
}
