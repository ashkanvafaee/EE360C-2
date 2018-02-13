
public class ReadWriteTester extends Thread {

	public static FairReadWriteLock lock = new FairReadWriteLock();

	public static void main(String[] args) {

		Thread t1 = new Thread(new ReadWriteTester());
		t1.setName("t1");

		Thread t2 = new Thread(new ReadWriteTester());
		t2.setName("t2");
		
		Thread t3 = new Thread(new ReadWriteTester());
		t3.setName("t3");

		t1.start();
		t2.start();
		t3.start();

	}

	public void run() {

		if (Thread.currentThread().getName().contains("t1")) {

			for(int i=0; i<100; i++) {
				lock.beginRead();
				System.out.println("read");
				lock.endRead();
			}



		} else if(Thread.currentThread().getName().contains("t2")){
			
			for(int i=0; i<100; i++) {
				lock.beginRead();
				System.out.println("read");
				lock.endRead();
			}

			
	

		}
		else if(Thread.currentThread().getName().contains("t3")) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			lock.beginWrite();
			System.out.println("write");
			lock.endRead();
			
			
		}

	}
}
