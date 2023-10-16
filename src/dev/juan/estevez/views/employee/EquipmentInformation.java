package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Equipments;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.persistence.EquipmentDAO;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.services.impl.EquipmentService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.EquipmentBounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public class EquipmentInformation extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private String user = "";
	private EquipmentService equipmentController;
	private ClientService clientController;
	public int idEquipment = 0;
	public static int idClientUpdate = 0;
	private JTextField txtName;
	private JTextField txtModel;
	private JTextField txtSerialNumber;
	private JTextField txtDateOfAdmission;
	private JTextField txtModifyBy;
	private JTextField txtStatus;
	private JTextField txtTypeEquip;
	private JTextField txtMark;
	private JTextPane textPaneObservations;
	private JTextPane textPaneComments;
	private JPanel container;
	private JButton btnUpdateEquipment;

	public EquipmentInformation(int idEquipment) {
		this.user = LoginView.user;
		this.idEquipment = idEquipment;
		idClientUpdate = ManagementClientsView.id_cliente_update;
		equipmentController = new EquipmentService(new EquipmentDAO());
		clientController = new ClientService(new ClientDAO());
		initializeFrame();
		initComponents();
		loadEquipmentData();
	}

	@Override
	public void initializeFrame() {
		setSize(640, 500);
		setTitle("Equipo del cliente " + user + " - Sesión de " + user);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
	}

	@Override
	public void initComponents() {
		setupMainPanel();
		setupLabels();
		setupTextFields();
		setupTextPane();
		setupButtons();
		setupEvents();
	}

	@Override
	public void setupMainPanel() {
		container = new JPanel();
		container.setBackground(Colors.BACKGROUND_COLOR.getValue());
		container.setLayout(null);
		container.setBounds(640, 500, 640, 500);
		setContentPane(container);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel("Información del Equipo " + user, EquipmentBounds.LABEL_EQUIPMENT_INFORMATION_TITLE, container);
		GUIComponents.createLabel("Nombre del Cliente", EquipmentBounds.LABEL_EQUIPMENT_CLIENT_NAME, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.TYPE.getValue(), EquipmentBounds.LABEL_EQUIPMENT_TYPE, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.MARK.getValue(), EquipmentBounds.LABEL_EQUIPMENT_MARK, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.MODEL.getValue(), EquipmentBounds.LABEL_EQUIPMENT_MODEL, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.SERIAL_NUMBER.getValue(), EquipmentBounds.LABEL_EQUIPMENT_SERIAL_NUMBER, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.LAST_MODIFICATION.getValue(), EquipmentBounds.LABEL_EQUIPMENT_LAST_MODIFICATION, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.DATE_OF_ADMISSION.getValue(), EquipmentBounds.LABEL_EQUIPMENT_DATE_OF_ADMISSION, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.STATUS.getValue(), EquipmentBounds.LABEL_EQUIPMENT_STATUS, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.OBSERVATION.getValue(), EquipmentBounds.LABEL_EQUIPMENT_OBSERVATION, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
		GUIComponents.createLabel(Equipments.TECHNICAL_COMMENTS.getValue(), EquipmentBounds.LABEL_EQUIPMENT_COMMENTS, container).setFont(Fonts.PANEL_LABEL_FONT.getValue());;
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_NAME, container);
		txtModel = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_MODEL, container);
		txtSerialNumber = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_SERIAL_NUMBER, container);
		txtDateOfAdmission = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_DATE_OF_ADMISSION, container);
		txtModifyBy = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_MODIFY_BY, container);
		txtStatus = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_STATUS, container);
		txtTypeEquip = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_TYPE, container);
		txtMark = GUIComponents.createTextField(EquipmentBounds.TXT_EQUIPMENT_MARK, container);

		txtName.setEditable(false);
		txtModifyBy.setEnabled(false);
		txtDateOfAdmission.setEnabled(false);
		txtStatus.setEnabled(false);
		txtTypeEquip.setEnabled(false);
		txtMark.setEnabled(false);
	}

	public void setupTextPane() {
		textPaneObservations = GUIComponents.createTextPane(EquipmentBounds.EQUIPMENT_TEXT_PANE_OBSERVATIONS, container);
		textPaneComments = GUIComponents.createTextPane(EquipmentBounds.EQUIPMENT_TEXT_PANE_COMMENTS, container);
		textPaneComments.setEditable(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateEquipment = GUIComponents.createButton(Constants.UPDATED_EQUIPMENT, EquipmentBounds.BUTTON_EQUIPMENT_UPDATE,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		btnUpdateEquipment.addActionListener(this);
	}

	
	private void loadEquipmentData() {
		Client client = clientController.getById(idClientUpdate);
		Equipment equipment = equipmentController.getById(idEquipment);

		if (client != null && equipment != null) {
			txtName.setText(client.getClientName());
			txtModel.setText(equipment.getModel());
			txtMark.setText(equipment.getMark());
			txtSerialNumber.setText(equipment.getSerialNumber());
			txtDateOfAdmission.setText(equipment.getAdmissionDay() + "/" + equipment.getAdmissionMonth() + "/" + equipment.getAdmissionYear());
			txtStatus.setText(equipment.getStatus());
			txtTypeEquip.setText(equipment.getEquipmentType());
			txtModifyBy.setText(client.getLastModification());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdateEquipment) {
			updateEquip();
		} 
	}

	
	private void updateEquip() {
		Equipment equipment = createEquipmentFromInputs();
		int validation = validateFields(equipment);

		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}

		if (validation == 0) {
			if (equipmentController.updateEquipment(equipment) == 1) {
				StringUtils.showMessage(Constants.SUCCESSFUL_MODIFICATION);
				setFieldsBackground(Color.green);
				ViewUtils.openPanel(new ClientInformationView(), this);
			}
		}
	}

	private Equipment createEquipmentFromInputs() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentID(idEquipment);
		equipment.setClientID(idClientUpdate);
		equipment.setEquipmentType(txtTypeEquip.getText());
		equipment.setModel(txtModel.getText());
		equipment.setMark(txtMark.getText());
		equipment.setSerialNumber(txtSerialNumber.getText());
		equipment.setStatus(txtStatus.getText());
		equipment.setObservation(textPaneObservations.getText());
		equipment.setTechnicalComments(textPaneComments.getText());
		return equipment;
	}

	private int validateFields(Equipment equipment) {
		int validation = 0;
		/* FieldValidator.validateField(client.getClientEmail(), txtEmail)
				+ FieldValidator.validateNameField(client.getClientName(), txtName)
				+ FieldValidator.validatePhoneField(client.getClientPhone(), txtPhone)
				+ FieldValidator.validateAdressField(client.getClientAddress(), txtAdress);
*/
		return validation;
	}

	private void setFieldsBackground(Color color) {
		txtName.setBackground(color);
	}
}
