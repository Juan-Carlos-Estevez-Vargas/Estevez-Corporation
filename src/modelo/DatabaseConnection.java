package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	/**
	 * Conexion con base de datos local
	 * 
	 * @return
	 */
	public static Connection conectar() {
		try {
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/data_system", "root", "");
			return cn;
		} catch (SQLException ex) {
			System.out.println("Error en la conexion local " + ex);
		}
		return (null);
	}

}
