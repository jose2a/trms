package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.services.AttachmentService;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventServlet - get");

		eventService = ServiceUtilities.getEventService();

		if (pathInfoParts.length > 0 && pathInfoParts[1].trim().equals("employee")) {
			int eventId = Integer.parseInt(pathInfoParts[2]);

			getByEventId(eventId, request, response);
		} else if (pathInfoParts.length > 0 && !pathInfoParts[1].trim().equals("")) {
			int attachmentId = Integer.parseInt(pathInfoParts[1]);

			getByEmployeeId(attachmentId, request, response);
		}
	}

	private void getByEmployeeId(int attachmentId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("EventServlet - getByEmployeeId");

	}

	private void getByEventId(int eventId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("EventServlet - getByEventId");

//		String attachmentsString = "";
//
//		List<Attachment> attachments;
//
//		try {
//			attachments = eventService.getAttachmentsByEventId(eventId);
//			attachmentsString = objectMapper.writeValueAsString(attachments);
//		} catch (IllegalParameterException e) {
//			LogUtilities.error("Error. AttachmentServlet. " + e.getMessage());
//		}
//
//		response.getWriter().append(attachmentsString);
		return;

	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}
