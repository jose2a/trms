package com.revature.trms.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.AttachmentDocType;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

@MultipartConfig
public class AttachmentUploadServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482661353345410340L;

	private EventService eventService;

	// <url-pattern>/attachment/upload</url-pattern>
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("AttachmentUploadServlet - post");

		eventService = ServiceUtilities.getEventService();

		String id = request.getParameter("eventId");
		String finalGrade = request.getParameter("finalGrade");

		int eventId = Integer.parseInt(id);
		String documentTypeStr = request.getParameter("documentType");

		AttachmentDocType docType = AttachmentDocType.valueOf(Integer.parseInt(documentTypeStr));

		Part content = request.getPart("fileContent");

		Attachment attachment = new Attachment();
		attachment.setFileName(content.getSubmittedFileName());
		attachment.setDateSubmitted(Date.valueOf(LocalDate.now()));
		attachment.setEventId(eventId);
		attachment.setDocumentType(docType);
		attachment.setContentType(content.getContentType());

		InputStream is = null;
		ByteArrayOutputStream os = null;

		try {
			is = content.getInputStream();
			os = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			while (is.read(buffer) != -1) {
				os.write(buffer);
			}

			attachment.setFileContent(os.toByteArray());

		} catch (IOException e) {
			LogUtilities.error("Could not upload file!");
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}

		setJsonResponseHeaders(response);

		try {
			boolean isSuccess = false;

			if (attachment.getDocumentType() == AttachmentDocType.Direct_Supervisor_Approval
					|| attachment.getDocumentType() == AttachmentDocType.Department_Head_Approval) {

				eventService.uploadEventApprovalAttachment(eventId, attachment);
				isSuccess = true;
			} else if (attachment.getDocumentType() == AttachmentDocType.Presentation_Document) {
				isSuccess = eventService.uploadEventPresentation(eventId, attachment);
			} else if (attachment.getDocumentType() == AttachmentDocType.Grade_Document) {

				isSuccess = eventService.uploadFinalGrade(eventId, finalGrade, attachment);
			}

			if (isSuccess) {
				attachment.setFileContent(null);

				response.getWriter().append(objectMapper.writeValueAsString(attachment));
				return;
			}

		} catch (PojoValidationException e) {
			LogUtilities.trace("AttachmentUploadServlet - Validation errors");

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));
		} catch (IllegalParameterException e) {
			LogUtilities.error("AttachmentUploadServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (NotFoundRecordException e) {
			LogUtilities.trace("AttachmentUploadServlet - " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		if (employeeTypes.contains(EmployeeType.Direct_Supervisor)
				|| employeeTypes.contains(EmployeeType.Head_Department)
				|| employeeTypes.contains(EmployeeType.Benefits_Coordinator)) {
			return false;
		}

		return true;
	}

}