package com.revature.trms.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.AttachmentDocType;
import com.revature.trms.services.AttachmentService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

@MultipartConfig
public class AttachmentUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482661353345410340L;

	private AttachmentService attachmentService;
	private ObjectMapper objectMapper = new ObjectMapper();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LogUtilities.trace("AttachmentServlet - post");

		attachmentService = ServiceUtilities.getAttachmentService();

		String id = request.getParameter("eventId");

		LogUtilities.trace("id: " + id);

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

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			boolean isSuccess = attachmentService.addAttachment(attachment);

			if (isSuccess) {
				attachment.setFileContent(null);

				response.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().append(objectMapper.writeValueAsString(attachment));
				return;
			}

		} catch (PojoValidationException e) {
			LogUtilities.trace("AttachmentServlet - Validation errors");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

		} catch (IllegalParameterException e) {
			LogUtilities.error("AttachmentUploadServlet. " + e.getMessage());
		}

	}
}