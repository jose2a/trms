package com.revature.trms.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtilities {

	private static Connection connection;

//	private static DataSource dataSource;
//
//	private static DataSource getDataSource() {
//		try {
//			Context initCtx = new InitialContext();
//
//			// Look up our data source
//			dataSource = (DataSource) initCtx.lookup("java:comp/env/ds/trms");
//			
//			LogUtilities.trace("Getting datasource");
//		} catch (NamingException e) {
//			LogUtilities.error(e.getMessage());
//		}
//
//		return dataSource;
//	}
//
//	public static synchronized Connection getConnection() {
//		try {
//			connection = getDataSource().getConnection();
//			LogUtilities.trace("Got connection from Data Source");
//		} catch (SQLException e) {
//			LogUtilities.error("Could not connect to the database.");
//		}
//
//		LogUtilities.trace("Returning connection");
//		return connection;
//	}

	private static String url;
	private static String user;
	private static String password;
	private static final String PROPERTIES_FILE = "database.properties";

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
		LogUtilities.trace("Loading connection parameters from file.");
//		Properties prop = new Properties();
//
//		ClassLoader loader = Thread.currentThread().getContextClassLoader();
//
//		try (InputStream fis = loader.getResourceAsStream(PROPERTIES_FILE);) {
//			prop.load(fis);

			url = "jdbc:postgresql://" + System.getenv("TRMS_URL") + ":5432/jose_1905java";//prop.getProperty("url");
			user = System.getenv("TRMS_USER");//prop.getProperty("user");
			password = System.getenv("TRMS_PASS"); //prop.getProperty("password");
			
			LogUtilities.info("URL " + url);
			LogUtilities.info("USERNAME " + user);
//		} catch (FileNotFoundException e) {
//			LogUtilities.error("Connection file not loaded." + e.getMessage());
//		} catch (IOException e) {
//			LogUtilities.error("Could not load connection properties file. " + e.getMessage());
//		}
	}
}
