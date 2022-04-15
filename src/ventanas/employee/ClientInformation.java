package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class ClientInformation extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private DefaultTableModel model = new DefaultTableModel();
	public static int idEquipment = 0;
	private JLabel labelTittle;
	private JLabel labelName;
	private JLabel labelEmail;
	private JLabel labelPhone;
	private JLabel labelAdress;
	private JLabel labelModifyBy;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtAdress;
	private JTextField txtModifyBy;
	private JTable tableEquipment;
	private JScrollPane scrollEquipment;
	private JPanel container;
	private JButton btnRegisterEquipment;
	private JButton btnUpdateEquipment;

	/**
	 * Constructor de clase.
	 */
	public ClientInformation() {
		initComponents();
		this.user = Login.user;
		this.user_update = ManagementClients.user_update; // Guardamos el usuario seleccionado en la tabla usuarios
		this.setResizable(false);
		this.setSize(630, 460);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.labelTittle.setText("Información del cliente " + this.user_update);

		/**
		 * Conexion a la base de datos
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM clientes WHERE id_cliente = '" + user_update + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.setTitle("Información del cliente " + rs.getString("nombre_cliente") + " - Sesión de " + user);
				labelTittle.setText("Información del cliente " + rs.getString("nombre_cliente"));

				/**
				 * Llenado de los campos con la base de datos
				 */
				txtName.setText(rs.getString("nombre_cliente"));
				txtEmail.setText(rs.getString("mail_cliente"));
				txtPhone.setText(rs.getString("tel_cliente"));
				txtAdress.setText(rs.getString("dir_cliente"));
				txtModifyBy.setText(rs.getString("ultima_modificacion"));
			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error al cargar usuario " + e);
			JOptionPane.showMessageDialog(null, "¡¡Error al cargar!! Contacte al Administrador");
		}

		/**
		 * LLenado de la tabla equipos
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos WHERE id_cliente = '"
							+ user_update + "'");
			ResultSet rs = pst.executeQuery();

			this.tableEquipment = new JTable(this.model);
			this.tableEquipment.setFont(new Font("serif", Font.BOLD, 14));
			this.tableEquipment.setForeground(Color.BLACK);
			this.scrollEquipment = new JScrollPane(this.tableEquipment);
			this.scrollEquipment.setBounds(280, 65, 315, 170);
			this.scrollEquipment.setViewportView(this.tableEquipment);

			this.model.addColumn("ID equipo");
			this.model.addColumn("Tipo de Equipo");
			this.model.addColumn("Marca");
			this.model.addColumn("Estatus");

			while (rs.next()) {
				Object[] row = new Object[4];
				for (int i = 0; i < 4; i++) {
					row[i] = rs.getObject(i + 1);
				}
				model.addRow(row);
			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en el llenado de la tabla equipos " + e);
		}

		/**
		 * Evento de accion para seleccionar cliente
		 */
		tableEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowPoint = tableEquipment.rowAtPoint(e.getPoint());
				int columnPoint = 0;

				if (rowPoint > -1) {
					idEquipment = (int) model.getValueAt(rowPoint, columnPoint);
					EquipmentInformation equipmentInformation = new EquipmentInformation();
					equipmentInformation.setVisible(true);
				}
			}
		});
		this.container.add(this.scrollEquipment);
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
		this.container.setBounds(630, 460, 630, 460);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Información del Cliente - " + this.user_update);
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(10, 10, 400, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Nombre.
		 */
		this.labelName = new JLabel("Nombre :");
		this.labelName.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelName.setForeground(new java.awt.Color(192, 192, 192));
		this.labelName.setBounds(20, 50, 100, 20);
		this.container.add(this.labelName);

		/**
		 * Label Email.
		 */
		this.labelEmail = new JLabel("Email :");
		this.labelEmail.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelEmail.setForeground(new java.awt.Color(192, 192, 192));
		this.labelEmail.setBounds(20, 110, 100, 20);
		this.container.add(this.labelEmail);

		/**
		 * Label Phone.
		 */
		this.labelPhone = new JLabel("Teléfono :");
		this.labelPhone.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelPhone.setForeground(new java.awt.Color(192, 192, 192));
		this.labelPhone.setBounds(20, 170, 100, 20);
		this.container.add(this.labelPhone);

		/**
		 * Label Permisos de.
		 */
		this.labelAdress = new JLabel("Dirección :");
		this.labelAdress.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelAdress.setForeground(new java.awt.Color(192, 192, 192));
		this.labelAdress.setBounds(20, 230, 100, 20);
		this.container.add(this.labelAdress);

		/**
		 * Label Username.
		 */
		this.labelModifyBy = new JLabel("Última modificación por :");
		this.labelModifyBy.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModifyBy.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModifyBy.setBounds(20, 290, 100, 20);
		this.container.add(this.labelModifyBy);

		/**
		 * Campo de texto con la información del nombre del usuario a actualizar.
		 */
		this.txtName = new JTextField();
		this.txtName.setBounds(20, 70, 230, 30);
		this.txtName.setBackground(new Color(127, 140, 141));
		this.txtName.setFont(new Font("serif", Font.BOLD, 20));
		this.txtName.setHorizontalAlignment(JLabel.LEFT);
		this.txtName.setForeground(Color.WHITE);
		this.container.add(this.txtName);

		/**
		 * Campo de texto con la información del email del usuario a actualizar.
		 */
		this.txtEmail = new JTextField();
		this.txtEmail.setBounds(20, 130, 230, 30);
		this.txtEmail.setBackground(new Color(127, 140, 141));
		this.txtEmail.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmail.setHorizontalAlignment(JLabel.LEFT);
		this.txtEmail.setForeground(Color.WHITE);
		this.container.add(this.txtEmail);

		/**
		 * Campo de texto con la información del teléfono del usuario a actualizar.
		 */
		this.txtPhone = new JTextField();
		this.txtPhone.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtPhone.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.txtPhone.setBounds(20, 190, 230, 30);
		this.txtPhone.setBackground(new Color(127, 140, 141));
		this.txtPhone.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhone.setHorizontalAlignment(JLabel.LEFT);
		this.txtPhone.setForeground(Color.WHITE);
		this.container.add(this.txtPhone);

		/**
		 * Campo de texto con la información del username del usuario a actualizar.
		 */
		this.txtAdress = new JTextField();
		this.txtAdress.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtAdress.setBounds(20, 250, 230, 30);
		this.txtAdress.setBackground(new Color(127, 140, 141));
		this.txtAdress.setFont(new Font("serif", Font.BOLD, 20));
		this.txtAdress.setForeground(Color.WHITE);
		this.container.add(this.txtAdress);

		/**
		 * Campo de texto con la información de quién registró el usuario a actualizar.
		 */
		this.txtModifyBy = new JTextField();
		this.txtModifyBy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtModifyBy.setEnabled(false);
		this.txtModifyBy.setBounds(20, 310, 230, 30);
		this.txtModifyBy.setBackground(new Color(127, 140, 141));
		this.txtModifyBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModifyBy.setForeground(Color.WHITE);
		this.container.add(this.txtModifyBy);

		/**
		 * Botón encargado de actualizar el usuario en cuestión.
		 */
		this.btnRegisterEquipment = new JButton("Registrar Equipo");
		this.btnRegisterEquipment.setBounds(280, 260, 210, 35);
		this.btnRegisterEquipment.setFont(new Font("serif", Font.BOLD, 20));
		this.btnRegisterEquipment.setBackground(new Color(8, 85, 224));
		this.btnRegisterEquipment.setForeground(Color.WHITE);
		this.btnRegisterEquipment.setHorizontalAlignment(JButton.CENTER);
		this.btnRegisterEquipment.addActionListener(this);
		this.container.add(this.btnRegisterEquipment);

		/**
		 * Botón para restaurar la contraseña del usuario en cuestión.
		 */
		this.btnUpdateEquipment = new JButton("Actualizar Cliente");
		this.btnUpdateEquipment.setBounds(280, 310, 210, 35);
		this.btnUpdateEquipment.setFont(new Font("serif", Font.BOLD, 20));
		this.btnUpdateEquipment.setBackground(new Color(8, 85, 224));
		this.btnUpdateEquipment.setForeground(Color.WHITE);
		this.btnUpdateEquipment.setHorizontalAlignment(JButton.CENTER);
		this.btnUpdateEquipment.addActionListener(this);
		this.container.add(this.btnUpdateEquipment);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
