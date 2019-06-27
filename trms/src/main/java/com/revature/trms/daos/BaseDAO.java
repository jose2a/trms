package com.revature.trms.daos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.utilities.LogUtilities;

public abstract class BaseDAO {

	protected void closeResources(ResultSet rs, PreparedStatement ps, CallableStatement cs) {
		LogUtilities.trace("Cleaning resources");

		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}

			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

			if (cs != null && !cs.isClosed()) {
				cs.close();
			}
		} catch (SQLException e) {
			LogUtilities.error("Error closing resourses. " + e.getMessage());
		}
	}

	protected String getQuestionsMarksStringForSQLPlaceholder(int times) {
		String[] arr = new String[times];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = "?";
		}

		return String.join(",", arr);
	}

}
