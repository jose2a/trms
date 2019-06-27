package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class ReasonDeniedDAOImpl extends BaseDAO implements ReasonDeniedDAO {

	@Override
	public boolean addReasonDenied(ReasonDenied reasonDenied) {
		LogUtilities.trace("Inserting Reason Denied.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO reason_denied (event_id, reason) VALUES(?, ?)";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, reasonDenied.getEventId());
			ps.setString(2, reasonDenied.getReason());

			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error inserting the Reason Denied." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateReasonDenied(ReasonDenied reasonDenied) {
		LogUtilities.trace("Updating Reason Denied.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE reason_denied SET reason=? WHERE event_id=?";

			ps = conn.prepareStatement(sql);

			ps.setString(1, reasonDenied.getReason());
			ps.setInt(2, reasonDenied.getEventId());

			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error updating the Reason Denied." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public ReasonDenied getReasonDeniedByEventId(Integer eventId) {
		LogUtilities.trace("Getting Reason Denied by event Id. " + eventId);

		ReasonDenied reasonDenied = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT event_id, reason FROM reason_denied " + "WHERE event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {
				reasonDenied = new ReasonDenied();
				ModelMapperUtilities.mapRsToReasonDenied(rs, reasonDenied);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting Reason Denied." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return reasonDenied;
	}

}
