package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.Attachment;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class AttachmentDAOImpl extends BaseDAO implements AttachmentDAO {

	@Override
	public boolean addAttachment(Attachment attachment) {
		LogUtilities.trace("Inserting attachment.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO attachment (file_name, date_submitted, approval_doc, file_content, approval_stage_id, event_id) "
					+ "VALUES(?, ?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(sql);

			ps.setString(1, attachment.getFileName());
			ps.setDate(2, Date.valueOf(attachment.getDateSubmitted()));
			ps.setBoolean(3, attachment.isApprovalDoc());
			ps.setBytes(4, attachment.getFileContent());
			ps.setInt(5, attachment.getApprovalStage().getValue());
			ps.setInt(6, attachment.getEventId());

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Attachment inserted.");
				
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error adding the attachment." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean deleteAttachment(int attachmentId) {
		LogUtilities.trace("Deleting attachment: " + attachmentId);

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
			LogUtilities.error("Error deleting the attachment." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public Attachment getAttachmentById(int attachmentId) {
		LogUtilities.trace("Getting attachment by attachmentId " + attachmentId);

		Attachment attachment = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT attachment_id, file_name, date_submitted, approval_doc, file_content, approval_stage_id, event_id "
					+ "FROM attachment WHERE attachment_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, attachmentId);

			rs = ps.executeQuery();

			if (rs.next()) {
				attachment = new Attachment();
				ModelMapperUtilities.mapRsToAttachment(rs, attachment);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting attachment by id." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return attachment;
	}

	@Override
	public List<Attachment> getAttachmentsByEventId(int eventId) {
		LogUtilities.trace("Getting attachments by eventId " + eventId);

		List<Attachment> attachments = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT attachment_id, file_name, date_submitted, approval_doc, file_content, approval_stage_id, event_id "
					+ "FROM attachment WHERE event_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Attachment attachment = new Attachment();
				ModelMapperUtilities.mapRsToAttachment(rs, attachment);
				
				attachments.add(attachment);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting attachment by eventId." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return attachments;
	}

}
