import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * //UT-EID1= av28837
 * //UT-EID2= rg36763
 * 
 */
public class PriorityQueue {
	int maxSize;
	int currentSize = 0;
	Node head = null;
	Node tail = null;
	
	ReentrantLock monitorLock = new ReentrantLock();
	Condition notFull = monitorLock.newCondition();
	Condition notEmpty = monitorLock.newCondition();
	
	class Node{
		public String name;
		public int priority;	
		public Node next = null;		// Pointer to next node
	}

	public PriorityQueue(int maxSize) {
        // Creates a Priority queue with maximum allowed size as capacity
		this.maxSize = maxSize;
	}

	public int add(String name, int priority) {
		
		monitorLock.lock();
		// Checking for duplicate names
		for(Node i = head; i!= null; i=i.next) {
			if(i.name.equals(name)) {
				return -1;
			}
		}
		
		currentSize++;
		
		while(currentSize > maxSize) {
			try {
				notFull.await();
			} catch (InterruptedException e) {
			}
		}
		
		
		Node temp = new Node();
		temp.name = name;
		temp.priority = priority;
		
		
		int result = 0;
		// List Empty
		if(tail == null) {
			tail = temp;
			head = tail;
		}
		else {
			
			boolean added = false;
			for(Node i = head; i != null; i=i.next) {
				if(priority > i.priority) {
					if(i == head) {
						temp.next = head;
						head = temp;
						added = true;
						break;
					}
					else {
						temp.next = i;
						i = temp;
						added = true;
						break;
					}
				}
				result++;
			}
			
			// Add to end of queue
			if(!added) {
				result++;
				tail.next = temp;
				tail = temp;
			}
			
			notEmpty.signal();
			monitorLock.unlock();
		}
		return result;
        // Adds the name with its priority to this queue.
        // Returns the current position in the list where the name was inserted;
        // otherwise, returns -1 if the name is already present in the list.
        // This method blocks when the list is full.
	}

	public int search(String name) {
		int result = 0;
		boolean found = false;
		for(Node i = head; i != null; i = i.next) {
			if(i.name.equals(name)) {
				found = true;
				break;
			}
			result++;
		}
		
		if(found) {
			return result;
		}
		else {
			return -1;
		}

        // Returns the position of the name in the list;
        // otherwise, returns -1 if the name is not found.
	}

	public String getFirst() {
		monitorLock.lock();
		
		while(currentSize == 0) {
			try {
				notEmpty.await();
			} catch (InterruptedException e) {
			}
		}
		
		String temp = head.name;
		head = head.next;
		if(head == null) {
			tail = null;	//Resetting tail to correct position
		}
		
		notFull.signal();
			
		monitorLock.unlock();
		return null;
        // Retrieves and removes the name with the highest priority in the list,
        // or blocks the thread if the list is empty.
	}
}

