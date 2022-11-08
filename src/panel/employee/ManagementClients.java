package panel.employee;

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

import panel.utilities.Login;
import util.DatabaseConnection;

/**
 * Frame que lista los clientes registrados en el sistema.
 *
 * @author
 *
 */
public class ManagementClients extends JFrame {

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	public static int id_cliente_update = 0;
	public static String user_update = "";
	private JLabel labelTitle;
	private JPanel container;
	private String user;
	private JTable tableClients;
	private JScrollPane scrollPaneClients;
	private DefaultTableModel model = new DefaultTableModel();

	/**
	 * Constructor de Clase.
	 */
	public ManagementClients() {
		this.user = Login.user;
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Clientes registrados - Sesión de " + this.user);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.initComponents();

		/**
		 * Rellenando la tabla con los datos de la base de datos.
		 */
		try {
			Connection cn = DatabaseConnection.conectar();
			PreparedStatement pst = cn.prepareStatement(
					"SELECT id_cliente, nombre_cliente, mail_cliente, tel_cliente, ultima_modificacion FROM clientes");
			ResultSet rs = pst.executeQuery();

			this.tableClients = new JTable(this.model);
			this.tableClients.setFont(new Font("serif", Font.BOLD, 14));
			this.tableClients.setForeground(Color.BLACK);
			this.scrollPaneClients = new JScrollPane(this.tableClients);
			this.scrollPaneClients.setBounds(10, 55, 593, 230);
			this.scrollPaneClients.setViewportView(this.tableClients);

			/**
			 * Columnas de la tabla
			 */
			this.model.addColumn(" ");
			this.model.addColumn("Nombre");
			this.model.addColumn("Email");
			this.model.addColumn("Teléfono");
			this.model.addColumn("Midificado por");

			/**
			 * Llenado de la tabla
			 */
			while (rs.next()) {
				Object[] row = new Object[5];
				for (int i = 0; i < 5; i++) {
					row[i] = rs.getObject(i + 1);
				}
				this.model.addRow(row);
			}
			
			rs.close();
			pst.close();
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en el llenado de la tabla clientes " + e);
		}

		/**
		 * Evento de accion para seleccionar cliente
		 */
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
		this.container.add(this.scrollPaneClients);

	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.setContentPane(this.container);

		/**
		 * Título del Panel.
		 */
		this.labelTitle = new JLabel("Clientes Registrados");
		this.labelTitle.setBounds(210, 10, 250, 20);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		this.container.add(this.labelTitle);
	}

}
