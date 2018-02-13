import java.util.ArrayList;
import java.util.HashMap;

/*
 * //UT-EID1= av28837
 * //UT-EID2= rg36763
 * 
 */

public class FairReadWriteLock {

	public int num = 0;
	public boolean reading = false, writing = false;
	public ArrayList<Long> readingTickets = new ArrayList<>();
	public ArrayList<Long> writingTickets = new ArrayList<>();
	
	public HashMap<Thread, Integer> map = new HashMap<Thread,Integer>();
                        
	public synchronized void beginRead() {int temp = num++; map.put(Thread.currentThread(), temp);
		boolean myTurn = true;
		
		long myTicket = 0;
		
		// Checking all reading Tickets
		for(int i=0; i<readingTickets.size(); i++) {
			if (myTicket < readingTickets.get(i)) {
				myTicket = readingTickets.get(i);
			}
		}
		
		// Checking all writing Tickets
		for(int i=0; i<writingTickets.size(); i++) {
			if(myTicket < writingTickets.get(i)) {
				myTicket = writingTickets.get(i);
			}
		}
		
		// Ensuring current ticket is largest
		myTicket++;	
		readingTickets.add(myTicket);
		
		// Checking if there are no other preceding writer threads
		for(int i=0; i<writingTickets.size(); i++) {
			if(myTicket > writingTickets.get(i)) {
				myTurn = false;
			}
		}
		
		
		while((writing || !myTurn) && myTicket != 1) {
			try {
				System.out.println("reader stalled***********************");
				this.wait();
				
				myTurn = true;
				
				// Checking if there are no other preceding writer threads
				for(int i=0; i<writingTickets.size(); i++) {
					if(myTicket > writingTickets.get(i)) {
						myTurn = false;
					}
				}
				
				
				
			} catch (InterruptedException e) {
			}
			}
		
		reading = true;
		
		System.out.println("reader " + temp + " entered");
		
	}
	
	public synchronized void endRead() {
		if(readingTickets.size() > 0) {
			readingTickets.remove(0);
		}
		reading = false;
		this.notifyAll();
		
		System.out.println("reader " + map.get(Thread.currentThread()) + " exited");
	}
	
	public synchronized void beginWrite() {int temp = num++; map.put(Thread.currentThread(), temp);
		
		boolean myTurn = false;
		
		long myTicket = 0;
		// Checking all reading Tickets
		for(int i=0; i<readingTickets.size(); i++) {
			if (myTicket < readingTickets.get(i)) {
				myTicket = readingTickets.get(i);
			}
		}
		
		// Checking all writing Tickets
		for(int i=0; i<writingTickets.size(); i++) {
			if(myTicket < writingTickets.get(i)) {
				myTicket = writingTickets.get(i);
			}
		}
		
		// Ensuring current ticket is largest
		myTicket++;	
		writingTickets.add(myTicket);
		
		
		
		
		while((writing || reading || !myTurn) && myTicket != 1) {
			try {
				
				System.out.println("writer stalled***********************");
				this.wait();
				
				myTurn = true;
				
				// Checking if there are no other preceding writer threads
				for(int i=0; i<writingTickets.size(); i++) {
					if(myTicket > writingTickets.get(i)) {
						myTurn = false;
					}
				}
				
				// Checking if there are no preceding reader threads
				for(int i=0; i<readingTickets.size(); i++) {
					if(myTicket > readingTickets.get(i)) {
						myTurn = false;
					}
				}
				
				
			} catch (InterruptedException e) {
			}
		}
		
		writing = true;
		System.out.println("writer " + temp + " entered");
	}
	public synchronized void endWrite() {
		
		if(writingTickets.size() > 0) {
			writingTickets.remove(0);
		}

		writing = false;
		this.notifyAll();
		System.out.println("writer " + map.get(Thread.currentThread()) + " exited");
	}
}
	
