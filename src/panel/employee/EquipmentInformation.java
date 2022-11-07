package panel.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import panel.utilities.Login;
import util.DatabaseConnection;

/**
 * Frame con la información del equipo asociado a un cliente específico.
 *
 * @author
 *
 */
public class EquipmentInformation extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private String nameClient = "", user = "";
	public static int idEquipment = 0;
	public static int idClientUpdate = 0;
	private JLabel labelTittle;
	private JLabel labelName;
	private JLabel labelTypeEquip;
	private JLabel labelMark;
	private JLabel labelModel;
	private JLabel labelSerialNumber;
	private JLabel labelModifyBy;
	private JLabel labelDateOfAdmission;
	private JLabel labelStatus;
	private JLabel labelObservations;
	private JLabel labelComments;
	private JTextField txtName;
	private JTextField txtModel;
	private JTextField txtSerialNumber;
	private JTextField txtDateOfAdmission;
	private JTextField txtModifyBy;
	private JComboBox<String> cmbStatus;
	private JComboBox<String> cmbTypeEquip;
	private JComboBox<String> cmbMark;
	private JTextPane textPaneObservations;
	private JTextPane textPaneComments;
	private JScrollPane scrollObservations;
	private JScrollPane scrollComments;
	private JPanel container;
	private JButton btnUpdateEquipment;

	/**
	 * Constructor de clase.
	 */
	public EquipmentInformation() {
		initComponents();
		this.user = Login.user;
		this.setResizable(false);
		this.setSize(610, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		idEquipment = ClientInformation.idEquipment;
		idClientUpdate = ManagementClients.id_cliente_update;

		/*
		 * Recuperamos el nombre del cliente
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
					"SELECT nombre_cliente FROM clientes WHERE id_cliente = '" + idClientUpdate + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameClient = rs.getString("nombre_cliente");
				this.setTitle("Equipo del cliente " + nameClient);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar el nombre del cliente " + e);
		}

		/**
		 * Consultando la información general del equipo
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM equipos WHERE id_equipo = '" + idEquipment + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.cmbTypeEquip.setSelectedItem(rs.getString(3));
				this.cmbMark.setSelectedItem(rs.getString(4));
				this.cmbStatus.setSelectedItem(rs.getString(11));
				this.txtModel.setText(rs.getString("modelo"));
				this.txtSerialNumber.setText(rs.getString("num_serie"));
				this.txtModifyBy.setText(rs.getString("ultima_modificacion"));
				this.textPaneObservations.setText(rs.getString(10));

				/**
				 * Recuperando la fecha
				 */
				String dia = "", mes = "", annio = "";
				dia = rs.getString("dia_ingreso");
				mes = rs.getString("mes_ingreso");
				annio = rs.getString("annio_ingreso");

				this.txtDateOfAdmission.setText(dia + " del " + mes + " del " + annio);
				this.txtName.setText(nameClient);
				this.textPaneObservations.setText(rs.getString("observaciones"));
				this.textPaneComments.setText(rs.getString("comentarios_tecnicos"));
				this.labelComments
						.setText("Comentarios y Actualizacion del técnico " + rs.getString("revision_tecnica_de"));
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar información del equipo " + e);
		}
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
		this.container.setBounds(610, 500, 610, 500);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Información del Equipo");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(100, 10, 400, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Nombre Cliente.
		 */
		this.labelName = new JLabel("Nombre del Cliente :");
		this.labelName.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelName.setForeground(new java.awt.Color(192, 192, 192));
		this.labelName.setBounds(10, 60, 200, 20);
		this.container.add(this.labelName);

		/**
		 * Label Tipo de Equipo.
		 */
		this.labelTypeEquip = new JLabel("Tipo de Equipo :");
		this.labelTypeEquip.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelTypeEquip.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTypeEquip.setBounds(10, 120, 200, 20);
		this.container.add(this.labelTypeEquip);

		/**
		 * Label Marca.
		 */
		this.labelMark = new JLabel("Marca :");
		this.labelMark.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelMark.setForeground(new java.awt.Color(192, 192, 192));
		this.labelMark.setBounds(10, 180, 100, 20);
		this.container.add(this.labelMark);

		/**
		 * Label Modelo.
		 */
		this.labelModel = new JLabel("Modelo :");
		this.labelModel.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModel.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModel.setBounds(10, 240, 100, 20);
		this.container.add(this.labelModel);

		/**
		 * Label Número de Serie.
		 */
		this.labelSerialNumber = new JLabel("Número de Serie :");
		this.labelSerialNumber.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelSerialNumber.setForeground(new java.awt.Color(192, 192, 192));
		this.labelSerialNumber.setBounds(10, 300, 200, 20);
		this.container.add(this.labelSerialNumber);

		/**
		 * Label Modificado por.
		 */
		this.labelModifyBy = new JLabel("Modificado por :");
		this.labelModifyBy.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModifyBy.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModifyBy.setBounds(10, 360, 200, 20);
		this.container.add(this.labelModifyBy);

		/**
		 * Label Fecha de Ingreso.
		 */
		this.labelDateOfAdmission = new JLabel("Fecha de Ingreso :");
		this.labelDateOfAdmission.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelDateOfAdmission.setForeground(new java.awt.Color(192, 192, 192));
		this.labelDateOfAdmission.setBounds(260, 60, 200, 20);
		this.container.add(this.labelDateOfAdmission);

		/**
		 * Label Estado.
		 */
		this.labelStatus = new JLabel("Estatus :");
		this.labelStatus.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelStatus.setForeground(new java.awt.Color(192, 192, 192));
		this.labelStatus.setBounds(460, 60, 100, 20);
		this.container.add(this.labelStatus);

		/**
		 * Label Daño Reportado y Observaciones.
		 */
		this.labelObservations = new JLabel("Daño reportado y observaciones :");
		this.labelObservations.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelObservations.setForeground(new java.awt.Color(192, 192, 192));
		this.labelObservations.setBounds(260, 110, 200, 20);
		this.container.add(this.labelObservations);

		/**
		 * Label Comentarios y Actualización del Técnico.
		 */
		this.labelComments = new JLabel("Comentarios y Actualización del Técnico :");
		this.labelComments.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelComments.setForeground(new java.awt.Color(192, 192, 192));
		this.labelComments.setBounds(260, 260, 300, 20);
		this.container.add(this.labelComments);

		/**
		 * Campo de texto con la información del nombre del cliente.
		 */
		this.txtName = new JTextField();
		this.txtName.setBounds(10, 80, 230, 30);
		this.txtName.setBackground(new Color(127, 140, 141));
		this.txtName.setFont(new Font("serif", Font.BOLD, 20));
		this.txtName.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtName.setForeground(Color.WHITE);
		this.txtName.setEditable(false);
		this.container.add(this.txtName);

		/**
		 * Campo de texto con la información del modelo del equipo.
		 */
		this.txtModel = new JTextField();
		this.txtModel.setBounds(10, 260, 230, 30);
		this.txtModel.setBackground(new Color(127, 140, 141));
		this.txtModel.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModel.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtModel.setForeground(Color.WHITE);
		this.container.add(this.txtModel);

		/**
		 * Campo de texto con la información del número de serie del equipo.
		 */
		this.txtSerialNumber = new JTextField();
		this.txtSerialNumber.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtSerialNumber.setBounds(10, 320, 230, 30);
		this.txtSerialNumber.setBackground(new Color(127, 140, 141));
		this.txtSerialNumber.setFont(new Font("serif", Font.BOLD, 20));
		this.txtSerialNumber.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtSerialNumber.setForeground(Color.WHITE);
		this.container.add(this.txtSerialNumber);

		/**
		 * Campo de texto con la información de la fecha de ingreso del equipo.
		 */
		this.txtDateOfAdmission = new JTextField();
		this.txtDateOfAdmission.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtDateOfAdmission.setBounds(260, 80, 180, 30);
		this.txtDateOfAdmission.setBackground(new Color(127, 140, 141));
		this.txtDateOfAdmission.setFont(new Font("serif", Font.BOLD, 20));
		this.txtDateOfAdmission.setForeground(Color.WHITE);
		this.txtDateOfAdmission.setEnabled(false);
		this.container.add(this.txtDateOfAdmission);

		/**
		 * Campo de texto con la información de quién modificó por última vez el equipo.
		 */
		this.txtModifyBy = new JTextField();
		this.txtModifyBy.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtModifyBy.setEnabled(false);
		this.txtModifyBy.setBounds(10, 380, 230, 30);
		this.txtModifyBy.setBackground(new Color(127, 140, 141));
		this.txtModifyBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModifyBy.setForeground(Color.WHITE);
		this.container.add(this.txtModifyBy);

		/**
		 * ComboBox con el estado de equipo.
		 */
		this.cmbStatus = new JComboBox<>();
		this.cmbStatus.setBounds(460, 80, 120, 30);
		this.cmbStatus.setBackground(new Color(127, 140, 141));
		this.cmbStatus.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbStatus.setForeground(Color.WHITE);
		this.cmbStatus.setModel(new DefaultComboBoxModel<>(
				new String[] { "Nuevo Ingreso", "No repaired", "En revision", "Reparado", "Entregado" }));
		this.container.add(this.cmbStatus);

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
		this.container.add(this.cmbTypeEquip);

		/**
		 * ComboBox con la marca del equipo.
		 */
		this.cmbMark = new JComboBox<>();
		this.cmbMark.setBounds(10, 200, 170, 30);
		this.cmbMark.setBackground(new Color(127, 140, 141));
		this.cmbMark.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbMark.setForeground(Color.WHITE);
		this.cmbMark.setModel(new DefaultComboBoxModel<>(new String[] { "Acer", "Alienware", "Apple", "Asus", "Brother",
				"Dell", "HP", "Lenovo", "Samsung", "Toshiba", "Xerox" }));
		this.container.add(this.cmbMark);

		/**
		 * TextPane con las observaciones generales del equipo.
		 */
		this.textPaneObservations = new JTextPane();
		this.textPaneObservations.setForeground(Color.BLACK);
		this.textPaneObservations.setFont(new Font("serif", Font.BOLD, 14));
		this.scrollObservations = new JScrollPane(this.textPaneObservations);
		this.scrollObservations.setBounds(260, 130, 320, 120);
		this.scrollObservations.setViewportView(this.textPaneObservations);
		this.container.add(this.scrollObservations);

		/**
		 * TextPane con las observaciones del técnico.
		 */
		this.textPaneComments = new JTextPane();
		this.textPaneComments.setForeground(Color.BLACK);
		this.textPaneComments.setFont(new Font("serif", Font.BOLD, 14));
		this.textPaneComments.setEditable(false);
		this.scrollComments = new JScrollPane(this.textPaneComments);
		this.scrollComments.setBounds(260, 280, 320, 125);
		this.scrollComments.setViewportView(this.textPaneComments);
		this.container.add(this.scrollComments);

		/**
		 * Botón para actualizar el equipo en cuestión.
		 */
		this.btnUpdateEquipment = new JButton("Actualizar Equipo");
		this.btnUpdateEquipment.setBounds(370, 415, 210, 35);
		this.btnUpdateEquipment.setFont(new Font("serif", Font.BOLD, 20));
		this.btnUpdateEquipment.setBackground(new Color(8, 85, 224));
		this.btnUpdateEquipment.setForeground(Color.WHITE);
		this.btnUpdateEquipment.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnUpdateEquipment.addActionListener(this);
		this.container.add(this.btnUpdateEquipment);

	}

	/**
	 * Limpia los campos de texto.
	 */
	public void clean() {
		this.txtDateOfAdmission.setText("");
		this.txtModel.setText("");
		this.txtName.setText("");
		this.txtSerialNumber.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnUpdateEquipment) {
			int validation = 0;
			String typeEquip, mark, model, serialNumber, status, observations;

			typeEquip = this.cmbTypeEquip.getSelectedItem().toString();
			mark = this.cmbMark.getSelectedItem().toString();
			status = this.cmbStatus.getSelectedItem().toString();

			model = this.txtModel.getText().trim();
			serialNumber = this.txtSerialNumber.getText().trim();
			observations = this.textPaneObservations.getText().trim();

			/**
			 * Validación de campos.
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
				this.textPaneObservations.setText("Sin observaciones");
			}

			if (validation == 0) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
							"UPDATE equipos SET tipo_equipo = ?, marca = ?, modelo = ?, num_serie = ?, observaciones = ?, estatus = ?, "
									+ "ultima_modificacion = ? WHERE id_equipo = '" + idEquipment + "'");

					pst.setString(1, typeEquip);
					pst.setString(2, mark);
					pst.setString(3, model);
					pst.setString(4, serialNumber);
					pst.setString(5, observations);
					pst.setString(6, status);
					pst.setString(7, user);

					pst.executeUpdate();
					cn.close();

					clean();

					this.txtName.setBackground(Color.green);
					this.txtDateOfAdmission.setBackground(Color.green);
					this.txtModel.setBackground(Color.green);
					this.txtSerialNumber.setBackground(Color.green);
					this.txtModifyBy.setText(user);

					JOptionPane.showMessageDialog(null, "Actualización correcta");
					this.dispose();
				} catch (SQLException ex) {
					System.err.println("Error al actualizar equipo " + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al actualizar equipo!! Contacte al Administrador");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
			}

		}
	}

}
