package com.revature.trms.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.AttachmentService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class AttachmentServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private AttachmentService attachmentService;

	// <url-pattern>/attachment/*</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("AttachmentServlet - get");

		attachmentService = ServiceUtilities.getAttachmentService();

		if (pathInfoParts.length > 0 && pathInfoParts[1].trim().equals("event")) {
			int eventId = Integer.parseInt(pathInfoParts[2]);

			getByEventId(eventId, request, response);
		} else if (pathInfoParts.length > 0 && !pathInfoParts[1].trim().equals("")) {
			int attachmentId = Integer.parseInt(pathInfoParts[1]);

			getByAttachmentId(attachmentId, request, response);
		}
	}

	// <url-pattern>/attachment/event</url-pattern>
	private void getByEventId(int eventId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("AttachmentServlet - getByEventId");

		List<Attachment> attachments;

		try {
			attachments = attachmentService.getAttachmentsByEventId(eventId);
			removeFileContent(attachments);
			response.getWriter().append(objectMapper.writeValueAsString(attachments));
			
			LogUtilities.trace("Attachments for event " + eventId);
			
			return;
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. AttachmentServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

	}

	/**
	 * Remove file content from attachment not need to send it at this point.
	 * 
	 * @param attachments The attachment list
	 */
	private void removeFileContent(List<Attachment> attachments) {
		LogUtilities.trace("Removing binary file from the attachments");

		for (Attachment attachment : attachments) {
			attachment.setFileContent(null);
		}
	}

	// <url-pattern>/attachment/*</url-pattern>
	private void getByAttachmentId(int attachmentId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("AttachmentServlet - getByAttachmentId");

		Attachment attachment = null;

		try {
			attachment = attachmentService.getAttachmentById(attachmentId);

			if (attachment == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. AttachmentServlet. getByAttachmentId " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.setContentType(attachment.getContentType());

		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());

		// Create the input stream (IN to the app FROM the attachment)
		InputStream is = new ByteArrayInputStream(attachment.getFileContent());

		// Create the output stream (OUT of the app TO the client)
		OutputStream os = response.getOutputStream();

		// We're going to read and write 1KB at a time
		byte[] buffer = new byte[1024];

		// Reading returns -1 when there's no more data left to read.
		while (is.read(buffer) != -1) {
			os.write(buffer);
		}

		os.flush();
		os.close();
		is.close();
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}
