package com.jeeplus.common.security.lisence;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class TestThread {
	public static void main(String[] args) {
		int threadNum = 5;
		CountDownLatch threadSignal = new CountDownLatch(threadNum);//初始化countDown  
		for (int ii = 0; ii < threadNum; ii++) {//开threadNum个线程  
//		final Iterator<String> itt = it.get(ii);  
		Thread t = new ImportThread(threadSignal);  
		t.start();  
		}  
		try {
			threadSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//等待所有子线程执行完  
		System.out.println(Thread.currentThread().getName() + "结束.");//打印结束标记 
	}
}
