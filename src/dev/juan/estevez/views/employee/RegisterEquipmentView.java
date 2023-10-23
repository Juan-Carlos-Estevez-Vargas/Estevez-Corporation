package dev.juan.estevez.views.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.EquipStates;
import dev.juan.estevez.enums.Equipments;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.States;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.persistence.EquipmentDAO;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.services.impl.EquipmentService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.cap.EquipRegister;
import dev.juan.estevez.utils.constants.CapConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterEquipmentView extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private int idClientUpdate;
	private ClientService clientController;
	private EquipmentService equipmentController;
	private JTextField txtName;
	private JTextField txtModel;
	private JTextField txtSerialNumber;
	private JComboBox<String> cmbType;
	private JComboBox<String> cmbMark;
	private JTextPane textPaneObservations;
	private JScrollPane scrollObservations;
	private JLabel labelTitle;
	private JPanel panel;
	private JButton btnRegisterEquip;
	private String user;
	private String nameClient;

	public RegisterEquipmentView() {
		user = LoginView.user;
		clientController = new ClientService(new ClientDAO());
		equipmentController = new EquipmentService(new EquipmentDAO());
		idClientUpdate = ManagementClientsView.id_cliente_update;
		initializeFrame();
		initComponents();
		loadData();
	}

	@Override
	public void initializeFrame() {
		setSize(615, 400);
		setTitle(String.format(CapConstants.REGISTER_EQUIPMENT_SESION, user));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
	}

	@Override
	public void setupMainPanel() {
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		panel.setBounds(615, 400, 615, 400);
		setContentPane(panel);
	}

	@Override
	public void initComponents() {
		setupMainPanel();
		setupLabels();
		setupTextFields();
		setupTextPane();
		setupComboBoxes();
		setupButtons();
		setupEvents();
	}

	@Override
	public void setupLabels() {
		JLabel labelTitle = GUIComponents.createLabel(CapConstants.EQUIPMENT_REGISTER, EquipRegister.LABEL_TITLE,
				panel);
		labelTitle.setFont(Fonts.BUTTON_FONT.getValue());
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

		GUIComponents.createLabel(CapConstants.CLIENT_NAME, EquipRegister.LABEL_NAME, panel);
		GUIComponents.createLabel(Equipments.TYPE.getValue(), EquipRegister.LABEL_TYPE, panel);
		GUIComponents.createLabel(Equipments.MARK.getValue(), EquipRegister.LABEL_MARK, panel);
		GUIComponents.createLabel(Equipments.MODEL.getValue(), EquipRegister.LABEL_MODEL, panel);
		GUIComponents.createLabel(Equipments.SERIAL_NUMBER.getValue(), EquipRegister.LABEL_SERIAL_NUMBER, panel);
		GUIComponents.createLabel(Equipments.OBSERVATIONS.getValue(), EquipRegister.LABEL_OBSERVATION, panel);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(EquipRegister.TXT_NAME, panel);
		txtModel = GUIComponents.createTextField(EquipRegister.TXT_MODEL, panel);
		txtSerialNumber = GUIComponents.createTextField(EquipRegister.TXT_SERIAL_NUMBER, panel);
		txtName.setEditable(false);
	}

	public void setupTextPane() {
		textPaneObservations = GUIComponents.createTextPane(EquipRegister.TEXT_PANE_OBSERVATIONS, panel);
	}

	private void setupComboBoxes() {
		// cmbType = GUIComponents.createComboBox(EquipRegister.CMB_TYPE,
		// new String[] { "Laptop", "Desktop", "Impresora", "Multifuncional" }, panel);
		// cmbMark = GUIComponents.createComboBox(EquipRegister.CMB_MARK,
		// new String[] { "Acer", "Alienware", "Apple", "Asus", "Brother",
		// "Dell", "HP", "Lenovo", "Samsung", "Toshiba", "Xerox" },
		// panel);
	}

	@Override
	public void setupButtons() {
		btnRegisterEquip = GUIComponents.createButton(CapConstants.EQUIPMENT_REGISTER, EquipRegister.BUTTON_REGISTER,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), panel);
	}

	@Override
	public void setupEvents() {
		btnRegisterEquip.addActionListener(this);
	}

	private void loadData() {
		Client client = clientController.getById(idClientUpdate);

		if (client != null) {
			txtName.setText(client.getName());
		}

		// TODO: Obtener los equipos y marcas desde base de datos y no dejarlos quemados
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterEquip) {
			registerEquipment();
		}
		/*
		 * if (e.getSource() == this.btnRegisterEquip) {
		 * int validation = 0;
		 * String typeEquip, mark, model, serialNumber, day, month, year, status,
		 * observations;
		 * 
		 * typeEquip = this.cmbTypeEquip.getSelectedItem().toString();
		 * mark = this.cmbMark.getSelectedItem().toString();
		 * model = this.txtModel.getText().trim();
		 * serialNumber = this.txtSerialNumber.getText().trim();
		 * observations = this.textPaneObservations.getText();
		 * status = "Nuevo Ingreso";
		 * 
		 * Calendar calendar = Calendar.getInstance();
		 * 
		 * day = Integer.toString(calendar.get(Calendar.DATE)); // Recuperamos el dia de
		 * ingreso del equipo
		 * month = Integer.toString(calendar.get(Calendar.MONTH) + 1); // Recuperamos el
		 * mes de ingreso del equipo
		 * year = Integer.toString(calendar.get(Calendar.YEAR)); // Recuperamos el a�o
		 * de ingreso del equipo
		 * 
		 * if (model.equals("") || model.length() >= 50) {
		 * this.txtModel.setBackground(Color.red);
		 * if (model.length() >= 50) {
		 * JOptionPane.showMessageDialog(null,
		 * "El campo MODELO no debe contener m�s de 50 caracteres");
		 * this.txtModel.requestFocus();
		 * }
		 * validation++;
		 * }
		 * if (serialNumber.equals("") || serialNumber.length() >= 50) {
		 * this.txtSerialNumber.setBackground(Color.red);
		 * if (serialNumber.length() >= 50) {
		 * JOptionPane.showMessageDialog(null,
		 * "El campo N�MERO SERIAL no debe contener m�s de 50 caracteres");
		 * this.txtModel.requestFocus();
		 * }
		 * validation++;
		 * }
		 * if (observations.equals("")) {
		 * this.textPaneObservations.setText("Sin observaciones.");
		 * }
		 * 
		 * if (validation == 0) {
		 * try {
		 * Connection cn = (Connection) DatabaseConnection.connect();
		 * PreparedStatement pst = (PreparedStatement) cn
		 * .prepareStatement("INSERT INTO equipos VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
		 * );
		 * 
		 * pst.setInt(1, 0);
		 * pst.setInt(2, idClientUpdate);
		 * pst.setString(3, typeEquip);
		 * pst.setString(4, mark);
		 * pst.setString(5, model);
		 * pst.setString(6, serialNumber);
		 * pst.setString(7, day);
		 * pst.setString(8, month);
		 * pst.setString(9, year);
		 * pst.setString(10, observations);
		 * pst.setString(11, status);
		 * pst.setString(12, user);
		 * pst.setString(13, "");
		 * pst.setString(14, "");
		 * pst.executeUpdate();
		 * 
		 * pst.close();
		 * cn.close();
		 * 
		 * this.txtModel.setBackground(Color.green);
		 * this.txtNameClient.setBackground(Color.green);
		 * this.txtSerialNumber.setBackground(Color.green);
		 * 
		 * JOptionPane.showMessageDialog(null, "Registro exitoso");
		 * this.dispose();
		 * ClientInformationView clientInformation = new ClientInformationView();
		 * clientInformation.setVisible(true);
		 * } catch (Exception ex) {
		 * System.err.println("Error en registrar el equipo " + ex);
		 * JOptionPane.showMessageDialog(null,
		 * "��Error al registrar el equipo!! Contacte al Administrador");
		 * }
		 * } else {
		 * JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
		 * }
		 * }
		 */
	}

	private void registerEquipment() {
		Equipment equipment = createEquipmentFromInputs();
		int validation = validateFields(equipment);

		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}

		if (validation == 0) {
			if (equipmentController.create(equipment) == 1) {
				StringUtils.showMessage(Constants.SUCCESSFUL_REGISTRATION);
				ViewUtils.openPanel(new ClientInformationView(), this);
			}
		}
	}

	private Equipment createEquipmentFromInputs() {
		Equipment equipment = new Equipment();
		equipment.setType(cmbMark.getSelectedItem().toString());
		equipment.setMark(cmbMark.getSelectedItem().toString());
		equipment.setModel(txtModel.getText());
		equipment.setSerialNumber(txtSerialNumber.getText());
		equipment.setObservations(textPaneObservations.getText());
		equipment.setStatus(EquipStates.NEW_ENTRY.getValue());
		equipment.setLastModification(user);
		return equipment;
	}

	private int validateFields(Equipment equipment) {
		int validation = 0;
		/*
		 * FieldValidator.validateField(client.getClientEmail(), txtEmail)
		 * + FieldValidator.validateNameField(client.getClientName(), txtName)
		 * + FieldValidator.validatePhoneField(client.getClientPhone(), txtPhone)
		 * + FieldValidator.validateAdressField(client.getClientAddress(), txtAdress);
		 */
		return validation;
	}

}
