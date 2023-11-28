package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	// Code database URL
	static final String DB_URL = "jdbc:mysql://localhost:3306/db1";
	// Database credentials
	static final String USER = "root", PASS = "Van-1988";

	public Connection connect() throws SQLException {

		return DriverManager.getConnection(DB_URL, USER, PASS);

	}

}
