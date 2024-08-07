package dev.juan.estevez.views.technical;

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

import dev.juan.estevez.utils.database.DatabaseConnection;
import dev.juan.estevez.views.LoginView;

/**
 * Frame con la informaci�n general de un equipo espec�fico.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class EquipmentInformationTechnical extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String user = "";
	public static int idEquipment = 0;
	private JLabel labelTittle;
	private JLabel labelTypeEquip;
	private JLabel labelMark;
	private JLabel labelModel;
	private JLabel labelSerialNumber;
	private JLabel labelModifyBy;
	private JLabel labelDateOfAdmission;
	private JLabel labelStatus;
	private JLabel labelObservations;
	private JLabel labelComments;
	private JTextField txtModel;
	private JTextField txtSerialNumber;
	private JTextField txtDateOfAdmission;
	private JTextField txtModifyBy;
	private JComboBox<String> cmbStatus;
	private JTextField cmbTypeEquip;
	private JTextField txtMark;
	private JTextPane textPaneObservations;
	private JTextPane textPaneComments;
	private JScrollPane scrollObservations;
	private JScrollPane scrollComments;
	private JPanel container;
	private JButton btnUpdateEquipment;

	/**
	 * Constructor de clase.
	 */
	public EquipmentInformationTechnical() {
		initComponents();
		this.user = LoginView.user;
		this.setResizable(false);
		this.setSize(610, 460);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		idEquipment = ManagementEquips.id_equip_update;
		this.setTitle("Equipo registrado con el ID " + idEquipment + " - Sesi�n de " + user);

		/**
		 * Consultando la informaci�n general del equipo
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.connect();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM equipos WHERE id_equipo = '" + idEquipment + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.cmbTypeEquip.setText(rs.getString(3));
				this.txtMark.setText(rs.getString(4));
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
				this.textPaneObservations.setText(rs.getString("observaciones"));
				this.textPaneComments.setText(rs.getString("comentarios_tecnicos"));
				this.labelComments
						.setText("Comentarios y Actualizacion del t�cnico " + rs.getString("revision_tecnica_de"));
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar informaci�n del equipo " + e);
		}
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(610, 460, 610, 460);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Informaci�n del Equipo");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(100, 10, 400, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Tipo de Equipo.
		 */
		this.labelTypeEquip = new JLabel("Tipo de Equipo :");
		this.labelTypeEquip.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelTypeEquip.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTypeEquip.setBounds(10, 60, 200, 20);
		this.container.add(this.labelTypeEquip);

		/**
		 * Label Marca.
		 */
		this.labelMark = new JLabel("Marca :");
		this.labelMark.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelMark.setForeground(new java.awt.Color(192, 192, 192));
		this.labelMark.setBounds(10, 120, 100, 20);
		this.container.add(this.labelMark);

		/**
		 * Label Modelo.
		 */
		this.labelModel = new JLabel("Modelo :");
		this.labelModel.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModel.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModel.setBounds(10, 180, 100, 20);
		this.container.add(this.labelModel);

		/**
		 * Label N�mero de Serie.
		 */
		this.labelSerialNumber = new JLabel("N�mero de Serie :");
		this.labelSerialNumber.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelSerialNumber.setForeground(new java.awt.Color(192, 192, 192));
		this.labelSerialNumber.setBounds(10, 240, 200, 20);
		this.container.add(this.labelSerialNumber);

		/**
		 * Label Modificado por.
		 */
		this.labelModifyBy = new JLabel("Modificado por :");
		this.labelModifyBy.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModifyBy.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModifyBy.setBounds(10, 300, 200, 20);
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
		 * Label Da�o Reportado y Observaciones.
		 */
		this.labelObservations = new JLabel("Da�o reportado y observaciones :");
		this.labelObservations.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelObservations.setForeground(new java.awt.Color(192, 192, 192));
		this.labelObservations.setBounds(260, 110, 200, 20);
		this.container.add(this.labelObservations);

		/**
		 * Label Comentarios y Actualizaci�n del T�cnico.
		 */
		this.labelComments = new JLabel("Comentarios y Actualizaci�n del T�cnico :");
		this.labelComments.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelComments.setForeground(new java.awt.Color(192, 192, 192));
		this.labelComments.setBounds(260, 260, 300, 20);
		this.container.add(this.labelComments);

		/**
		 * Campo de texto con la informaci�n del modelo del equipo.
		 */
		this.txtModel = new JTextField();
		this.txtModel.setBounds(10, 200, 230, 30);
		this.txtModel.setBackground(new Color(127, 140, 141));
		this.txtModel.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModel.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtModel.setEditable(false);
		this.txtModel.setForeground(Color.WHITE);
		this.container.add(this.txtModel);

		/**
		 * Campo de texto con la informaci�n del n�mero de serie del equipo.
		 */
		this.txtSerialNumber = new JTextField();
		this.txtSerialNumber.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtSerialNumber.setBounds(10, 260, 230, 30);
		this.txtSerialNumber.setBackground(new Color(127, 140, 141));
		this.txtSerialNumber.setFont(new Font("serif", Font.BOLD, 20));
		this.txtSerialNumber.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtSerialNumber.setForeground(Color.WHITE);
		this.txtSerialNumber.setEditable(false);
		this.container.add(this.txtSerialNumber);

		/**
		 * Campo de texto con la informaci�n de la fecha de ingreso del equipo.
		 */
		this.txtDateOfAdmission = new JTextField();
		this.txtDateOfAdmission.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtDateOfAdmission.setBounds(260, 80, 180, 30);
		this.txtDateOfAdmission.setBackground(new Color(127, 140, 141));
		this.txtDateOfAdmission.setFont(new Font("serif", Font.BOLD, 20));
		this.txtDateOfAdmission.setForeground(Color.WHITE);
		this.txtDateOfAdmission.setEditable(false);
		this.container.add(this.txtDateOfAdmission);

		/**
		 * Campo de texto con la informaci�n de qui�n modific� por �ltima vez el equipo.
		 */
		this.txtModifyBy = new JTextField();
		this.txtModifyBy.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtModifyBy.setEnabled(false);
		this.txtModifyBy.setBounds(10, 320, 230, 30);
		this.txtModifyBy.setBackground(new Color(127, 140, 141));
		this.txtModifyBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModifyBy.setForeground(Color.WHITE);
		this.container.add(this.txtModifyBy);

		/**
		 * ComboBox con el estado de equipo.
		 */
		this.cmbStatus = new JComboBox<String>();
		this.cmbStatus.setBounds(460, 80, 120, 30);
		this.cmbStatus.setBackground(new Color(127, 140, 141));
		this.cmbStatus.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbStatus.setForeground(Color.WHITE);
		this.cmbStatus.setModel(new DefaultComboBoxModel<>(
				new String[] { "Nuevo Ingreso", "En Revisi�n", "Entregado", "No Reparado", "Reparado" }));
		this.container.add(this.cmbStatus);

		/**
		 * ComboBox con el tipo de equipo.
		 */
		this.cmbTypeEquip = new JTextField();
		this.cmbTypeEquip.setBounds(10, 80, 170, 30);
		this.cmbTypeEquip.setBackground(new Color(127, 140, 141));
		this.cmbTypeEquip.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbTypeEquip.setForeground(Color.WHITE);
		this.cmbTypeEquip.setEnabled(false);
		this.container.add(this.cmbTypeEquip);

		/**
		 * ComboBox con la marca del equipo.
		 */
		this.txtMark = new JTextField();
		this.txtMark.setBounds(10, 140, 170, 30);
		this.txtMark.setBackground(new Color(127, 140, 141));
		this.txtMark.setFont(new Font("serif", Font.BOLD, 14));
		this.txtMark.setForeground(Color.WHITE);
		this.txtMark.setEnabled(false);
		this.container.add(this.txtMark);

		/**
		 * TextPane con las observaciones generales del equipo.
		 */
		this.textPaneObservations = new JTextPane();
		this.textPaneObservations.setForeground(Color.BLACK);
		this.textPaneObservations.setFont(new Font("serif", Font.BOLD, 14));
		this.textPaneObservations.setEditable(false);
		this.scrollObservations = new JScrollPane(this.textPaneObservations);
		this.scrollObservations.setBounds(260, 130, 320, 120);
		this.scrollObservations.setViewportView(this.textPaneObservations);
		this.container.add(this.scrollObservations);

		/**
		 * TextPane con las observaciones del t�cnico.
		 */
		this.textPaneComments = new JTextPane();
		this.textPaneComments.setForeground(Color.BLACK);
		this.textPaneComments.setFont(new Font("serif", Font.BOLD, 14));
		this.scrollComments = new JScrollPane(this.textPaneComments);
		this.scrollComments.setBounds(260, 280, 320, 125);
		this.scrollComments.setViewportView(this.textPaneComments);
		this.container.add(this.scrollComments);

		/**
		 * Bot�n para actualizar el equipo en cuesti�n.
		 */
		this.btnUpdateEquipment = new JButton("Actualizar Equipo");
		this.btnUpdateEquipment.setBounds(20, 368, 210, 35);
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
		this.txtSerialNumber.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnUpdateEquipment) {
			int validation = 0;
			String typeEquip, mark, model, serialNumber, status, observations, coments;

			typeEquip = this.cmbTypeEquip.getText().trim();
			mark = this.txtMark.getText().trim();
			status = (String) this.cmbStatus.getSelectedItem();
			model = this.txtModel.getText().trim();
			serialNumber = this.txtSerialNumber.getText().trim();
			observations = this.textPaneObservations.getText().trim();
			coments = this.textPaneComments.getText().trim();

			/**
			 * Validaci�n de campos.
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
			if (coments.equals("")) {
				this.textPaneComments.setText("Sin comentarios.");
			}

			if (validation == 0) {
				try {
					Connection cn = (Connection) DatabaseConnection.connect();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
							"UPDATE equipos SET tipo_equipo = ?, marca = ?, modelo = ?, num_serie = ?, observaciones = ?, estatus = ?, "
									+ "ultima_modificacion = ?, comentarios_tecnicos = ? WHERE id_equipo = '"
									+ idEquipment + "'");

					pst.setString(1, typeEquip);
					pst.setString(2, mark);
					pst.setString(3, model);
					pst.setString(4, serialNumber);
					pst.setString(5, observations);
					pst.setString(6, status);
					pst.setString(7, user);
					pst.setString(8, coments);

					pst.executeUpdate();
					cn.close();

					clean();

					this.txtDateOfAdmission.setBackground(Color.green);
					this.txtModel.setBackground(Color.green);
					this.txtSerialNumber.setBackground(Color.green);
					this.txtModifyBy.setText(user);

					JOptionPane.showMessageDialog(null, "Actualizaci�n correcta");
					this.dispose();
					ManagementEquips managementEquips = new ManagementEquips();
					managementEquips.setVisible(true);
				} catch (SQLException ex) {
					System.err.println("Error al actualizar equipo " + ex);
					JOptionPane.showMessageDialog(null, "��Error al actualizar equipo!! Contacte al Administrador");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
			}
		}

	}

}
