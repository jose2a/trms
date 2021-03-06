package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.Attachment;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class AttachmentDAOImpl extends BaseDAO implements AttachmentDAO {

	@Override
	public boolean addAttachment(Attachment attachment) {
		LogUtilities.trace("addAttachment");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO public.attachment(file_name, date_submitted, file_content, attachment_type_id, event_id, content_type)"
					+ " VALUES (?, ?, ?, ?, ?, ?);";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, attachment.getFileName());
			ps.setDate(2, attachment.getDateSubmitted());
			ps.setBytes(3, attachment.getFileContent());
			ps.setInt(4, attachment.getDocumentType().getValue());
			ps.setInt(5, attachment.getEventId());
			ps.setString(6, attachment.getContentType());

			if (ps.executeUpdate() != 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					LogUtilities.trace("Getting id for evnet");

					attachment.setAttachmentId(rs.getInt(1));
				}

				LogUtilities.trace("Attachment Id: " + attachment.getAttachmentId().toString());
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addAttachment. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean deleteAttachment(int attachmentId) {
		LogUtilities.trace("deleteAttachment. " + attachmentId);

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "DELETE FROM attachment WHERE attachment_id=?";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, attachmentId);

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Attachment deleted.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. deleteAttachment. " + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public Attachment getAttachmentById(int attachmentId) {
		LogUtilities.trace("getAttachmentById. " + attachmentId);

		Attachment attachment = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT attachment_id, file_name, date_submitted, file_content, attachment_type_id, event_id, content_type"
					+ " FROM attachment WHERE attachment_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, attachmentId);

			rs = ps.executeQuery();

			if (rs.next()) {
				attachment = new Attachment();
				ModelMapperUtilities.mapRsToAttachment(rs, attachment);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getAttachmentById. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return attachment;
	}

	@Override
	public List<Attachment> getAttachmentsByEventId(int eventId) {
		LogUtilities.trace("getAttachmentsByEventId. " + eventId);

		List<Attachment> attachments = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT attachment_id, file_name, date_submitted, file_content, attachment_type_id, event_id, content_type"
					+ " FROM attachment WHERE event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Attachment attachment = new Attachment();
				ModelMapperUtilities.mapRsToAttachment(rs, attachment);

				attachments.add(attachment);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getAttachmentsByEventId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return attachments;
	}

}
