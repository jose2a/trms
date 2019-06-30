package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class InformationRequiredDAOImpl extends BaseDAO implements InformationRequiredDAO {

	private String baseSql = "SELECT event_id, employee_id, information, provided, required_by FROM info_required";

	@Override
	public boolean addInformationRequired(InformationRequired informationRequired) {
		LogUtilities.trace("addInformationRequired.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO info_required (event_id, employee_id, information, provided, required_by) VALUES(?, ?, ?, ?)";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, informationRequired.getEventId());
			ps.setInt(2, informationRequired.getEmployeeId());
			ps.setString(3, informationRequired.getInformation());
			ps.setBoolean(4, informationRequired.isProvided());
			ps.setInt(5, informationRequired.getRequiredBy());

			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addInformationRequired." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateInformationRequired(InformationRequired informationRequired) {
		LogUtilities.trace("updateInformationRequired.");

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE info_required SET information=?, provided=?, required_by=? WHERE event_id=? AND employee_id=?";

			ps = conn.prepareStatement(sql);

			ps.setString(1, informationRequired.getInformation());
			ps.setBoolean(2, informationRequired.isProvided());
			ps.setInt(3, informationRequired.getEmployeeId());
			ps.setInt(4, informationRequired.getRequiredBy());
			ps.setInt(5, informationRequired.getEmployeeId());

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Information required updated.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. updateInformationRequired." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public List<InformationRequired> getInformationRequiredByEmployeeId(Integer employeeId) {
		LogUtilities.trace("Getting info required from this employee. ID " + employeeId);

		List<InformationRequired> informationRequiredList = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT event_id, employee_id, information, provided, required_by "
					+ "FROM info_required WHERE employee_id=? AND provided=false";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				InformationRequired informationRequired = new InformationRequired();
				ModelMapperUtilities.mapRsToInformationRequired(rs, informationRequired);

				informationRequiredList.add(informationRequired);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting info required from this employee." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return informationRequiredList;
	}

	@Override
	public InformationRequired getInformationRequiredByEmployeeIdAndEventId(Integer employeeId, Integer eventId) {
		LogUtilities.trace("getInformationRequiredByEmployeeIdAndEventId. " + employeeId);

		InformationRequired informationRequired = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE employee_id=? AND event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeId);
			ps.setInt(2, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {
				informationRequired = new InformationRequired();
				ModelMapperUtilities.mapRsToInformationRequired(rs, informationRequired);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getInformationRequiredByEmployeeIdAndEventId" + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return informationRequired;
	}

}
