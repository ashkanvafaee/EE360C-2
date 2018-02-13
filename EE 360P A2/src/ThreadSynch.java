/*
 * //UT-EID1= av28837
 * //UT-EID2= rg36763
 * 
 */
import java.util.concurrent.Semaphore; // for implementation using Semaphores

public class ThreadSynch {
	
	public Semaphore bs_enter;			// Entry Semaphore
	public Semaphore bs_process;		// Semaphore to lock processes
	public Semaphore bs_leave;			// Exit Semaphore
	public int size;
	public int count;

	
	
	public ThreadSynch(int parties) {	
		size = parties;
		count = parties;
		bs_enter = new Semaphore(1);			
		bs_process = new Semaphore(0);	
		bs_leave = new Semaphore((size*-1) + 2);
	}
	
	public int await() throws InterruptedException {
    
		bs_enter.acquire();

		count--;
		
		if(count != 0) {
			bs_enter.release();

			bs_process.acquire();
			bs_leave.release();

			return count;
		}
		else {
			
			
				
			for(int i=0; i<size-1; i++) {			// Number of processes that call bs_process is size-1
				bs_process.release();
			}
			
			
			bs_leave.acquire();						// Waiting for all processes to have called bs_process.acquire()
			
			
			// Resetting variables
			count = size;
			bs_process = new Semaphore(0);
			bs_leave = new Semaphore(size*-1 + 2);


			bs_enter.release();
			return(0);
		}
		
	}
}
