package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.bounds.cap.RegisterClientBounds;
import dev.juan.estevez.utils.constants.CapConstants;
import dev.juan.estevez.utils.constants.ValidFieldsConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.FieldValidator;
import dev.juan.estevez.utils.validators.ValidateCharacters;
import dev.juan.estevez.utils.validators.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterClientView extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private ClientService clientController;
	private JTextField txtNameClient;
	private JTextField txtEmailClient;
	private JTextField txtPhoneClient;
	private JTextField txtAdressClient;
	private JPanel panel;
	private JButton btnRegisterClient;
	private String user;

	public RegisterClientView() {
		this.user = LoginView.user;
		initializeFrame();
		clientController = new ClientService(new ClientDAO());
		initComponents();
	}

	@Override
	public void initializeFrame() {
		setSize(590, 340);
		setTitle(String.format(CapConstants.REGISTER_CLIENT_SESION, this.user));
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
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		setContentPane(this.panel);
	}

	@Override
	public void setupLabels() {
		JLabel labelTitle = GUIComponents.createLabel(CapConstants.CLIENT_REGISTER, RegisterClientBounds.LABEL_TITLE, panel);
		labelTitle.setFont(Fonts.BUTTON_FONT.getValue());
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

		GUIComponents.createLabel(Clients.NAME.getValue(), RegisterClientBounds.LABEL_NAME, panel);
		GUIComponents.createLabel(Clients.EMAIL.getValue(), RegisterClientBounds.LABEL_EMAIL, panel);
		GUIComponents.createLabel(Clients.PHONE.getValue(), RegisterClientBounds.LABEL_PHONE, panel);
		GUIComponents.createLabel(Clients.ADDRESS.getValue(), RegisterClientBounds.LABEL_ADDRESS, panel);
		GUIComponents.createLabel(CapConstants.CLIENT_REGISTER, RegisterClientBounds.LABEL_REGISTER, panel);
	}

	@Override
	public void setupTextFields() {
		txtNameClient = GUIComponents.createTextField(RegisterClientBounds.TXT_NAME, panel);
		txtEmailClient = GUIComponents.createTextField(RegisterClientBounds.TXT_EMAIL, panel);
		txtPhoneClient = GUIComponents.createTextField(RegisterClientBounds.TXT_PHONE, panel);
		txtAdressClient = GUIComponents.createTextField(RegisterClientBounds.TXT_ADDRESS, panel);
	}

	@Override
	public void setupButtons() {
		btnRegisterClient = GUIComponents.createButton(Icons.REGISTER_CLIENT.getValue(),
				RegisterClientBounds.BUTTON_REGISTER, Colors.BACKGROUND_COLOR.getValue(), panel);
	}

	@Override
	public void setupEvents() {
		txtNameClient.addKeyListener(new ValidateCharacters());
		txtPhoneClient.addKeyListener(new ValidateNumbers());
		btnRegisterClient.addActionListener(this);
	}

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
	 * @return a new Client object with the client name, email, phone, and address
	 *         set based on the input values
	 */
	private Client createClientFromInputs() {
		Client client = new Client();
		client.setName(txtNameClient.getText().trim());
		client.setEmail(txtEmailClient.getText().trim());
		client.setPhone(txtPhoneClient.getText().trim());
		client.setAddress(txtAdressClient.getText().trim());
		return client;
	}

	/**
	 * Validates the fields of a client object and returns the total number of
	 * validation errors.
	 *
	 * @param client the client object to validate
	 * @return the total number of validation errors
	 */
	private int validateClientFields(Client client) {
		int validation = FieldValidator.validateEmailField(client.getEmail(), txtEmailClient)
				+ FieldValidator.validateNameField(client.getName(), txtNameClient)
				+ FieldValidator.validatePhoneField(client.getPhone(), txtPhoneClient)
				+ FieldValidator.validateAdressField(client.getAddress(), txtAdressClient);
		return validation;
	}

	/**
	 * Handles a valid client.
	 *
	 * @param client the client object to handle
	 */
	private void handleValidClient(Client client) {
		Client existingClient = clientController.getByEmail(client.getEmail());

		if (existingClient != null && !existingClient.getEmail().equals(client.getEmail())) {
			txtEmailClient.setBackground(Color.red);
			StringUtils.showMessage(ValidFieldsConstants.EMAIL_NOT_AVAILABLE);
		} else {
			client.setLastModification(user);
			registerClient(client);
		}
	}

	private void registerClient(Client client) {
		if (clientController.createClient(client) == 1) {
			clean();
			txtEmailClient.setBackground(Color.green);
			txtNameClient.setBackground(Color.green);
			txtPhoneClient.setBackground(Color.green);
			txtAdressClient.setBackground(Color.green);
			StringUtils.showMessage(Constants.SUCCESSFUL_REGISTRATION);
			dispose();
		}
	}

}
