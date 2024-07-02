package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.enums.States;
import dev.juan.estevez.enums.Users;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Role;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.RoleDAO;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.services.impl.RoleService;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.bounds.admin.RegisterUserBounds;
import dev.juan.estevez.utils.constants.AdminConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.FieldValidator;
import dev.juan.estevez.utils.validators.ValidateCharacters;
import dev.juan.estevez.utils.validators.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterUserView extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtEmail, txtPhone, txtUsername;
	private JPanel panel;
	private JButton btnRegister;
	private JComboBox<String> cmbRoles;
	private String user;
	private RoleService roleService;
	private UserService userService;
	private List<Role> roles;

	public RegisterUserView() {
		this.user = LoginView.user;
		userService = new UserService(new UserDAO());
		roleService = new RoleService(new RoleDAO());
		roles = roleService.getAll();
		initializeFrame();
		initComponents();
	}

	@Override
	public void initializeFrame() {
		setSize(590, 340);
		setTitle(String.format(AdminConstants.REGISTER_USER_SESION, user));
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
		cmbRoles = GUIComponents.createComboBox(RegisterUserBounds.CMB_PERMISIONS, getRoleNames(), panel);
		setupEvents();
	}

	@Override
	public void setupMainPanel() {
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		setContentPane(panel);
	}

	@Override
	public void setupLabels() {
		GUIComponents
			.createLabel(AdminConstants.USER_REGISTER, RegisterUserBounds.LABEL_TITLE, panel)
			.setFont(Fonts.BUTTON_FONT.getValue());

		GUIComponents.createLabel(Users.NAME.getValue(), RegisterUserBounds.LABEL_NAME, panel);
		GUIComponents.createLabel(Users.EMAIL.getValue(), RegisterUserBounds.LABEL_EMAIL, panel);
		GUIComponents.createLabel(Users.PHONE.getValue(), RegisterUserBounds.LABEL_PHONE, panel);
		GUIComponents.createLabel(Users.PERMISIONS_OF.getValue(), RegisterUserBounds.LABEL_PERMISIONS_OF, panel);
		GUIComponents.createLabel(Users.USERNAME.getValue(), RegisterUserBounds.LABEL_USERNAME, panel);
		GUIComponents.createLabel(AdminConstants.USER_REGISTER, RegisterUserBounds.LABEL_REGISTER_USER, panel);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(RegisterUserBounds.TXT_NAME, panel);
		txtEmail = GUIComponents.createTextField(RegisterUserBounds.TXT_EMAIL, panel);
		txtPhone = GUIComponents.createTextField(RegisterUserBounds.TXT_PHONE, panel);
		txtUsername = GUIComponents.createTextField(RegisterUserBounds.TXT_USERNAME, panel);
	}

	@Override
	public void setupButtons() {
		btnRegister = GUIComponents
			.createButton(Icons.REGISTER_USER_BUTTON.getValue(), 
				RegisterUserBounds.BUTTON_REGISTER_USER, Colors.BACKGROUND_COLOR.getValue(), panel);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		txtPhone.addKeyListener(new ValidateNumbers());
		btnRegister.addActionListener(this);
	}

	public void clean() {
		txtEmail.setText("");
		txtName.setText("");
		txtPhone.setText("");
		txtUsername.setText("");
		cmbRoles.setSelectedIndex(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegister) {
			validateAndRegisterUser();
		}
	}

	/**
	 * Validates and registers a user.
	 */
	private void validateAndRegisterUser() {
		User user = createUserFromInputs();
		int validation = validateUserFields(user);

		if (validation == 0 && user != null) {
			handleValidUser(user);
		} else if (validation == 4) {
			StringUtils.showEmptyFieldsMessage();
		}
	}

	/**
	 * Creates a new User object based on the inputs provided.
	 *
	 * @return a User object populated with the values from the input fields
	 */
	private User createUserFromInputs() {
		return User.builder()
			.email(txtEmail.getText().trim())
			.phone(txtPhone.getText().trim())
			.username(txtUsername.getText().trim())
			.name(txtName.getText().trim())
			.build();
	}

	/**
	 * Validates the user fields and returns the total number of validation errors.
	 *
	 * @param user the user object containing the user's information
	 * @return the total number of validation errors
	 */
	private int validateUserFields(User user) {
		int validation = FieldValidator.validateEmailField(user.getEmail(), txtEmail)
				+ FieldValidator.validateNameField(user.getName(), txtName)
				+ FieldValidator.validatePhoneField(user.getPhone(), txtPhone)
				+ FieldValidator.validateUsernameField(user.getUsername(), txtUsername);
		return validation;
	}

	/**
	 * Handles a valid user.
	 *
	 * @param user the user object
	 */
	private void handleValidUser(User user) {
		Role role = roles.get(cmbRoles.getSelectedIndex());
		User existingUser = userService.getByUsername(user.getUsername());

		if (existingUser != null && !existingUser.getUsername().equals(user.getUsername())) {
			txtUsername.setBackground(Color.red);
			StringUtils.showMessage(AdminConstants.USERNAME_NOT_AVAILABLE);
		} else {
			user.setStatus(States.ACTIVE.getValue());
			user.setPassword(Constants.DEFAULT_PASSWORD);
			user.setRegisterBy(this.user);
			registerUser(user, role);
		}
	}

	/**
	 * Registers a user.
	 *
	 * @param user the user to be registered
	 */
	private void registerUser(User user, Role role) {
		if (userService.createUser(user, role) == 1) {
			clean();
			txtEmail.setBackground(Color.green);
			txtName.setBackground(Color.green);
			txtPhone.setBackground(Color.green);
			txtUsername.setBackground(Color.green);
			StringUtils.showMessage(AdminConstants.REGISTRATION_SUCCESS);
			dispose();
		}
	}

	private List<String> getRoleNames() {
		List<String> roleNames = new ArrayList<>();
		roles.forEach(role -> roleNames.add(role.getRoleName()));
		return roleNames;
	}

}
