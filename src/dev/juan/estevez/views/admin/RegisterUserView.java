package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.Utils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterUserView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtNameUser;
	private JTextField txtEmailUser;
	private JTextField txtPhoneUser;
	private JTextField txtUsername;
	private JLabel labelTitle;
	private JLabel labelUsername;
	private JLabel labelNameUser;
	private JLabel labelEmailUser;
	private JLabel labelPhoneUser;
	private JLabel labelRegisterUser;
	private JLabel labelPermissionsOf;
	private JPanel panelBackUser;
	private JButton btnRegisterUser;
	private JComboBox<String> cmbPermissions;
	private String user;
	private UserController userController;

	public RegisterUserView() {
		this.user = LoginView.user;
		userController = new UserController(new UserDAO());
		initializeFrame();
		initComponents();
	}

	/**
	 * Initializes the frame.
	 */
	private void initializeFrame() {
        setSize(590, 340);
        setTitle("Registrar Usuario - Sesión de " + user);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

	/**
	 * Initializes the components of the GUI.
	 */
	private void initComponents() {
		panelBackUser = new JPanel();
		panelBackUser.setBackground(Constants.BACKGROUND_COLOR);
		panelBackUser.setLayout(null);
		setContentPane(panelBackUser);

        labelTitle = GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.REGISTER_USER_TITLE_BOUNDS, Constants.LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelNameUser = GUIComponents.createLabel(Constants.USER_NAME, Bounds.REGISTER_USER_NAME_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelEmailUser = GUIComponents.createLabel(Constants.EMAIL, Bounds.REGISTER_USER_EMAIL_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelPhoneUser = GUIComponents.createLabel(Constants.PHONE, Bounds.REGISTER_USER_PHONE_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelPermissionsOf = GUIComponents.createLabel(Constants.PERMISIONS_OF, Bounds.REGISTER_USER_PERMISIONS_OF_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelUsername = GUIComponents.createLabel(Constants.USERNAME, Bounds.REGISTER_USERNAME_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);
		labelRegisterUser = GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.REGISTER_USER_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBackUser);

		txtNameUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_NAME_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, panelBackUser);
		txtEmailUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_EMAIL_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, panelBackUser);
		txtPhoneUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_PHONE_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, panelBackUser);
		txtUsername = GUIComponents.createTextField(Bounds.TEXT_FIELD_USERNAME_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, panelBackUser);
        
		btnRegisterUser = GUIComponents.createButton(Constants.REGISTER_USER_BUTTON_ICON, Bounds.REGISTER_USER_BUTTON_BOUNDS, Constants.BACKGROUND_COLOR, panelBackUser);
		setupPermissionsComboBox();

		txtNameUser.addKeyListener(new ValidateCharacters());
		txtPhoneUser.addKeyListener(new ValidateNumbers());
        btnRegisterUser.addActionListener(this);
    }

	/**
	 * Clears all the input fields and resets the permissions dropdown to the
	 * default value.
	 */
	public void clean() {
		txtEmailUser.setText("");
		txtNameUser.setText("");
		txtPhoneUser.setText("");
		txtUsername.setText("");
		cmbPermissions.setSelectedIndex(0);
	}

	/**
	 * Sets up the permissions combo box.
	 */
	private void setupPermissionsComboBox() {
		cmbPermissions = new JComboBox<>();
		cmbPermissions.addItem(Constants.ROLE_ADMIN);
		cmbPermissions.addItem(Constants.ROLE_TECH);
		cmbPermissions.addItem(Constants.ROLE_CAPTURISTA);
		cmbPermissions.setBounds(20, 250, 170, 25);
		cmbPermissions.setBackground(Constants.TEXT_FIELD_COLOR);
		cmbPermissions.setFont(Constants.PANEL_LABEL_FONT);
		cmbPermissions.setForeground(Color.WHITE);
		panelBackUser.add(cmbPermissions);
	}

	/**
	 * Performs an action when an event is triggered.
	 *
	 * @param e the event that triggered the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterUser) {
			validateAndRegisterUser();
		}
	}

	/**
	 * Validates and registers a user.
	 */
	private void validateAndRegisterUser() {
		User user = createUserFromInputs();
		int validation = validateUserFields(user);
	
		if (validation == 0) {
			handleValidUser(user);
		} else if (validation == 4) {
			Utils.showEmptyFieldsMessage();
		}
	}
	
	/**
	 * Creates a new User object based on the inputs provided.
	 *
	 * @return  a User object populated with the values from the input fields
	 */
	private User createUserFromInputs() {
		User user = new User();
		user.setUserEmail(txtEmailUser.getText().trim());
		user.setUserPhone(txtPhoneUser.getText().trim());
		user.setUsername(txtUsername.getText().trim());
		user.setUserName(txtNameUser.getText().trim());
		return user;
	}
	
	/**
	 * Validates the user fields and returns the total number of validation errors.
	 *
	 * @param  user  the user object containing the user's information
	 * @return       the total number of validation errors
	 */
	private int validateUserFields(User user) {
		int validation = Utils.validateEmailField(user.getUserEmail(), txtEmailUser)
				+ Utils.validateNameField(user.getUserName(), txtNameUser)
				+ Utils.validatePhoneField(user.getUserPhone(), txtPhoneUser);
		return validation;
	}
	
	/**
	 * Handles a valid user.
	 *
	 * @param  user  the user object
	 */
	private void handleValidUser(User user) {
		int permissionsCmb = cmbPermissions.getSelectedIndex() + 1;
		String permissionsString = getPermissionsString(permissionsCmb);
		String username = userController.getUsernameByUsername(user.getUsername());
	
		if (username != null && !username.isEmpty()) {
			txtUsername.setBackground(Color.red);
			Utils.showMessage(Constants.USERNAME_NOT_AVAILABLE_MESSAGE);
		} else {
			user.setpermissions(permissionsString);
			user.setPassword(Constants.DEFAULT_PASSWORD);
			user.setRegisterBy(this.user);
			registerUser(user);
		}
	}
	
	/**
	 * Returns the corresponding permissions string based on the given permissionsCmb value.
	 *
	 * @param  permissionsCmb   the permissions combination value
	 * @return                  the corresponding permissions string
	 */
	private String getPermissionsString(int permissionsCmb) {
		switch (permissionsCmb) {
			case 1:
				return Constants.ROLE_ADMIN;
			case 3:
				return Constants.ROLE_CAPTURISTA;
			case 2:
				return Constants.ROLE_TECH;
			default:
				return "";
		}
	}	

	/**
	 * Registers a user.
	 *
	 * @param  user  the user to be registered
	 */
	private void registerUser(User user) {
		if (userController.registerUser(user) == 1) {
			clean();
			txtEmailUser.setBackground(Color.green);
			txtNameUser.setBackground(Color.green);
			txtPhoneUser.setBackground(Color.green);
			txtUsername.setBackground(Color.green);
			Utils.showMessage(Constants.REGISTRATION_SUCCESS_MESSAGE);
			dispose();
		};	
	}
}
