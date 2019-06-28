package com.revature.trms.utilities;

import com.revature.trms.services.AttachmentService;
import com.revature.trms.services.AttachmentServiceImpl;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeServiceImpl;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.services.EmployeeTypeServiceImpl;
import com.revature.trms.services.EventTypeService;
import com.revature.trms.services.EventTypeServiceImpl;
import com.revature.trms.services.GradingFormatService;
import com.revature.trms.services.GradingFormatServiceImpl;

public class ServiceUtilities {

	/**
	 * Creating a new instance of AttachmentService.
	 * 
	 * @return AttachmentService
	 */
	public static AttachmentService getAttachmentService() {
		return new AttachmentServiceImpl();
	}

	/**
	 * Creating a new instance of EmployeeService.
	 * 
	 * @return EmployeeService
	 */
	public static EmployeeService getEmployeeService() {
		return new EmployeeServiceImpl();
	}

	/**
	 * Creating a new instance of EmployeeTypeService.
	 * 
	 * @return EmployeeTypeService
	 */
	public static EmployeeTypeService geEmployeeTypeService() {
		return new EmployeeTypeServiceImpl();
	}

	/**
	 * Cresting a new instance of EventTypeService.
	 * 
	 * @return EventTypeService
	 */
	public static EventTypeService getEventTypeService() {
		return new EventTypeServiceImpl();
	}

	/**
	 * Cresting a new instance of GradingFormatService.
	 * 
	 * @return GradingFormatService
	 */
	public static GradingFormatService getGradingFormatService() {
		return new GradingFormatServiceImpl();
	}
}
