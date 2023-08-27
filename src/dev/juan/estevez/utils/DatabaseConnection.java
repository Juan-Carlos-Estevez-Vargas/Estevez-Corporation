package dev.juan.estevez.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class DatabaseConnection {

	/**
	 * Connects to the local database.
	 *
	 * @return the connection object
	 * @throws SQLException if there was an error connecting to the database
	 */
	public static Connection connect() throws SQLException {
		try {
			final String URL = "jdbc:mysql://localhost/estevez_corporation";
			final String USER = "root";
			final String PASSWORD = "";
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException ex) {
			throw new SQLException(Constants.ERROR_DB_CONNECTION, ex);
		}
	}

}
