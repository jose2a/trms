package com.revature.trms.tests;

import java.util.TimerTask;

public class ReportGenerator extends TimerTask {
	
	private HelloPrinter helloPrinter = new HelloPrinter();

	public void run() {
		System.out.println("Updating pending events");
		
		helloPrinter.printHello();
	}

}
