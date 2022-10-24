package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * Frame encargado de registrar un equipo asociado a un cliente específico.
 *
 * @author
 *
 */
public class RegisterEquipment extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables
	 */
	private static final long serialVersionUID = 1L;
	private int idClientUpdate;
	private JTextField txtNameClient;
	private JTextField txtModel;
	private JTextField txtSerialNumber;
	private JComboBox<String> cmbTypeEquip;
	private JComboBox<String> cmbMark;
	private JTextPane textPaneObservations;
	private JScrollPane scrollObservations;
	private JLabel labelTypeEquipment;
	private JLabel labelNameClient;
	private JLabel labelMark;
	private JLabel labelModel;
	private JLabel labelSerialNumber;
	private JLabel labelTitle;
	private JLabel labelObservations;
	private JPanel panelBackClient;
	private JButton btnRegisterEquip;
	private String user;
	private String nameClient;

	/**
	 * Constructor de clase.
	 */
	public RegisterEquipment() {
		this.user = Login.user;
		this.setTitle("Registrar nuevo Equipo - Sesión de " + this.user);
		this.setSize(615, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
		this.txtNameClient.setText(this.nameClient);
		this.idClientUpdate = ManagementClients.id_cliente_update;
		this.nameClient = ManagementClients.user_update; // Guardamos el usuario seleccionado en la tabla usuarios
		idClientUpdate = ManagementClients.id_cliente_update;

		/**
		 * Recuperando el nombre del cliente.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM clientes WHERE id_cliente = '" + idClientUpdate + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.labelTitle.setText("Registro de Equipo para " + rs.getString("nombre_cliente"));

				/**
				 * Llenado de los campos con la base de datos
				 */
				this.txtNameClient.setText(rs.getString("nombre_cliente"));

			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error al cargar usuario " + e);
			JOptionPane.showMessageDialog(null, "¡¡Error al cargar!! Contacte al Administrador");
		}
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel principal.
		 */
		this.panelBackClient = new JPanel();
		this.panelBackClient.setBackground(new Color(46, 59, 104));
		this.panelBackClient.setLayout(null);
		this.setContentPane(this.panelBackClient);

		/**
		 * Título de la ventana.
		 */
		this.labelTitle = new JLabel();
		this.labelTitle.setBounds(140, 10, 400, 30);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 24));
		this.panelBackClient.add(this.labelTitle);

		/**
		 * Label Nombre Cliente.
		 */
		this.labelNameClient = new JLabel("Nombre Cliente :");
		this.labelNameClient.setBounds(10, 60, 200, 25);
		this.labelNameClient.setForeground(new Color(192, 192, 192));
		this.labelNameClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelNameClient);

		/**
		 * Campo de texto para ingresar el nombre del cliente a registrar.
		 */
		this.txtNameClient = new JTextField();
		this.txtNameClient.setBounds(10, 80, 230, 25);
		this.txtNameClient.setBackground(new Color(127, 140, 141));
		this.txtNameClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtNameClient.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtNameClient.setForeground(Color.WHITE);
		this.txtNameClient.setEnabled(false);
		this.panelBackClient.add(this.txtNameClient);

		/**
		 * Label tipo de Equipo.
		 */
		this.labelTypeEquipment = new JLabel("Tipo de Equipo :");
		this.labelTypeEquipment.setBounds(10, 120, 200, 25);
		this.labelTypeEquipment.setForeground(new Color(192, 192, 192));
		this.labelTypeEquipment.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelTypeEquipment);

		/**
		 * ComboBox con el tipo de equipo.
		 */
		this.cmbTypeEquip = new JComboBox<>();
		this.cmbTypeEquip.setBounds(10, 140, 170, 30);
		this.cmbTypeEquip.setBackground(new Color(127, 140, 141));
		this.cmbTypeEquip.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbTypeEquip.setForeground(Color.WHITE);
		this.cmbTypeEquip.setModel(
				new DefaultComboBoxModel<>(new String[] { "Laptop", "Desktop", "Impresora", "Multifuncional" }));
		this.panelBackClient.add(this.cmbTypeEquip);

		/**
		 * Label marca del Equipo.
		 */
		this.labelMark = new JLabel("Marca :");
		this.labelMark.setBounds(10, 180, 100, 25);
		this.labelMark.setForeground(new Color(192, 192, 192));
		this.labelMark.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelMark);

		/**
		 * ComboBox con las marcas disponibles.
		 */
		this.cmbMark = new JComboBox<>();
		this.cmbMark.setBounds(10, 200, 170, 30);
		this.cmbMark.setBackground(new Color(127, 140, 141));
		this.cmbMark.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbMark.setForeground(Color.WHITE);
		this.cmbMark.setModel(new DefaultComboBoxModel<>(new String[] { "Acer", "Alienware", "Apple", "Asus", "Brother",
				"Dell", "HP", "Lenovo", "Samsung", "Toshiba", "Xerox" }));
		this.panelBackClient.add(this.cmbMark);

		/**
		 * Label Modelo del Equipo.
		 */
		this.labelModel = new JLabel("Modelo :");
		this.labelModel.setBounds(10, 240, 100, 25);
		this.labelModel.setForeground(new Color(192, 192, 192));
		this.labelModel.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelModel);

		/**
		 * Campo de texto para ingresar el modelo del equipo a registrar.
		 */
		this.txtModel = new JTextField();
		this.txtModel.setBounds(10, 260, 230, 25);
		this.txtModel.setBackground(new Color(127, 140, 141));
		this.txtModel.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModel.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtModel.setForeground(Color.WHITE);
		this.panelBackClient.add(this.txtModel);

		/**
		 * Label Número Serial del Equipo.
		 */
		this.labelSerialNumber = new JLabel("Número de Serie :");
		this.labelSerialNumber.setBounds(10, 300, 200, 25);
		this.labelSerialNumber.setForeground(new Color(192, 192, 192));
		this.labelSerialNumber.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelSerialNumber);

		/**
		 * Campo de texto para ingresar el número serial del equipo a registrar.
		 */
		this.txtSerialNumber = new JTextField();
		this.txtSerialNumber.setBounds(10, 320, 230, 25);
		this.txtSerialNumber.setBackground(new Color(127, 140, 141));
		this.txtSerialNumber.setFont(new Font("serif", Font.BOLD, 20));
		this.txtSerialNumber.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtSerialNumber.setForeground(Color.WHITE);
		this.panelBackClient.add(this.txtSerialNumber);

		/**
		 * Label Daño Reportado y Observaciones.
		 */
		this.labelObservations = new JLabel("Daño Reportado y Observaciones :");
		this.labelObservations.setBounds(255, 60, 300, 25);
		this.labelObservations.setForeground(new Color(192, 192, 192));
		this.labelObservations.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelObservations);

		/**
		 * TextPane con las observaciones del técnico.
		 */
		this.textPaneObservations = new JTextPane();
		this.textPaneObservations.setForeground(Color.BLACK);
		this.textPaneObservations.setFont(new Font("serif", Font.BOLD, 14));
		this.scrollObservations = new JScrollPane(this.textPaneObservations);
		this.scrollObservations.setBounds(255, 80, 330, 230);
		this.scrollObservations.setViewportView(this.textPaneObservations);
		this.panelBackClient.add(this.scrollObservations);

		/**
		 * Botón para ingresar el equipo en cuestión.
		 */
		this.btnRegisterEquip = new JButton("Registrar Equipo");
		this.btnRegisterEquip.setBounds(375, 310, 210, 35);
		this.btnRegisterEquip.setFont(new Font("serif", Font.BOLD, 20));
		this.btnRegisterEquip.setBackground(new Color(8, 85, 224));
		this.btnRegisterEquip.setForeground(Color.WHITE);
		this.btnRegisterEquip.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnRegisterEquip.addActionListener(this);
		this.panelBackClient.add(this.btnRegisterEquip);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Ingresa un equipo a la base de datos y lo asocia a un cliente.
		 */
		if (e.getSource() == this.btnRegisterEquip) {
			int validation = 0;
			String typeEquip, mark, model, serialNumber, day, month, year, status, observations;

			typeEquip = this.cmbTypeEquip.getSelectedItem().toString();
			mark = this.cmbMark.getSelectedItem().toString();
			model = this.txtModel.getText().trim();
			serialNumber = this.txtSerialNumber.getText().trim();
			observations = this.textPaneObservations.getText();
			status = "Nuevo Ingreso";

			Calendar calendar = Calendar.getInstance();

			day = Integer.toString(calendar.get(Calendar.DATE)); // Recuperamos el dia de ingreso del equipo
			month = Integer.toString(calendar.get(Calendar.MONTH) + 1); // Recuperamos el mes de ingreso del equipo
			year = Integer.toString(calendar.get(Calendar.YEAR)); // Recuperamos el año de ingreso del equipo

			/**
			 * Validacion de campos
			 */
			if (model.equals("")) {
				this.txtModel.setBackground(Color.red);
				validation++;
			}
			if (serialNumber.equals("")) {
				this.txtSerialNumber.setBackground(Color.red);
				validation++;
			}
			if (observations.equals("")) {
				this.textPaneObservations.setText("Sin observaciones.");
			}

			if (validation == 0) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn
							.prepareStatement("INSERT INTO equipos VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					pst.setInt(1, 0);
					pst.setInt(2, idClientUpdate);
					pst.setString(3, typeEquip);
					pst.setString(4, mark);
					pst.setString(5, model);
					pst.setString(6, serialNumber);
					pst.setString(7, day);
					pst.setString(8, month);
					pst.setString(9, year);
					pst.setString(10, observations);
					pst.setString(11, status);
					pst.setString(12, user);
					pst.setString(13, "");
					pst.setString(14, "");
					pst.executeUpdate();
					cn.close();

					this.txtModel.setBackground(Color.green);
					this.txtNameClient.setBackground(Color.green);
					this.txtSerialNumber.setBackground(Color.green);

					JOptionPane.showMessageDialog(null, "Registro exitoso");
					this.dispose();
					ClientInformation clientInformation = new ClientInformation();
					clientInformation.setVisible(true);
				} catch (Exception ex) {
					System.err.println("Error en registrar el equipo " + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al registrar el equipo!! Contacte al Administrador");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
			}
		}
	}
}
