package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.juan.estevez.controllers.ClientController;
import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.FieldValidator;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterClientView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private ClientController clientController;
	private JTextField txtNameClient;
	private JTextField txtEmailClient;
	private JTextField txtPhoneClient;
	private JTextField txtAdressClient;
	private JPanel panelBackClient;
	private JButton btnRegisterClient;
	private String user;

	public RegisterClientView() {
		this.user = LoginView.user;
		initializeFrame();
		clientController = new ClientController(new ClientDAO());
		initComponents();
	}

	@Override
    public void initializeFrame() {
        setSize(590, 340);
        setTitle("Registrar nuevo cliente - Sesión de " + user);
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
        panelBackClient = new JPanel();
		panelBackClient.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panelBackClient.setLayout(null);
		setContentPane(this.panelBackClient);
    }

	@Override
    public void setupLabels() {
        GUIComponents.createLabel(Constants.CLIENT_REGISTER, Bounds.LABEL_ADMIN_TITLE, panelBackClient);
		GUIComponents.createLabel(Clients.NAME.getValue(), Bounds.LABEL_REGISTER_USER_NAME, panelBackClient).setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Clients.EMAIL.getValue(), Bounds.LABEL_REGISTER_USER_EMAIL, panelBackClient).setFont(Fonts.PANEL_LABEL_FONT.getValue());
    	GUIComponents.createLabel(Clients.PHONE.getValue(), Bounds.LABEL_REGISTER_USER_PHONE, panelBackClient).setFont(Fonts.PANEL_LABEL_FONT.getValue());
		GUIComponents.createLabel(Clients.ADDRESS.getValue(), Bounds.LABEL_REGISTER_USER_PERMISIONS_OF, panelBackClient).setFont(Fonts.PANEL_LABEL_FONT.getValue());
		GUIComponents.createLabel(Constants.CLIENT_REGISTER, Bounds.LABEL_REGISTER_USER, panelBackClient).setFont(Fonts.PANEL_LABEL_FONT.getValue());
    }

	@Override
	public void setupTextFields() {
		txtNameClient = GUIComponents.createTextField(Bounds.TXT_REGISTER_USER_NAME, panelBackClient);
		txtEmailClient = GUIComponents.createTextField(Bounds.TXT_REGISTER_USER_EMAIL, panelBackClient);
		txtPhoneClient = GUIComponents.createTextField(Bounds.TXT_REGISTER_USER_PHONE, panelBackClient);
		txtAdressClient = GUIComponents.createTextField(Bounds.TXT_REGISTER_USER_USERNAME, panelBackClient);
	}

	@Override
	public void setupButtons() {
		btnRegisterClient = GUIComponents.createButton(Icons.REGISTER_CLIENT_ICON.getValue(), Bounds.BUTTON_REGISTER_USER, Colors.BACKGROUND_COLOR.getValue(), panelBackClient);
	}

	@Override
	public void setupEvents() {
		txtNameClient.addKeyListener(new ValidateCharacters());
		txtPhoneClient.addKeyListener(new ValidateNumbers());
		btnRegisterClient.addActionListener(this);
	}

	/**
	 * Cleans the text fields for email, name, address, and phone number of the
	 * client.
	 */
	public void clean() {
		this.txtEmailClient.setText("");
		this.txtNameClient.setText("");
		this.txtAdressClient.setText("");
		this.txtPhoneClient.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterClient) {
			validateAndRegisterClient();
		}
	}

	/**
	 * Validates and registers a client.
	 */
	private void validateAndRegisterClient() {
		Client client = createClientFromInputs();
		int validation = validateClientFields(client);
	
		if (validation == 0) {
			handleValidClient(client);
		} else if (validation == 4) {
			StringUtils.showEmptyFieldsMessage();
		}
	}
	
	/**
	 * Creates a new Client object based on the inputs provided.
	 *
	 * @return  a new Client object with the client name, email, phone, and address set based on the input values
	 */
	private Client createClientFromInputs() {
		Client client = new Client();
		client.setClientName(txtNameClient.getText().trim());
		client.setClientEmail(txtEmailClient.getText().trim());
		client.setClientPhone(txtPhoneClient.getText().trim());
		client.setClientAddress(txtAdressClient.getText().trim());
		return client;
	}

	/**
	 * Validates the fields of a client object and returns the total number of validation errors.
	 *
	 * @param  client  the client object to validate
	 * @return         the total number of validation errors
	 */
	private int validateClientFields(Client client) {
		int validation = FieldValidator.validateEmailField(client.getClientEmail(), txtEmailClient)
				+ FieldValidator.validateNameField(client.getClientName(), txtNameClient)
				+ FieldValidator.validatePhoneField(client.getClientPhone(), txtPhoneClient)
				+ FieldValidator.validateAdressField(client.getClientAddress(), txtAdressClient);
		return validation;
	}
	
	/**
	 * Handles a valid client.
	 *
	 * @param  client  the client object to handle
	 */
	private void handleValidClient(Client client) {
		Client existingClient = clientController.getClientByEmail(client.getClientEmail());
	
		if (existingClient != null && !existingClient.getClientEmail().equals(client.getClientEmail())) {
			txtEmailClient.setBackground(Color.red);
			StringUtils.showMessage(Constants.EMAIL_NOT_AVAILABLE_MESSAGE);
		} else {
			client.setLastModification(user);
			registerClient(client);
		}
	}

	/**
	 * Registers a client by calling the registerClient method of the clientController.
	 * If the registration is successful, it performs some UI updates, shows a success message,
	 * and closes the current window.
	 *
	 * @param  client the client object to be registered
	 */
	private void registerClient(Client client) {
		if (clientController.registerClient(client) == 1) {
			clean();
			txtEmailClient.setBackground(Color.green);
			txtNameClient.setBackground(Color.green);
			txtPhoneClient.setBackground(Color.green);
			txtAdressClient.setBackground(Color.green);
			StringUtils.showMessage(Constants.REGISTRATION_SUCCESS_MESSAGE);
			dispose();
		};	
	}

}
