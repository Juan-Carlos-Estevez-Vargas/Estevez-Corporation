package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import ventanas.Login;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class EquipmentInformation extends JFrame{

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private String user = "";
	private int user_update = 0;
	private DefaultTableModel model = new DefaultTableModel();
	public static int idEquipment = 0;
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
	private JPanel container;
	private JButton btnUpdateEquipment;
	private JScrollPane scrollObservations;

	/**
	 * Constructor de clase.
	 */
	public EquipmentInformation() {
		initComponents();
		this.user = Login.user;
		this.setResizable(false);
		this.setSize(670, 530);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		this.container.setBounds(670, 530, 670, 530);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Información del Equipo");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(200, 10, 400, 30);
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
		this.labelDateOfAdmission.setBounds(320, 60, 200, 20);
		this.container.add(this.labelDateOfAdmission);
		
		/**
		 * Label Estatus.
		 */
		this.labelStatus = new JLabel("Estatus :");
		this.labelStatus.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelStatus.setForeground(new java.awt.Color(192, 192, 192));
		this.labelStatus.setBounds(520, 60, 100, 20);
		this.container.add(this.labelStatus);
		
		/**
		 * Label Daño Reportado y Observaciones.
		 */
		this.labelObservations = new JLabel("Daño reportado y observaciones :");
		this.labelObservations.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelObservations.setForeground(new java.awt.Color(192, 192, 192));
		this.labelObservations.setBounds(320, 110, 200, 20);
		this.container.add(this.labelObservations);
		
		/**
		 * Label Comentarios y Actualización del Técnico.
		 */
		this.labelComments = new JLabel("Comentarios y Actualización del Técnico :");
		this.labelComments.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelComments.setForeground(new java.awt.Color(192, 192, 192));
		this.labelComments.setBounds(320, 260, 300, 20);
		this.container.add(this.labelComments);

		/**
		 * Campo de texto con la información del nombre del cliente.
		 */
		this.txtName = new JTextField();
		this.txtName.setBounds(10, 80, 230, 30);
		this.txtName.setBackground(new Color(127, 140, 141));
		this.txtName.setFont(new Font("serif", Font.BOLD, 20));
		this.txtName.setHorizontalAlignment(JLabel.LEFT);
		this.txtName.setForeground(Color.WHITE);
		this.container.add(this.txtName);

		/**
		 * Campo de texto con la información del modelo del equipo.
		 */
		this.txtModel = new JTextField();
		this.txtModel.setBounds(10, 260, 230, 30);
		this.txtModel.setBackground(new Color(127, 140, 141));
		this.txtModel.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModel.setHorizontalAlignment(JLabel.LEFT);
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
		this.txtSerialNumber.setHorizontalAlignment(JLabel.LEFT);
		this.txtSerialNumber.setForeground(Color.WHITE);
		this.container.add(this.txtSerialNumber);

		/**
		 * Campo de texto con la información de la fecha de ingreso del equipo.
		 */
		this.txtDateOfAdmission = new JTextField();
		this.txtDateOfAdmission.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtDateOfAdmission.setBounds(320, 80, 180, 30);
		this.txtDateOfAdmission.setBackground(new Color(127, 140, 141));
		this.txtDateOfAdmission.setFont(new Font("serif", Font.BOLD, 20));
		this.txtDateOfAdmission.setForeground(Color.WHITE);
		this.container.add(this.txtDateOfAdmission);

		/**
		 * Campo de texto con la información de quién registró el equipo.
		 */
		this.txtModifyBy = new JTextField();
		this.txtModifyBy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtModifyBy.setEnabled(false);
		this.txtModifyBy.setBounds(10, 380, 230, 30);
		this.txtModifyBy.setBackground(new Color(127, 140, 141));
		this.txtModifyBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModifyBy.setForeground(Color.WHITE);
		this.container.add(this.txtModifyBy);

		/**
		 * ComboBox con el estatus de equipo.
		 */
		this.cmbStatus = new JComboBox<String>();
		this.cmbStatus.setBounds(520, 80, 120, 30);
		this.cmbStatus.setBackground(new Color(127, 140, 141));
		this.cmbStatus.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbStatus.setForeground(Color.WHITE);
		this.container.add(this.cmbStatus);
		
		/**
		 * ComboBox con el tipo de equipo.
		 */
		this.cmbTypeEquip = new JComboBox<String>();
		this.cmbTypeEquip.setBounds(10, 140, 170, 30);
		this.cmbTypeEquip.setBackground(new Color(127, 140, 141));
		this.cmbTypeEquip.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbTypeEquip.setForeground(Color.WHITE);
		this.container.add(this.cmbTypeEquip);

		/**
		 * ComboBox con la marca del equipo.
		 */
		this.cmbMark = new JComboBox<String>();
		this.cmbMark.setBounds(10, 200, 170, 30);
		this.cmbMark.setBackground(new Color(127, 140, 141));
		this.cmbMark.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbMark.setForeground(Color.WHITE);
		this.container.add(this.cmbMark);
		
		/**
		 * TextPane cons las observaciones generales del equipo.
		 */
		this.textPaneObservations = new JTextPane();
		this.textPaneObservations.setForeground(Color.BLACK);
		this.textPaneObservations.setFont(new Font("serif", Font.BOLD, 14));
		this.scrollObservations = new JScrollPane(this.textPaneObservations);
		this.scrollObservations.setBounds(320, 130, 320, 120);
		this.scrollObservations.setViewportView(this.textPaneObservations);
		this.container.add(this.scrollObservations);

		
		/**
		 * Botón para actualizar el equipo en cuestión.
		 */
		this.btnUpdateEquipment = new JButton("Actualizar Equipo");
		this.btnUpdateEquipment.setBounds(420, 420, 210, 35);
		this.btnUpdateEquipment.setFont(new Font("serif", Font.BOLD, 20));
		this.btnUpdateEquipment.setBackground(new Color(8, 85, 224));
		this.btnUpdateEquipment.setForeground(Color.WHITE);
		this.btnUpdateEquipment.setHorizontalAlignment(JButton.CENTER);
		//this.btnUpdateEquipment.addActionListener(this);
		this.container.add(this.btnUpdateEquipment);

	}

}
