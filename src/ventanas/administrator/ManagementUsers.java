package ventanas.administrator;

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
import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * Vista con el listado de los usuario registrados en el sistema.
 * 
 * @author 
 *
 */
public class ManagementUsers extends JFrame {

	/**
	 * Declaraci?n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	public static String user_update = "";
	private JLabel jlabel1;
	private JPanel container;
	private JTable tableUsers;
	private JScrollPane scrollPaneUsers;
	private String user;
	private DefaultTableModel model = new DefaultTableModel();

	/**
	 * Constructor de Clase.
	 */
	public ManagementUsers() {
		this.user = Login.user;
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Usuarios registrados - Sesi?n de " + this.user);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.initComponents();

		/**
		 * Conexion a la base de datos ya que se deben listar los usuarios tan pronto se
		 * inicie su ejecuci?n.
		 */
		try {
			Connection cn = DatabaseConnection.conectar();
			PreparedStatement pst = cn
					.prepareStatement("SELECT id_usuario, nombre_usuario, username, tipo_nivel, estatus FROM usuarios");
			ResultSet rs = pst.executeQuery();

			/**
			 * Creamos la tabla y la a?adimos al JScrollPanel.
			 */
			this.tableUsers = new JTable(this.model);
			this.tableUsers.setFont(new Font("serif", Font.BOLD, 14));
			this.tableUsers.setForeground(Color.BLACK);
			this.scrollPaneUsers = new JScrollPane(this.tableUsers);
			this.scrollPaneUsers.setBounds(10, 55, 593, 230);

			/**
			 * A?adiendo las columnas a la tabla.
			 */
			this.model.addColumn(" ");
			this.model.addColumn("Nombre Usuario");
			this.model.addColumn("Username");
			this.model.addColumn("Permisos");
			this.model.addColumn("Estatus");

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
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error al llenar Tabla " + e);
			JOptionPane.showMessageDialog(null, "??Error al mostrar informacion!! Contacte al Administrador");
		}

		/**
		 * Eventos de la tabla para mostrar informacion de los usuario
		 */
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row_point = tableUsers.rowAtPoint(e.getPoint()); // Esta variable almacena la fila seleccionada
				int column_point = 2;

				if (row_point > -1) {
					user_update = (String) model.getValueAt(row_point, column_point);
					UserInformation userInformation = new UserInformation();
					userInformation.setVisible(true);
					dispose();
				}
			}
		});
		this.container.add(this.scrollPaneUsers);
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
		 * T?tulo del Panel.
		 */
		this.jlabel1 = new JLabel("Usuarios Registrados");
		this.jlabel1.setBounds(210, 10, 250, 20);
		this.jlabel1.setForeground(new Color(192, 192, 192));
		this.jlabel1.setFont(new Font("serif", Font.BOLD, 20));
		this.container.add(this.jlabel1);
	}

}
