package dev.juan.estevez.views.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

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
import dev.juan.estevez.utils.bounds.cap.EquipInfoBounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public class EquipmentInformationView extends JFrame implements ActionListener, IGui {

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

	public EquipmentInformationView(int idEquipment) {
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
		JLabel labelTitle = GUIComponents.createLabel("Información del Equipo " + user, EquipInfoBounds.LABEL_TITLE, container);
		labelTitle.setFont(Fonts.BUTTON_FONT.getValue());
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

		GUIComponents.createLabel("Nombre del Cliente", EquipInfoBounds.LABEL_NAME, container);
		GUIComponents.createLabel(Equipments.TYPE.getValue(), EquipInfoBounds.LABEL_TYPE, container);
		GUIComponents.createLabel(Equipments.MARK.getValue(), EquipInfoBounds.LABEL_MARK, container);
		GUIComponents.createLabel(Equipments.MODEL.getValue(), EquipInfoBounds.LABEL_MODEL, container);
		GUIComponents.createLabel(Equipments.SERIAL_NUMBER.getValue(), EquipInfoBounds.LABEL_SERIAL_NUMBER, container);
		GUIComponents.createLabel(Equipments.LAST_MODIFICATION.getValue(), EquipInfoBounds.LABEL_LAST_MODIFICATION, container);
		GUIComponents.createLabel(Equipments.DATE_OF_ADMISSION.getValue(), EquipInfoBounds.LABEL_DATE_OF_ADMISSION,	container);
		GUIComponents.createLabel(Equipments.STATUS.getValue(), EquipInfoBounds.LABEL_STATUS, container);
		GUIComponents.createLabel(Equipments.OBSERVATION.getValue(), EquipInfoBounds.LABEL_OBSERVATION, container);
		GUIComponents.createLabel(Equipments.TECHNICAL_COMMENTS.getValue(), EquipInfoBounds.LABEL_COMMENTS, container);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(EquipInfoBounds.TXT_NAME, container);
		txtModel = GUIComponents.createTextField(EquipInfoBounds.TXT_MODEL, container);
		txtSerialNumber = GUIComponents.createTextField(EquipInfoBounds.TXT_SERIAL_NUMBER, container);
		txtDateOfAdmission = GUIComponents.createTextField(EquipInfoBounds.TXT_DATE_OF_ADMISSION, container);
		txtModifyBy = GUIComponents.createTextField(EquipInfoBounds.TXT_MODIFY_BY, container);
		txtStatus = GUIComponents.createTextField(EquipInfoBounds.TXT_STATUS, container);
		txtTypeEquip = GUIComponents.createTextField(EquipInfoBounds.TXT_TYPE, container);
		txtMark = GUIComponents.createTextField(EquipInfoBounds.TXT_MARK, container);

		txtName.setEditable(false);
		txtModifyBy.setEnabled(false);
		txtDateOfAdmission.setEnabled(false);
		txtStatus.setEnabled(false);
		txtTypeEquip.setEnabled(false);
		txtMark.setEnabled(false);
	}

	public void setupTextPane() {
		textPaneObservations = GUIComponents.createTextPane(EquipInfoBounds.TEXT_PANE_OBSERVATIONS, container);
		textPaneComments = GUIComponents.createTextPane(EquipInfoBounds.TEXT_PANE_COMMENTS, container);
		textPaneComments.setEditable(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateEquipment = GUIComponents.createButton(Constants.UPDATED_EQUIPMENT, EquipInfoBounds.BUTTON_UPDATE,
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
			txtDateOfAdmission.setText(equipment.getAdmissionDay() + "/" + equipment.getAdmissionMonth() + "/"
					+ equipment.getAdmissionYear());
			txtStatus.setText(equipment.getStatus());
			txtTypeEquip.setText(equipment.getEquipmentType());
			txtModifyBy.setText(client.getLastModification());
			textPaneObservations.setText(equipment.getObservation());
			textPaneComments.setText(equipment.getTechnicalComments());
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
				ViewUtils.openPanel(new ClientInformationView(), this);
			}
		}
	}

	private Equipment createEquipmentFromInputs() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentID(idEquipment);
		equipment.setEquipmentType(txtTypeEquip.getText());
		equipment.setModel(txtModel.getText());
		equipment.setMark(txtMark.getText());
		equipment.setSerialNumber(txtSerialNumber.getText());
		equipment.setObservation(textPaneObservations.getText());
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
