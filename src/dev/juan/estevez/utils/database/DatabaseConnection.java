package dev.juan.estevez.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dev.juan.estevez.utils.constants.DbConstants;

/**
 * 
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
			throw new SQLException(DbConstants.ERROR_DB_CONNECTION, ex);
		}
	}

}
