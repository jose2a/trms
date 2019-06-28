package com.revature.trms.utilities;

import com.revature.trms.daos.AttachmentDAO;
import com.revature.trms.daos.AttachmentDAOImpl;
import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.daos.EmployeeDAOImpl;
import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.daos.EmployeeTypeDAOImpl;
import com.revature.trms.daos.EventDAO;
import com.revature.trms.daos.EventDAOImpl;
import com.revature.trms.daos.EventTypeDAO;
import com.revature.trms.daos.EventTypeDAOImpl;
import com.revature.trms.daos.GradingFormatDAO;
import com.revature.trms.daos.GradingFormatDAOImpl;
import com.revature.trms.daos.InformationRequiredDAO;
import com.revature.trms.daos.InformationRequiredDAOImpl;
import com.revature.trms.daos.ReasonDeniedDAO;
import com.revature.trms.daos.ReasonDeniedDAOImpl;
import com.revature.trms.daos.ReasonExceedingDAO;
import com.revature.trms.daos.ReasonExceedingDAOImpl;

public class DAOUtilities {

	/**
	 * Creates a new instance of AttachmentDAO.
	 * @return AttachmentDAO
	 */
	public static AttachmentDAO getAttachmentDAO() {
		return new AttachmentDAOImpl();
	}

	/**
	 * Creates a new instance of EmployeeDAO.
	 * @return EmployeeDAO
	 */
	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}

	/**
	 * Creates a new instance of EmployeeTypeDAO.
	 * @return EmployeeTypeDAO
	 */
	public static EmployeeTypeDAO getEmployeeTypeDAO() {
		return new EmployeeTypeDAOImpl();
	}
	
	/**
	 * Creates a new instance of EventDAO.
	 * @return EventDAO
	 */
	public static EventDAO geEventDAO() {
		return new EventDAOImpl();
	}
	
	/**
	 * Creates a new instance of EventTypeDAO.
	 * @return EventTypeDAO
	 */
	public static EventTypeDAO geEventTypeDAO() {
		return new EventTypeDAOImpl();
	}
	
	/**
	 * Creates a new instance of GradingFormatDAO.
	 * @return GradingFormatDAO
	 */
	public static GradingFormatDAO getGradingFormatDAO() {
		return new GradingFormatDAOImpl();
	}
	
	/**
	 * Creates a new instance of InformationRequiredDAO.
	 * @return InformationRequiredDAO
	 */
	public static InformationRequiredDAO getInformationRequireDAO() {
		return new InformationRequiredDAOImpl();
	}
	
	/**
	 * Creates a new instance of ReasonDeniedDAO.
	 * @return ReasonDeniedDAO
	 */
	public static ReasonDeniedDAO getReasonDeniedDAO() {
		return new ReasonDeniedDAOImpl();
	}
	
	/**
	 * Creates a new instance of ReasonExceeding.
	 * @return ReasonExceeding
	 */
	public static ReasonExceedingDAO getReasonExceeding() {
		return new ReasonExceedingDAOImpl();
	}

}
