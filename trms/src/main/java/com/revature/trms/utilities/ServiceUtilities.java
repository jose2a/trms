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
import com.revature.trms.services.InformationRequiredService;
import com.revature.trms.services.InformationRequiredServiceImpl;
import com.revature.trms.services.ReasonDeniedService;
import com.revature.trms.services.ReasonDeniedServiceImpl;
import com.revature.trms.services.ReasonExceedingService;
import com.revature.trms.services.ReasonExceedingServiceImpl;

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
	 * Creating a new instance of EventTypeService.
	 * 
	 * @return EventTypeService
	 */
	public static EventTypeService getEventTypeService() {
		return new EventTypeServiceImpl();
	}

	/**
	 * Creating a new instance of GradingFormatService.
	 * 
	 * @return GradingFormatService
	 */
	public static GradingFormatService getGradingFormatService() {
		return new GradingFormatServiceImpl();
	}

	/**
	 * Creating a new instance of InformationRequiredService.
	 * 
	 * @return InformationRequiredService
	 */
	public static InformationRequiredService getInformationRequiredService() {
		return new InformationRequiredServiceImpl();
	}

	/**
	 * Creating a new instance of ReasonDeniedService.
	 * 
	 * @return ReasonDeniedService
	 */
	public static ReasonDeniedService getReasonDeniedService() {
		return new ReasonDeniedServiceImpl();
	}

	/**
	 * Creating a new instance of ReasonExceedingService.
	 * 
	 * @return ReasonExceedingService
	 */
	public static ReasonExceedingService getReasonExceedingService() {
		return new ReasonExceedingServiceImpl();
	}
}
