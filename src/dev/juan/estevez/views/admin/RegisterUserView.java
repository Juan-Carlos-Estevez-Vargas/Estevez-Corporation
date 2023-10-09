package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.enums.Roles;
import dev.juan.estevez.enums.Users;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.FieldValidator;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.bounds.AdminBounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterUserView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private JTextField txtNameUser;
	private JTextField txtEmailUser;
	private JTextField txtPhoneUser;
	private JTextField txtUsername;
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

	@Override
	public void initializeFrame() {
        setSize(590, 340);
        setTitle("Registrar Usuario - Sesión de " + user);
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
		cmbPermissions = GUIComponents.createComboBox(AdminBounds.CMB_REGISTER_USER_PERMISIONS, Roles.getAllValues(), panelBackUser);
		setupEvents();
    }

	@Override
	public void setupMainPanel() {
		panelBackUser = new JPanel();
		panelBackUser.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panelBackUser.setLayout(null);
		setContentPane(panelBackUser);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, AdminBounds.LABEL_REGISTER_USER_TITLE, panelBackUser);
		GUIComponents.createLabel(Users.NAME.getValue(), AdminBounds.LABEL_REGISTER_USER_NAME, panelBackUser);
		GUIComponents.createLabel(Users.EMAIL.getValue(), AdminBounds.LABEL_REGISTER_USER_EMAIL, panelBackUser);
		GUIComponents.createLabel(Users.PHONE.getValue(), AdminBounds.LABEL_REGISTER_USER_PHONE, panelBackUser);
		GUIComponents.createLabel(Users.PERMISIONS_OF.getValue(), AdminBounds.LABEL_REGISTER_USER_PERMISIONS_OF, panelBackUser);
		GUIComponents.createLabel(Users.USERNAME.getValue(), AdminBounds.LABEL_REGISTER_USER_USERNAME, panelBackUser);
		GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, AdminBounds.LABEL_REGISTER_USER, panelBackUser);
	}

	@Override
	public void setupTextFields() {
		txtNameUser = GUIComponents.createTextField(AdminBounds.TXT_REGISTER_USER_NAME, panelBackUser);
		txtEmailUser = GUIComponents.createTextField(AdminBounds.TXT_REGISTER_USER_EMAIL, panelBackUser);
		txtPhoneUser = GUIComponents.createTextField(AdminBounds.TXT_REGISTER_USER_PHONE, panelBackUser);
		txtUsername = GUIComponents.createTextField(AdminBounds.TXT_REGISTER_USER_USERNAME, panelBackUser);
	}

	@Override
	public void setupButtons() {
		btnRegisterUser = GUIComponents.createButton(Icons.REGISTER_USER_BUTTON_ICON.getValue(), AdminBounds.BUTTON_REGISTER_USER, Colors.BACKGROUND_COLOR.getValue(), panelBackUser);
	}

	@Override
	public void setupEvents() {
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
			StringUtils.showEmptyFieldsMessage();
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
		int validation = FieldValidator.validateEmailField(user.getUserEmail(), txtEmailUser)
				+ FieldValidator.validateNameField(user.getUserName(), txtNameUser)
				+ FieldValidator.validatePhoneField(user.getUserPhone(), txtPhoneUser);
		return validation;
	}
	
	/**
	 * Handles a valid user.
	 *
	 * @param  user  the user object
	 */
	private void handleValidUser(User user) {
		int permissionsCmb = cmbPermissions.getSelectedIndex() + 1;
		String permissionsString = StringUtils.getPermissionsString(permissionsCmb);
		User existingUser = userController.getUserByUsername(user.getUsername());
	
		if (existingUser != null && !existingUser.getUsername().equals(user.getUsername())) {
			txtUsername.setBackground(Color.red);
			StringUtils.showMessage(Constants.USERNAME_NOT_AVAILABLE_MESSAGE);
		} else {
			user.setpermissions(permissionsString);
			user.setPassword(Constants.DEFAULT_PASSWORD);
			user.setRegisterBy(this.user);
			registerUser(user);
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
			StringUtils.showMessage(Constants.REGISTRATION_SUCCESS_MESSAGE);
			dispose();
		};	
	}
	
}
