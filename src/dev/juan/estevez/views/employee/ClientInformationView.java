package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.FieldValidator;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientInformationView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private DefaultTableModel model = new DefaultTableModel();
	private ClientController clientController;
	private EquipmentController equipmentController;
	public static int idEquipment = 0;
	public static int idClient = 0;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtAdress;
	private JTextField txtModifyBy;
	private JTable tableEquipment;
	private JPanel container;
	private JButton btnRegisterEquipment;
	private JButton btnUpdateClient;
	private JButton btnPrint;

	public ClientInformationView() {
		this.user = LoginView.user;
		user_update = ManagementClientsView.user_update;
		idClient = ManagementClientsView.id_cliente_update;
		clientController = new ClientController(new ClientDAO());
		initComponents();
		loadClientData();
		loadEquipmentTable();
		setupEquipmentTableEvent();
	}

	@Override
	public void initializeFrame() {
		setSize(680, 405);
		setTitle("Información del cliente " + user_update + " - Sesión de " + user);
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
		GUIComponents.createLabel("Información del Cliente " + user_update, Bounds.LABEL_CLIENT_INFORMATION_TITLE, container);
		GUIComponents.createLabel(Clients.NAME.getValue(), Bounds.LABEL_CLIENT_INFORMATION_NAME, container);
		GUIComponents.createLabel(Clients.EMAIL.getValue(), Bounds.LABEL_CLIENT_INFORMATION_EMAIL, container);
		GUIComponents.createLabel(Clients.PHONE.getValue(), Bounds.LABEL_CLIENT_INFORMATION_PHONE, container);
		GUIComponents.createLabel(Clients.ADDRESS.getValue(), Bounds.LABEL_CLIENT_INFORMATION_ADDRESS, container);
		GUIComponents.createLabel(Clients.MODIFY_BY.getValue(), Bounds.LABEL_CLIENT_INFORMATION_MODIFY_BY, container);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(Bounds.TXT_CLIENT_INFORMATION_NAME, container);
		txtEmail = GUIComponents.createTextField(Bounds.TXT_CLIENT_INFORMATION_EMAIL, container);
		txtPhone = GUIComponents.createTextField(Bounds.TXT_CLIENT_INFORMATION_PHONE, container);
		txtAdress = GUIComponents.createTextField(Bounds.TXT_CLIENT_INFORMATION_ADDRESS, container);
		txtModifyBy = GUIComponents.createTextField(Bounds.TXT_CLIENT_INFORMATION_MODIFY_BY, container);
		txtModifyBy.setEnabled(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateClient = GUIComponents.createButton(Constants.UPDATED_CLIENT, Bounds.BUTTON_CLIENT_INFORMATION_UPDATE, Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
		btnRegisterEquipment = GUIComponents.createButton(Constants.EQUIPMENT_REGISTER, Bounds.BUTTON_CLIENT_INFORMATION_EQUIPMENT_REGISTER, Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
		btnPrint = GUIComponents.createButton(Constants.CLIENT_PRINT, Bounds.BUTTON_CLIENT_INFORMATION_PRINT, Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		txtPhone.addKeyListener(new ValidateNumbers());
		btnUpdateClient.addActionListener(this);
		btnRegisterEquipment.addActionListener(this);
		btnPrint.addActionListener(this);
	}

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

	/**
	 * Loads the equipment table from the database.
	 */
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
		tableEquipment.addMouseListener(new MouseAdapter() {
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

	/**
	 * Clears the text fields for name, address, email, and phone number.
	 */
	public void clean() {
		this.txtName.setText("");
		this.txtAdress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
	}

	/**
	 * Handles the action performed when an event is triggered.
	 *
	 * @param e the ActionEvent object representing the event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterEquipment) {
			ViewUtils.openPanel(new RegisterEquipment());
		} else if (e.getSource() == btnUpdateClient) {
			updateClient();
		} else if (e.getSource() == btnPrint) {
			handlePrintClients();
		}
	}

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

	/**
	 * Sets the background color of the fields.
	 *
	 * @param color the color to set the background to
	 */
	private void setFieldsBackground(Color color) {
		txtName.setBackground(color);
		txtEmail.setBackground(color);
		txtAdress.setBackground(color);
		txtPhone.setBackground(color);
	}

	private void handlePrintClients() {

    }

}
