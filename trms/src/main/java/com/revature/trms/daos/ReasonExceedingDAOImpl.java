package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.ReasonExceeding;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class ReasonExceedingDAOImpl extends BaseDAO implements ReasonExceedingDAO {

	@Override
	public boolean addReasonExceeding(ReasonExceeding reasonExceeding) {
		LogUtilities.trace("addReasonExceeding.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO reason_exceeding (event_id, reason) VALUES(?, ?)";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, reasonExceeding.getEventId());
			ps.setString(2, reasonExceeding.getReason());

			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addReasonExceeding. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding) {
		LogUtilities.trace("updateReasonExceeding.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE reason_exceeding SET reason=? WHERE event_id=?";

			ps = conn.prepareStatement(sql);

			ps.setString(1, reasonExceeding.getReason());
			ps.setInt(2, reasonExceeding.getEventId());

			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. updateReasonExceeding. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public ReasonExceeding getReasonExceedingByEventId(Integer eventId) {
		LogUtilities.trace("getReasonExceedingByEventId. " + eventId);

		ReasonExceeding reasonExceeding = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT event_id, reason FROM reason_exceeding WHERE event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {
				reasonExceeding = new ReasonExceeding();
				ModelMapperUtilities.mapRsToReasonExceeding(rs, reasonExceeding);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getReasonExceedingByEventId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return reasonExceeding;
	}

}
