
public class ReadWriteTester extends Thread {

	public static FairReadWriteLock lock = new FairReadWriteLock();

	public static void main(String[] args) {

		Thread t1 = new Thread(new ReadWriteTester());
		t1.setName("t1");

		Thread t2 = new Thread(new ReadWriteTester());
		t2.setName("t2");
		
		Thread t3 = new Thread(new ReadWriteTester());
		t3.setName("t3");
		
		Thread t4 = new Thread(new ReadWriteTester());
		t4.setName("t4");

		t1.start();
		t2.start();
		t3.start();
		t4.start();

	}

	public void run() {

		if (Thread.currentThread().getName().contains("t1")) {

			for(int i=0; i<500; i++) {
				lock.beginRead();
				lock.endRead();
			}



		} else if(Thread.currentThread().getName().contains("t2")){
			
			for(int i=0; i<500; i++) {
				lock.beginRead();
				lock.endRead();
			}

			
	

		}
		else if(Thread.currentThread().getName().contains("t3")) {
			

			
			for(int i=0; i<500; i++) {
				lock.beginWrite();
				lock.endWrite();
			}
			
			
		}
		
		else if (Thread.currentThread().getName().contains("t4")) {
			for(int i=0; i<500; i++) {
				lock.beginWrite();
				lock.endWrite();
			}
		}

	}
}
