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
		LogUtilities.trace("addReasonDenied.");

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
			LogUtilities.error("Error. addReasonDenied. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateReasonDenied(ReasonDenied reasonDenied) {
		LogUtilities.trace("updateReasonDenied.");

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
			LogUtilities.error("Error. updateReasonDenied. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public ReasonDenied getReasonDeniedByEventId(Integer eventId) {
		LogUtilities.trace("getReasonDeniedByEventId. " + eventId);

		ReasonDenied reasonDenied = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT event_id, reason FROM reason_denied WHERE event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {
				reasonDenied = new ReasonDenied();
				ModelMapperUtilities.mapRsToReasonDenied(rs, reasonDenied);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getReasonDeniedByEventId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return reasonDenied;
	}

}
