package com.revature.trms.listeners;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.revature.trms.services.EventService;
import com.revature.trms.utilities.ServiceUtilities;

public class AutoApproveEventListener implements ServletContextListener {

	private EventService eventService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		eventService = ServiceUtilities.getEventService();

		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.DAY_OF_WEEK);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// Schedule to run every day at midnight
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				eventService.autoApproveEvents();

			}
		}, date.getTime(), 1000 * 60 * 60 * 24); // running every day
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
