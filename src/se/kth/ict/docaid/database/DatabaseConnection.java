package se.kth.ict.docaid.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static String dbName = "docaid";
	private static String dbHost = "localhost";
	private static String dbUser = "gis";
	private static String dbPassword = "gis";
	private Connection connection;

//	private static String dbName = "kthtest";
//	private static String dbHost = System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST");
//	private static String dbPort = System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT");
//	private static String dbUser = System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME");
//	private static String dbPassword = System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD");
	
	public DatabaseConnection() {
		connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://" + dbHost + "/" + dbName, dbUser, dbPassword);
//			connection = DriverManager.getConnection("jdbc:postgresql://" + dbHost +":"+dbPort + "/" + dbName, dbUser, dbPassword);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
}

