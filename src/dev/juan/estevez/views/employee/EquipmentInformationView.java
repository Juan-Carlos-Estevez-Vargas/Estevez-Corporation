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
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.cap.EquipInfoBounds;
import dev.juan.estevez.utils.constants.CapConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.ValidateCharacters;
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
	private JPanel panel;
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
		setTitle(String.format(CapConstants.EQUIPMENT_INFO_PANEL_SESION, this.user));
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
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		panel.setBounds(640, 500, 640, 500);
		setContentPane(panel);
	}

	@Override
	public void setupLabels() {
		JLabel labelTitle = GUIComponents.createLabel(String.format(CapConstants.EQUIPMENT_INFO_TITLE, this.user), EquipInfoBounds.LABEL_TITLE,	panel);
		labelTitle.setFont(Fonts.BUTTON_FONT.getValue());
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

		GUIComponents.createLabel(CapConstants.CLIENT_NAME, EquipInfoBounds.LABEL_NAME, panel);
		GUIComponents.createLabel(Equipments.TYPE.getValue(), EquipInfoBounds.LABEL_TYPE, panel);
		GUIComponents.createLabel(Equipments.MARK.getValue(), EquipInfoBounds.LABEL_MARK, panel);
		GUIComponents.createLabel(Equipments.MODEL.getValue(), EquipInfoBounds.LABEL_MODEL, panel);
		GUIComponents.createLabel(Equipments.SERIAL_NUMBER.getValue(), EquipInfoBounds.LABEL_SERIAL_NUMBER, panel);
		GUIComponents.createLabel(Equipments.LAST_MODIFICATION.getValue(), EquipInfoBounds.LABEL_LAST_MODIFICATION,	panel);
		GUIComponents.createLabel(Equipments.DATE_OF_ADMISSION.getValue(), EquipInfoBounds.LABEL_DATE_OF_ADMISSION,	panel);
		GUIComponents.createLabel(Equipments.STATUS.getValue(), EquipInfoBounds.LABEL_STATUS, panel);
		GUIComponents.createLabel(Equipments.OBSERVATIONS.getValue(), EquipInfoBounds.LABEL_OBSERVATION, panel);
		GUIComponents.createLabel(Equipments.TECHNICAL_COMMENTS.getValue(), EquipInfoBounds.LABEL_COMMENTS, panel);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(EquipInfoBounds.TXT_NAME, panel);
		txtModel = GUIComponents.createTextField(EquipInfoBounds.TXT_MODEL, panel);
		txtSerialNumber = GUIComponents.createTextField(EquipInfoBounds.TXT_SERIAL_NUMBER, panel);
		txtDateOfAdmission = GUIComponents.createTextField(EquipInfoBounds.TXT_DATE_OF_ADMISSION, panel);
		txtModifyBy = GUIComponents.createTextField(EquipInfoBounds.TXT_MODIFY_BY, panel);
		txtStatus = GUIComponents.createTextField(EquipInfoBounds.TXT_STATUS, panel);
		txtTypeEquip = GUIComponents.createTextField(EquipInfoBounds.TXT_TYPE, panel);
		txtMark = GUIComponents.createTextField(EquipInfoBounds.TXT_MARK, panel);

		txtName.setEditable(false);
		txtModifyBy.setEnabled(false);
		txtDateOfAdmission.setEnabled(false);
		txtStatus.setEnabled(false);
		txtTypeEquip.setEnabled(false);
		txtMark.setEnabled(false);
	}

	public void setupTextPane() {
		textPaneObservations = GUIComponents.createTextPane(EquipInfoBounds.TEXT_PANE_OBSERVATIONS, panel);
		textPaneComments = GUIComponents.createTextPane(EquipInfoBounds.TEXT_PANE_COMMENTS, panel);
		textPaneComments.setEditable(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateEquipment = GUIComponents.createButton(CapConstants.UPDATE_EQUIP, EquipInfoBounds.BUTTON_UPDATE,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), panel);
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
			txtName.setText(client.getName());
			txtModel.setText(equipment.getModel());
			txtMark.setText(equipment.getMark());
			txtSerialNumber.setText(equipment.getSerialNumber());
			txtDateOfAdmission.setText(equipment.getAdmissionDate().toString());
			txtStatus.setText(equipment.getStatus());
			txtTypeEquip.setText(equipment.getType());
			txtModifyBy.setText(client.getLastModification());
			textPaneObservations.setText(equipment.getObservations());
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
		equipment.setId(idEquipment);
		equipment.setType(txtTypeEquip.getText());
		equipment.setModel(txtModel.getText());
		equipment.setMark(txtMark.getText());
		equipment.setSerialNumber(txtSerialNumber.getText());
		equipment.setObservations(textPaneObservations.getText());
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
