package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.EventType;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class EventTypeDAOImpl extends BaseDAO implements EventTypeDAO {

	private String baseSql = "SELECT event_type_id, name, reimburse_coverage FROM event_type";
	
	@Override
	public EventType getEventTypeById(int eventTypeId) {
		LogUtilities.trace("getEventTypeById. " + eventTypeId);

		EventType eventType = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE event_type_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventTypeId);

			rs = ps.executeQuery();

			if (rs.next()) {
				eventType = new EventType();
				ModelMapperUtilities.mapRsToEventType(rs, eventType);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventTypeById. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return eventType;
	}

	@Override
	public List<EventType> getAllEventTypes() {
		LogUtilities.trace("getAllEventType.");

		List<EventType> eventTypes = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			ps = conn.prepareStatement(baseSql);

			rs = ps.executeQuery();

			while (rs.next()) {
				EventType eventType = new EventType();
				ModelMapperUtilities.mapRsToEventType(rs, eventType);

				eventTypes.add(eventType);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getAllEventType. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return eventTypes;
	}

}
