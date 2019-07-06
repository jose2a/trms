package com.revature.trms.tests;

import java.util.Calendar;
import java.util.Timer;

public class DriverScheduler {

	public static void main(String[] args) {
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		date.set(Calendar.HOUR, 9);
		date.set(Calendar.MINUTE, 7);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// Schedule to run every Sunday in midnight
		timer.schedule(new ReportGenerator(), date.getTime(), 1000 * 60 * 2);
//		timer.schedule(new ReportGenerator(), date.getTime(), 1000 * 60 * 60 * 24 * 7);
	}

}
