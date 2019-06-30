package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class GradingFormatDAOImpl extends BaseDAO implements GradingFormatDAO {

	private String baseSql = "SELECT grading_format_id, from_range, to_range FROM grading_format";

	@Override
	public boolean addGradingFormat(GradingFormat gradingFormat) {
		LogUtilities.trace("addGradingFormat.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO grading_format (from_range, to_range) VALUES(?, ?)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, gradingFormat.getFromRange());
			ps.setString(2, gradingFormat.getToRange());

			if (ps.executeUpdate() != 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					LogUtilities.trace("Getting id for grading.");

					gradingFormat.setGradingFormatId(rs.getInt(1));
				}

				LogUtilities.trace("Grading format inserted Id: " + gradingFormat.getGradingFormatId());
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addGradingFormat. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public GradingFormat getGradingFormatById(Integer gradingFormatId) {
		LogUtilities.trace("getGradingFormatById. " + gradingFormatId);

		GradingFormat gradingFormat = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE grading_format_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, gradingFormatId);

			rs = ps.executeQuery();

			if (rs.next()) {
				gradingFormat = new GradingFormat();
				ModelMapperUtilities.mapRsToGradingFormat(rs, gradingFormat);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getGradingFormatById. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return gradingFormat;
	}

	@Override
	public List<GradingFormat> getAllGradingFormats() {
		LogUtilities.trace("getAllGradingFormats.");

		List<GradingFormat> gradingFormats = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			ps = conn.prepareStatement(baseSql);

			rs = ps.executeQuery();

			while (rs.next()) {
				GradingFormat gradingFormat = new GradingFormat();
				ModelMapperUtilities.mapRsToGradingFormat(rs, gradingFormat);

				gradingFormats.add(gradingFormat);
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. getAllGradingFormats. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return gradingFormats;
	}

}
