package panel.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import panel.utilities.Login;
import util.DatabaseConnection;

/**
 * Reporte del estado de los equipos.
 *
 * @author
 *
 */
public class StateGraph extends JFrame {

	/**
	 * Variables.
	 */
	private static final long serialVersionUID = 1L;
	int newEntry, notRepaired, inReview, repaired, delivered;
	int[] vectorCantStatus = new int[5];
	private String user;
	private String[] vectorNameStatus = new String[5];

	/**
	 * Constructor de clase.
	 */
	public StateGraph() {
		user = Login.user;
		this.setSize(515, 360);
		this.setResizable(false);
		this.setTitle("Técnico - Sesión de " + user);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		try {

			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT estatus, COUNT(estatus) AS Cantidad FROM equipos GROUP BY estatus");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				int position = 0;

				do {
					vectorNameStatus[position] = rs.getString(1);
					vectorCantStatus[position] = rs.getInt(2);

					if (vectorNameStatus[position].equalsIgnoreCase("En revision")) {
						inReview = vectorCantStatus[position];
					} else if (vectorNameStatus[position].equalsIgnoreCase("Entregado")) {
						delivered = vectorCantStatus[position];
					} else if (vectorNameStatus[position].equalsIgnoreCase("No reparado")) {
						notRepaired = vectorCantStatus[position];
					} else if (vectorNameStatus[position].equalsIgnoreCase("Nuevo ingreso")) {
						newEntry = vectorCantStatus[position];
					} else if (vectorNameStatus[position].equalsIgnoreCase("Reparado")) {
						repaired = vectorCantStatus[position];
					}

					position++;

				} while (rs.next());

			}

		} catch (SQLException e) {
			System.err.println("Error en conectar con la base de datos " + e);
			JOptionPane.showMessageDialog(null, "¡¡Error!! Contacte al Administrador");
		}

	}

	public int mostRepeatedState(int newEntry, int notRepaired, int inReview, int repaired, int delivered) {

		/**
		 * Validando estado mas grande.
		 */
		if (newEntry > notRepaired && newEntry > inReview && newEntry > repaired && newEntry > delivered) {
			return newEntry;
		} else if (notRepaired > inReview && notRepaired > repaired && notRepaired > delivered) {
			return notRepaired;
		} else if (inReview > repaired && inReview > delivered) {
			return inReview;
		} else if (repaired > delivered) {
			return repaired;
		} else {
			return delivered;
		}

	}

	/**
	 * Graficando las barras.
	 */
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		int mostRepitedStatus = mostRepeatedState(newEntry, notRepaired, inReview, repaired, delivered);

		int longNewEntry = newEntry * 400 / mostRepitedStatus;
		int longNotRepaired = notRepaired * 400 / mostRepitedStatus;
		int longInReview = inReview * 400 / mostRepitedStatus;
		int longRepaired = repaired * 400 / mostRepitedStatus;
		int longDelivered = delivered * 400 / mostRepitedStatus;

		g.setColor(new Color(46, 59, 104));
		g.fillRect(0, 0, 515, 360);

		/**
		 * Pintando la gráfica de barras.
		 */
		g.setColor(Color.yellow);
		g.fillRect(100, 100, longNewEntry, 40);
		g.drawString("Nuevo Ingreso", 10, 118);
		g.drawString("Cantidad " + newEntry, 10, 133);

		g.setColor(Color.red);
		g.fillRect(100, 150, longNotRepaired, 40);
		g.drawString("No Reparado", 10, 168);
		g.drawString("Cantidad " + notRepaired, 10, 183);

		g.setColor(Color.black);
		g.fillRect(100, 200, longInReview, 40);
		g.drawString("En Revisión", 10, 218);
		g.drawString("Cantidad " + inReview, 10, 233);

		g.setColor(Color.white);
		g.fillRect(100, 250, longRepaired, 40);
		g.drawString("Reparado", 10, 268);
		g.drawString("Cantidad " + repaired, 10, 283);

		g.setColor(Color.green);
		g.fillRect(100, 300, longDelivered, 40);
		g.drawString("Entregado", 10, 318);
		g.drawString("Cantidad " + delivered, 10, 333);

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Gráfica de estado del equipo", 150, 68);

	}

}
