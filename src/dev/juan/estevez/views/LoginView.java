package dev.juan.estevez.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.enums.Colors;
import dev.juan.estevez.utils.enums.Fonts;
import dev.juan.estevez.utils.enums.Icons;
import dev.juan.estevez.utils.enums.Roles;
import dev.juan.estevez.utils.enums.States;
import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.User;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.admin.AdministratorPanelView;
import dev.juan.estevez.views.employee.EmployeePanel;
import dev.juan.estevez.views.technical.PanelTechnical;
import panel.utilities.ForgotPassword;

/**
 * @author Juan Carlos Estevez Vargas
 */
public final class LoginView extends JFrame implements ActionListener, GUIInterface {

    private static final long serialVersionUID = 1L;
    private JPanel container;
    private JLabel jlError;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnEye, btnForgot;
    private boolean eyeEstate;
    public static String user = "";
    private JTextField txtPassword2;
    private final UserController loginController;

    public LoginView(UserController loginController) {
        this.loginController = loginController;
        initializeFrame();
        initComponents();
    }

    @Override
    public void initializeFrame() {
        setSize(350, 600);
        setTitle("Estevez Corporation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void initComponents() {
        setupMainPanel();
        setupLabels();
        setupTextFields();
        setupButtons();
        GUIComponents.createSeparator(Bounds.SEPARATOR_BOUNDS, container);
        setupEvents();
    }

    @Override
    public void setupMainPanel() {
        container = new JPanel();
        container.setBackground(Colors.BACKGROUND_COLOR.getValue());
        container.setLayout(null);
        setContentPane(container);
    }

    @Override
    public void setupLabels() {
        GUIComponents.createImageLabel(Constants.LOGIN_LOGO_URL, Bounds.LOGO_LABEL_BOUNDS, container);
        GUIComponents.createLabel(Constants.LOGIN_USER_TEXT, Bounds.USER_LABEL_BOUNDS, container);
        GUIComponents.createLabel("", Bounds.ERROR_LABEL_BOUNDS, container).setFont(Fonts.ERROR_FONT.getValue());
        GUIComponents.createLabel(Constants.LOGIN_PASSWORD_TEXT, Bounds.PASSWORD_LABEL_BOUNDS, container);
    }

    @Override
    public void setupTextFields() {
        txtUser = GUIComponents.createTextField(Bounds.USER_TEXT_BOUNDS, container);
        txtPassword = GUIComponents.createPasswordField(Bounds.PASSWORD_FIELD_BOUNDS, container);
        txtPassword2 = GUIComponents.createTextField(Bounds.PASSWORD_TEXT_BOUNDS, container);
        txtPassword2.setVisible(false);
    }

    @Override
    public void setupButtons() {
        btnEye = GUIComponents.createButton(Icons.EYE_ICON.getValue(), Bounds.EYE_BUTTON_BOUNDS, Colors.BACKGROUND_COLOR.getValue(), container);
        btnLogin = GUIComponents.createButton(Constants.SIGN_IN, Bounds.LOGIN_BUTTON_BOUNDS, Colors.BUTTON_COLOR.getValue(), Fonts.BUTTON_FONT.getValue(), container);
        btnForgot = createForgotButton();
    }

    @Override
    public void setupEvents() {
        btnEye.addActionListener(this);
        btnLogin.addActionListener(this);
        btnForgot.addActionListener(this);
    }

    /**
     * Creates a `JButton` for the "Forgot" functionality.
     *
     * @return A `JButton` with the label "Forgot", styled with the specified
     *         background color and font. The button is set to be opaque, with no
     *         border, and has an error color for the foreground.
     */
    private JButton createForgotButton() {
        JButton btn = GUIComponents.createButton(Constants.FORGOT_TEXT, Bounds.FORGOT_BUTTON_BOUNDS,
                Colors.BACKGROUND_COLOR.getValue(), Fonts.BUTTON_FONT.getValue(), container);
        btn.setForeground(Colors.ERROR_COLOR.getValue());
        btn.setBorder(null);
        btn.setOpaque(true);
        return btn;
    }

    /**
     * Overrides the actionPerformed method from the ActionListener interface.
     *
     * @param e the ActionEvent object that triggered the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnForgot) {
            openForgotPasswordPanel();
        } else if (e.getSource() == btnEye) {
            togglePasswordVisibility();
        } else if (e.getSource() == btnLogin) {
            performLoginAction();
        }
    }

    /**
     * Perform the login action.
     */
    private void performLoginAction() {
        try {
            performLogin();
        } catch (SQLException ex) {
            handleLoginError();
        }
    }

    /**
     * Performs the login operation.
     *
     * @throws SQLException if there is an error in the SQL query
     */
    private void performLogin() throws SQLException {
        String password = new String(txtPassword.getPassword());
        String username = txtUser.getText().trim();
    
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = loginController.getUserByUsernameAndPassword(username, password);
            
            if (user != null) {
                LoginView.user = user.getUserName();
                handleUserLogin(user);
            } else {
                handleLoginError();
            }
        } else {
            StringUtils.showEmptyFieldsMessage();
        }
    }    

    /**
     * Handles the user login process.
     *
     * @param user the user object containing login information
     * @throws SQLException if there is an error accessing the database
     */
    private void handleUserLogin(User user) throws SQLException {
        String levelType = user.getLevelType();
        String status = user.getStatus();

        if (status.equalsIgnoreCase(States.ACTIVE.getValue())) {
            openAppropriatePanel(levelType);
        } else {
            jlError.setText(Constants.INACTIVE_USER);
        }
    }

    /**
     * Opens the appropriate panel based on the given level type.
     *
     * @param levelType the level type to determine which panel to open
     * @throws SQLException if there is an error accessing the database
     */
    private void openAppropriatePanel(String levelType) throws SQLException {
        if (levelType.equals(Roles.ROLE_ADMIN.getValue())) {
            openPanel(new AdministratorPanelView());
        } else if (levelType.equals(Roles.ROLE_CAPTURISTA.getValue())) {
            openPanel(new EmployeePanel());
        } else if (levelType.equals(Roles.ROLE_TECH.getValue())) {
            openPanel(new PanelTechnical());
        } else {
            jlError.setText(Constants.INACTIVE_USER);
        }
    }

    /**
     * Toggles the visibility of the password field.
     */
    private void togglePasswordVisibility() {
        if (!eyeEstate) {
            String passwordEye = new String(txtPassword.getPassword());
            txtPassword2.setText(passwordEye);
            txtPassword2.setVisible(true);
            txtPassword.setVisible(false);
            eyeEstate = true;
        } else {
            txtPassword.setText(txtPassword2.getText());
            txtPassword2.setVisible(false);
            txtPassword.setVisible(true);
            eyeEstate = false;
        }
    }

    /**
     * Opens the forgot password panel.
     */
    private void openForgotPasswordPanel() {
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setVisible(true);
    }

    /**
     * Sets the error message, font, and alignment for the error label.
     * Clears and resets the user and password fields.
     */
    private void handleLoginError() {
        jlError.setText(Constants.LOGIN_ERROR_TEXT);
        jlError.setFont(Fonts.ERROR_FONT.getValue());
        jlError.setHorizontalAlignment(SwingConstants.CENTER);

        txtUser.setText("");
        txtUser.requestFocus();
        txtPassword.setText("");
        txtPassword2.setText("");
    }

    /**
     * Opens a panel and disposes the current frame.
     *
     * @param panel the JFrame panel to be opened
     */
    private void openPanel(JFrame panel) {
        this.dispose();
        panel.setVisible(true);
    }
}
