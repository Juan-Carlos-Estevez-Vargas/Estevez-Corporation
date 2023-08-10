package dev.juan.estevez.views.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.views.LoginView;

/**
 * Vista con el listado de los usuario registrados en el sistema.
 *
 * @author Juan Carlos Estevez Vargas
 */
public class ManagementUsers extends JFrame {

	private static final long serialVersionUID = 1L;
	public static String user_update = "";
	private JPanel container;
	private JTable tableUsers;
	private JScrollPane scrollPaneUsers;
	private String user;
	private DefaultTableModel model = new DefaultTableModel();

	public ManagementUsers() {
		this.user = LoginView.user;
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Usuarios registrados - Sesi�n de " + this.user);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.initComponents();
		this.populateTable();
		this.addTableMouseEvent();
	}

	/**
	 * Populates the table with data from the usuarios table in the database.
	 *
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private void populateTable() {
		try {
			Connection cn = DatabaseConnection.connect();
			PreparedStatement pst = cn.prepareStatement(
					"SELECT id_usuario, nombre_usuario, username, tipo_nivel, estatus FROM usuarios");
			ResultSet rs = pst.executeQuery();

			createTableAndScrollPane();
			addColumnsToTable();
			fillTableWithData(rs);

			rs.close();
			pst.close();
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error al llenar Tabla " + e);
			JOptionPane.showMessageDialog(null, "¡Error al mostrar información! Contacte al Administrador");
		}
	}

	/**
	 * Creates a table and scroll pane.
	 *
	 * @param None
	 * @return None
	 */
	private void createTableAndScrollPane() {
		tableUsers = new JTable(model);
		tableUsers.setFont(new Font("serif", Font.BOLD, 14));
		tableUsers.setForeground(Color.BLACK);

		scrollPaneUsers = new JScrollPane(tableUsers);
		scrollPaneUsers.setBounds(10, 55, 593, 230);
		container.add(scrollPaneUsers);
	}

	/**
	 * Adds columns to the table.
	 */
	private void addColumnsToTable() {
		model.addColumn(" ");
		model.addColumn("Nombre Usuario");
		model.addColumn("Username");
		model.addColumn("Permisos");
		model.addColumn("Estatus");
	}

	/**
	 * Fills the table with data from the given ResultSet.
	 *
	 * @param rs the ResultSet containing the data to be filled in the table
	 * @throws SQLException if there is an error accessing the ResultSet
	 */
	private void fillTableWithData(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Object[] row = new Object[5];
			for (int i = 0; i < 5; i++) {
				row[i] = rs.getObject(i + 1);
			}
			model.addRow(row);
		}
	}

	/**
	 * Adds a mouse event listener to the tableUsers object.
	 * When the table is clicked, it retrieves the row and column index of the
	 * clicked cell.
	 * If the row index is valid, it retrieves the value at the specified column
	 * index and assigns it to the user_update variable.
	 * It creates a new instance of the UserInformation class and makes it visible.
	 * Finally, it disposes the current window.
	 */
	private void addTableMouseEvent() {
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row_point = tableUsers.rowAtPoint(e.getPoint());
				int column_point = 2;

				if (row_point > -1) {
					user_update = (String) model.getValueAt(row_point, column_point);
					UserInformation userInformation = new UserInformation();
					userInformation.setVisible(true);
					dispose();
				}
			}
		});
	}

	/**
	 * Initializes the components of the Java function.
	 */
	private void initComponents() {
		JPanel container = new JPanel();
		container.setBackground(new Color(46, 59, 104));
		container.setLayout(null);
		setContentPane(container);

		JLabel title = new JLabel("Usuarios Registrados");
		title.setBounds(210, 10, 250, 20);
		title.setForeground(new Color(192, 192, 192));
		title.setFont(new Font("serif", Font.BOLD, 20));
		container.add(title);
	}

}
