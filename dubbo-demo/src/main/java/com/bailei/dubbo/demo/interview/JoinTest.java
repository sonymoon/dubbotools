package com.bailei.dubbo.demo.interview;

public class JoinTest {

	public static void main(String[] args) throws InterruptedException {
		Thread A = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("a is running  " + i);
				}
			}
		});


		//Thread.sleep(10);

		Thread B = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("b is running  " + i);
				}
			}
		});
		B.start();
		B.join();
		
		A.start();
	}
}
