package dev.juan.estevez.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.Utils;
import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.models.User;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.administrator.AdministratorPanelView;
import dev.juan.estevez.views.employee.EmployeePanel;
import dev.juan.estevez.views.technical.PanelTechnical;
import panel.utilities.ForgotPassword;

/**
 * @author Juan Carlos Estevez Vargas
 */
public final class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel container;
    private JLabel jlLogo, jlUser, jlPassword, jlError;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnEye, btnForgot;
    private JSeparator separator;
    private boolean eyeEstate;
    public static String user = "";
    private JTextField txtPassword2;
    private final UserController loginController;

    public LoginView(UserController loginController) {
        this.loginController = loginController;
        initializeFrame();
        initComponents();
    }

    /**
     * Initializes the frame.
     */
    private void initializeFrame() {
        setSize(350, 600);
        setTitle("Estevez Corporation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Initializes and sets up the components of the UI.
     */
    private void initComponents() {
        container = new JPanel();
        container.setBackground(Constants.BACKGROUND_COLOR);
        container.setLayout(null);
        setContentPane(container);

        jlLogo = GUIComponents.createImageLabel(Constants.LOGIN_LOGO_URL, Bounds.LOGO_LABEL_BOUNDS, container);
        jlUser = GUIComponents.createLabel(Constants.LOGIN_USER_TEXT, Bounds.USER_LABEL_BOUNDS, Constants.LABEL_FONT, Constants.ERROR_COLOR, container);
        jlError = GUIComponents.createLabel("", Bounds.ERROR_LABEL_BOUNDS, Constants.ERROR_FONT, Constants.ERROR_COLOR, container);
        jlPassword = GUIComponents.createLabel(Constants.LOGIN_PASSWORD_TEXT, Bounds.PASSWORD_LABEL_BOUNDS, Constants.LABEL_FONT, Constants.ERROR_COLOR, container);

        txtUser = GUIComponents.createTextField(Bounds.USER_TEXT_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, container);
        txtPassword = GUIComponents.createPasswordField(Bounds.PASSWORD_FIELD_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, container);
        txtPassword2 = GUIComponents.createTextField(Bounds.PASSWORD_TEXT_BOUNDS, Constants.TEXT_FIELD_COLOR, Constants.LABEL_FONT, container);
        txtPassword2.setVisible(false);

        btnEye = GUIComponents.createButton(Constants.EYE_ICON, Bounds.EYE_BUTTON_BOUNDS, Constants.BUTTON_BACKGROUND_COLOR, container);
        btnLogin = GUIComponents.createButton(Constants.SIGN_IN, Bounds.LOGIN_BUTTON_BOUNDS, Constants.BUTTON_COLOR, Constants.BUTTON_FONT, container);
        btnForgot = createForgotButton();

        separator = GUIComponents.createSeparator(Bounds.SEPARATOR_BOUNDS, Constants.ERROR_COLOR, container);

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
                Constants.BUTTON_BACKGROUND_COLOR, Constants.BUTTON_FONT, container);
        btn.setForeground(Constants.ERROR_COLOR);
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
        String username = txtUser.getText().trim();
        String password = txtPassword.getText().trim();

        if (!username.isEmpty() || !password.isEmpty()) {
            User user = loginController.getUserByUsernameAndPassword(username, password);
            LoginView.user = user.getUserName();
            handleUserLogin(user);
        } else {
            Utils.showEmptyFieldsMessage();
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

        if (status.equalsIgnoreCase(Constants.ACTIVE)) {
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
        switch (levelType) {
            case Constants.ROLE_ADMIN:
                openPanel(new AdministratorPanelView());
                break;
            case Constants.ROLE_CAPTURISTA:
                openPanel(new EmployeePanel());
                break;
            case Constants.ROLE_TECH:
                openPanel(new PanelTechnical());
                break;
            default:
                jlError.setText(Constants.INACTIVE_USER);
                break;
        }
    }

    /**
     * Toggles the visibility of the password field.
     */
    private void togglePasswordVisibility() {
        if (!eyeEstate) {
            txtPassword2.setText(txtPassword.getText());
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
        jlError.setFont(Constants.ERROR_FONT);
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
