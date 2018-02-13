
public class Main extends Thread{
	
	public static ThreadSynch ts = new ThreadSynch (3);

	public static void main(String[] args) {
		Main temp = new Main();

		Thread t1 = new Thread(temp);
		Thread t2 = new Thread(temp);
		Thread t3 = new Thread(temp);
		Thread t4 = new Thread(temp);
		Thread t5 = new Thread(temp);
		Thread t6 = new Thread(temp);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		

	}
	
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " entered");
		try {
			ts.await();
			System.out.println(Thread.currentThread().getName() + " is done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
