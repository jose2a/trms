package com.revature.trms.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtilities {

	private static Connection connection;

	private static DataSource dataSource;

	private static DataSource getDataSource() {
		try {
			Context initCtx = new InitialContext();

			// Look up our data source
			dataSource = (DataSource) initCtx.lookup("java:comp/env/ds/trms");
			
			LogUtilities.trace("Getting datasource");
		} catch (NamingException e) {
			LogUtilities.error(e.getMessage());
		}

		return dataSource;
	}

	public static synchronized Connection getConnection() {
		try {
			connection = getDataSource().getConnection();
			LogUtilities.trace("Got connection from Data Source");
		} catch (SQLException e) {
			LogUtilities.error("Could not connect to the database.");
		}

		LogUtilities.trace("Returning connection");
		return connection;
	}
}
