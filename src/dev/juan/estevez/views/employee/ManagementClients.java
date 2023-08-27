package dev.juan.estevez.views.employee;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas
 */
public class ManagementClients extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int id_cliente_update = 0;
	public static String user_update = "";
	private JLabel labelTitle;
	private JPanel container;
	private String user;
	private JTable tableClients;
	private JScrollPane scrollPaneClients;
	private DefaultTableModel model = new DefaultTableModel();

	public ManagementClients() {
		initializeFrame();
		initComponents();
		populateTable();
		addTableMouseListener();
	}

	/**
	 * Initializes the frame with the necessary settings and properties.
	 */
	private void initializeFrame() {
		user = LoginView.user;
		setSize(630, 340);
		setResizable(false);
		setTitle("Clientes registrados - Sesión de " + user);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * Initializes and sets up the components for the GUI.
	 */
	private void initComponents() {
		container = new JPanel();
		container.setBackground(new Color(46, 59, 104));
		container.setLayout(null);
		setContentPane(container);

		labelTitle = new JLabel("Clientes Registrados");
		labelTitle.setBounds(210, 10, 250, 20);
		labelTitle.setForeground(new Color(192, 192, 192));
		labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		container.add(labelTitle);
	}

	/**
	 * Populates the table with client data from the database.
	 */
	private void populateTable() {
		try {
			Connection cn = DatabaseConnection.connect();
			PreparedStatement pst = cn.prepareStatement(
					"SELECT id_cliente, nombre_cliente, mail_cliente, tel_cliente, ultima_modificacion FROM clientes");
			ResultSet rs = pst.executeQuery();

			model = new DefaultTableModel();
			tableClients = new JTable(model);
			tableClients.setFont(new Font("serif", Font.BOLD, 14));
			tableClients.setForeground(Color.BLACK);

			scrollPaneClients = new JScrollPane(tableClients);
			scrollPaneClients.setBounds(10, 55, 593, 230);
			container.add(scrollPaneClients);

			model.addColumn(" ");
			model.addColumn("Nombre");
			model.addColumn("Email");
			model.addColumn("Teléfono");
			model.addColumn("Modificado por");

			while (rs.next()) {
				Object[] row = new Object[5];
				for (int i = 0; i < 5; i++) {
					row[i] = rs.getObject(i + 1);
				}
				model.addRow(row);
			}

			rs.close();
			pst.close();
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en el llenado de la tabla clientes " + e);
		}
	}

	/**
	 * Adds a mouse listener to the tableClients object. When the mouse is clicked
	 * on a row of the table,
	 * the id_cliente_update field is set to the value of the first column of that
	 * row. It then creates
	 * a ClientInformation object, sets it to visible, and disposes the current
	 * frame.
	 */
	private void addTableMouseListener() {
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowPoint = tableClients.rowAtPoint(e.getPoint());
				int columnPoint = 0;

				if (rowPoint > -1) {
					id_cliente_update = (int) model.getValueAt(rowPoint, columnPoint);
					ClientInformation clientInformation = new ClientInformation();
					clientInformation.setVisible(true);
					dispose();
				}
			}
		});
	}

}
