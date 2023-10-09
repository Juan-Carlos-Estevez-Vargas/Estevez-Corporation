package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.controllers.ClientController;
import dev.juan.estevez.controllers.EquipmentController;
import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Equipments;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.persistence.EquipmentDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.FieldValidator;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public class EquipmentInformation extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private String nameClient = "", user = "";
	private DefaultTableModel model = new DefaultTableModel();
	private EquipmentController equipmentController;
	public static int idEquipment = 0;
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
	private JScrollPane scrollObservations;
	private JScrollPane scrollComments;
	private JPanel container;
	private JButton btnUpdateEquipment;

	public EquipmentInformation() {
		this.user = LoginView.user;
		idClientUpdate = ManagementClientsView.id_cliente_update;
		equipmentController = new EquipmentController(new EquipmentDAO());
		equipmentController = new EquipmentController(new EquipmentDAO());
		initComponents();
		//loadEquipmentData();
		//loadEquipmentTable();
		//setupEquipmentTableEvent();
	}

	@Override
	public void initializeFrame() {
		setSize(680, 405);
		setTitle("Equipo del cliente " + user + " - Sesión de " + user);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		container.setBounds(630, 460, 630, 460);
		setContentPane(container);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel("Información del Equipo " + user, Bounds.LABEL_EQUIPMENT_INFORMATION_TITLE,
				container);
		GUIComponents.createLabel("Nombre del Cliente", Bounds.LABEL_EQUIPMENT_CLIENT_NAME, container);
		GUIComponents.createLabel(Equipments.TYPE.getValue(), Bounds.LABEL_EQUIPMENT_TYPE, container);
		GUIComponents.createLabel(Equipments.MARK.getValue(), Bounds.LABEL_EQUIPMENT_MARK, container);
		GUIComponents.createLabel(Equipments.MODEL.getValue(), Bounds.LABEL_EQUIPMENT_MODEL, container);
		GUIComponents.createLabel(Equipments.SERIAL_NUMBER.getValue(), Bounds.LABEL_EQUIPMENT_SERIAL_NUMBER, container);
		GUIComponents.createLabel(Equipments.LAST_MODIFICATION.getValue(), Bounds.LABEL_EQUIPMENT_LAST_MODIFICATION,
				container);
		GUIComponents.createLabel(Equipments.DATE_OF_ADMISSION.getValue(), Bounds.LABEL_EQUIPMENT_DATE_OF_ADMISSION,
				container);
		GUIComponents.createLabel(Equipments.STATUS.getValue(), Bounds.LABEL_EQUIPMENT_STATUS, container);
		GUIComponents.createLabel(Equipments.OBSERVATION.getValue(), Bounds.LABEL_EQUIPMENT_OBSERVATION, container);
		GUIComponents.createLabel(Equipments.TECHNICAL_COMMENTS.getValue(), Bounds.LABEL_EQUIPMENT_COMMENTS, container);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_NAME, container);
		txtModel = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_MODEL, container);
		txtSerialNumber = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_SERIAL_NUMBER, container);
		txtDateOfAdmission = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_DATE_OF_ADMISSION, container);
		txtModifyBy = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_MODIFY_BY, container);
		txtStatus = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_STATUS, container);
		txtTypeEquip = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_TYPE, container);
		txtMark = GUIComponents.createTextField(Bounds.TXT_EQUIPMENT_MARK, container);

		txtName.setEditable(false);
		txtModifyBy.setEnabled(false);
		txtDateOfAdmission.setEnabled(false);
		txtStatus.setEnabled(false);
		txtTypeEquip.setEnabled(false);
		txtMark.setEnabled(false);
	}

	public void setupTextPane() {
		textPaneObservations = GUIComponents.createTextPane(Bounds.EQUIPMENT_TEXT_PANE_OBSERVATIONS, container);
		textPaneComments = GUIComponents.createTextPane(Bounds.EQUIPMENT_TEXT_PANE_COMMENTS, container);
		textPaneComments.setEditable(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateEquipment = GUIComponents.createButton(Constants.UPDATED_EQUIPMENT, Bounds.BUTTON_EQUIPMENT_UPDATE,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		btnUpdateEquipment.addActionListener(this);
	}

	/*
	private void loadClientData() {
		Client client = clientController.getClientById(idClient);

		if (client != null) {
			txtName.setText(client.getClientName());
			txtEmail.setText(client.getClientEmail());
			txtPhone.setText(client.getClientPhone());
			txtAdress.setText(client.getClientAddress());
			txtModifyBy.setText(client.getLastModification());
		}
	}

	private void loadEquipmentTable() {
		Equipment equipment = equipmentController.getEquipmentByClientId(idClient);
		if (equipment != null) {
			createTableAndScrollPane();
			addColumnsToTable();
			try {
				fillTableWithData(equipment);
			} catch (SQLException e) {
				StringUtils.handleQueryError(e, Constants.ERROR_SHOWING_INFORMATION);
			}
		}
	}

	private void createTableAndScrollPane() {
		tableEquipment = GUIComponents.createTable(model, container);
		GUIComponents.createScrollPanel(tableEquipment, Bounds.SCROLL_MANAGE, container);
	}

	private void addColumnsToTable() {
		model.addColumn(Equipments.ID.getValue());
		model.addColumn(Equipments.TYPE.getValue());
		model.addColumn(Equipments.MARK.getValue());
		model.addColumn(Equipments.STATUS.getValue());
	}

	private void fillTableWithData(Equipment equipment) throws SQLException {
		if (equipment != null) {
			model.addRow(new Object[] {
					equipment.getEquipmentID(),
					equipment.getEquipmentType(),
					equipment.getMark(),
					equipment.getStatus()
			});
		}
	}

	private void setupEquipmentTableEvent() {
		this.tableEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableEquipment.rowAtPoint(e.getPoint());
				int column = 0;

				if (row > -1) {
					idEquipment = (int) model.getValueAt(row, column);
					ViewUtils.openPanel(new EquipmentInformation(), ClientInformationView.this);
				}
			}
		});
	}

	public void clean() {
		this.txtName.setText("");
		this.txtAdress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
	}
*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource() == btnRegisterEquipment) {
			ViewUtils.openPanel(new RegisterEquipment());
		} else if (e.getSource() == btnUpdateClient) {
			updateClient();
		} else if (e.getSource() == btnPrint) {
			handlePrintClients();
		}*/
	}

	/*
	private void updateClient() {
		Client client = createClientFromInputs();
		int validation = validateClientFields(client);

		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}

		if (validation == 0) {
			if (clientController.updateClient(client) == 1) {
				StringUtils.showMessage(Constants.SUCCESSFUL_MODIFICATION);
				clean();
				setFieldsBackground(Color.green);
				ViewUtils.openPanel(new ManagementClientsView(), this);
			}
		}
	}

	private Client createClientFromInputs() {
		Client client = new Client();
		client.setClientID(idClient);
		client.setClientName(txtName.getText().trim());
		client.setClientEmail(txtEmail.getText().trim());
		client.setClientPhone(txtPhone.getText().trim());
		client.setClientAddress(txtAdress.getText().trim());
		client.setLastModification(user);
		return client;
	}

	private int validateClientFields(Client client) {
		int validation = FieldValidator.validateEmailField(client.getClientEmail(), txtEmail)
				+ FieldValidator.validateNameField(client.getClientName(), txtName)
				+ FieldValidator.validatePhoneField(client.getClientPhone(), txtPhone)
				+ FieldValidator.validateAdressField(client.getClientAddress(), txtAdress);

		return validation;
	}

	private void setFieldsBackground(Color color) {
		txtName.setBackground(color);
		txtEmail.setBackground(color);
		txtAdress.setBackground(color);
		txtPhone.setBackground(color);
	}*/
}
