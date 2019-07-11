package com.revature.trms.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtilities {

	private static Connection connection;
//
//	private static String url;
//	private static String user;
//	private static String password;
//	private static final String PROPERTIES_FILE = "database.properties";
//
//	public static synchronized Connection getConnection() {
//		try {
//			if (connection == null || connection.isClosed()) {
//				LogUtilities.trace("Connection is null or closed.");
//				
//				loadConectionsParameters();
//				Class.forName("org.postgresql.Driver");
//				connection = DriverManager.getConnection(url, user, password);
//
//				LogUtilities.trace("Connection open.");
//			}
//		} catch (ClassNotFoundException e) {
//			LogUtilities.error("Could not register the driver.");
//		} catch (SQLException e) {
//			LogUtilities.error("Could not connect to the database.");
//		}
//
//		LogUtilities.trace("Returning existing connection.");
//
//		return connection;
//	}
//
//	private static void loadConectionsParameters() {
//		LogUtilities.trace("Loading connection parameters from file.");
//		Properties prop = new Properties();
//
//		ClassLoader loader = Thread.currentThread().getContextClassLoader();
//
//		try (InputStream fis = loader.getResourceAsStream(PROPERTIES_FILE);) {
//			prop.load(fis);
//
//			url = prop.getProperty("url");
//			user = prop.getProperty("user");
//			password = prop.getProperty("password");
//		} catch (FileNotFoundException e) {
//			LogUtilities.error("Connection file not loaded." + e.getMessage());
//		} catch (IOException e) {
//			LogUtilities.error("Could not load connection properties file. " + e.getMessage());
//		}
//	}
	
	private static DataSource dataSource;

	public ConnectionUtilities() {
	    try {
	        dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/trms");
	    } catch (NamingException e) {
	        // Handle error that it's not configured in JNDI.
	        throw new IllegalStateException("jdbc/trms is missing in JNDI!", e);
	    }
	}

	public static synchronized Connection getConnection() {
	    try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			LogUtilities.error("Could not connect to the database.");
		}
	    
	    return connection;
	}
}
