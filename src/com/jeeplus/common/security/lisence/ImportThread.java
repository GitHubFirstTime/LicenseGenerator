package com.jeeplus.common.security.lisence;
import java.util.concurrent.CountDownLatch;

public class ImportThread extends Thread {
	private CountDownLatch threadsSignal;

	public ImportThread(CountDownLatch threadsSignal) {
		this.threadsSignal = threadsSignal;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "开始...");
		// Do somethings
		threadsSignal.countDown();// 线程结束时计数器减1
		System.out.println(Thread.currentThread().getName() + "结束. 还有"
				+ threadsSignal.getCount() + " 个线程");
	}
}