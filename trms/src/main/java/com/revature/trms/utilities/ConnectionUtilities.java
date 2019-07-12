package com.revature.trms.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtilities {

	private static Connection connection;

	private static String url;
	private static String user;
	private static String password;

	public static synchronized Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				LogUtilities.trace("Connection is null or closed.");

				loadConectionsParameters();
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);

				LogUtilities.trace("Connection open.");
			}
		} catch (ClassNotFoundException e) {
			LogUtilities.error("Could not register the driver.");
		} catch (SQLException e) {
			LogUtilities.error("Could not connect to the database.");
		}

		LogUtilities.trace("Returning existing connection.");

		return connection;
	}

	private static void loadConectionsParameters() {
		LogUtilities.trace("Loading connection parameters from enviroment.");

		url = "jdbc:postgresql://" + System.getenv("TRMS_URL") + ":5432/jose_1905java";
		user = System.getenv("TRMS_USER");
		password = System.getenv("TRMS_PASS");
	}
}
