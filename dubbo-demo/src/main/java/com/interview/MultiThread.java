package com.interview;


public class MultiThread implements Runnable {

	private Object prev;

	private Object self;

	private String name;

	/**
	 * @param prev
	 * @param self
	 * @param name
	 */
	public MultiThread(Object prev, Object self, String name) {
		super();
		this.prev = prev;
		this.self = self;
		this.name = name;
	}

	@Override
	public void run() {
		int count = 10;
		while (count > 0) {
			synchronized (this.prev) {
				synchronized (this.self) {
					System.out.println(this.name + ": " + count);
					--count;
					/*
					 * try { Thread.sleep(1000); } catch (InterruptedException
					 * e) { e.printStackTrace(); }
					 */this.self.notify();
				}

				try {
					this.prev.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Float a = 5.1f;
		new MultiThread(null, null, null).testOverloading(a);
		System.out.println(a);
	}

	public void testOverloading() {

	}

	public int testOverloading(Float a) {
		a = new Float(5.2f);
		return 0;
	}

}
