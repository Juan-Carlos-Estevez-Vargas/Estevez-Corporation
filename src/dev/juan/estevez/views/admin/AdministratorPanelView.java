package dev.juan.estevez.views.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.reports.GeneratePDFReport;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.enums.Colors;
import dev.juan.estevez.utils.enums.Fonts;
import dev.juan.estevez.utils.enums.Icons;
import dev.juan.estevez.utils.enums.Users;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

public class AdministratorPanelView extends JFrame implements ActionListener, GUIInterface {

    private static final long serialVersionUID = 1L;
    private JPanel panelBack;
    private JButton btnRegisterUser;
    private JButton btnManageUser;
    private JButton btnPrintUsers;
    private JButton btnRestorePass;
    private JButton btnLogout;
    private String user;

    public AdministratorPanelView() throws SQLException {
        this.user = LoginView.user;
        initializeFrame();
        initComponents();
    }

    @Override
    public void initializeFrame() {
        setSize(630, 280);
        setTitle("Administrador - Sesión de " + user);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    @Override
    public void initComponents() {
        setupMainPanel();
        setupLabels();
        setupButtons();
        setupEvents();
    }

    @Override
    public void setupMainPanel() {
        panelBack = new JPanel();
        panelBack.setBackground(Colors.BACKGROUND_COLOR.getValue());
        panelBack.setLayout(null);
        setContentPane(panelBack);
    }

    @Override
    public void setupLabels() {
        GUIComponents.createLabel(this.user, Bounds.ADMINISTRATOR_LABEL_TITLE_BOUNDS, panelBack);
		GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.ADMINISTRATOR_REGISTER_USER_LABEL_BOUNDS, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.MANAGE_USERS_TEXT, Bounds.ADMINISTRATOR_MANAGE_USERS_LABEL_BOUNDS, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.PRINT_USERS_TEXT, Bounds.ADMINISTRATOR_PRINT_USERS_LABEL_BOUNDS, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
    }

    @Override
    public void setupTextFields() {   
    }

    @Override
    public void setupButtons() {
        btnRegisterUser = GUIComponents.createButton(Icons.ADD_USER_ICON.getValue(), Bounds.REGISTER_BUTTON_BOUNDS, null, panelBack);
        btnManageUser = GUIComponents.createButton(Icons.INFORMATION_USER_ICON.getValue(), Bounds.MANAGE_USERS_BUTTON_BOUNDS, null, panelBack);
        btnPrintUsers = GUIComponents.createButton(Icons.PRINT_USERS_ICON.getValue(), Bounds.PRINT_USERS_BUTTON_BOUNDS, null, panelBack);
        btnRestorePass = GUIComponents.createButton(Icons.RESTORE_PASS_ICON.getValue(), Bounds.RESTORE_PASS_BUTTON_BOUNDS, null, panelBack);
        btnLogout = GUIComponents.createLogoutButton(panelBack);
    }

    @Override
    public void setupEvents() {
        btnRegisterUser.addActionListener(this);
        btnManageUser.addActionListener(this);
        btnPrintUsers.addActionListener(this);        
        btnRestorePass.addActionListener(this);
        btnLogout.addActionListener(this);
    }

    /**
     * Overrides the actionPerformed method of the ActionListener interface.
     * Checks the source of the action event and calls the appropriate handler method.
     *
     * @param  e  the ActionEvent object representing the user action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRestorePass) {
            ViewUtils.handleRestorePassword();
            dispose();
        } else if (e.getSource() == btnRegisterUser) {
            ViewUtils.handleRegisterUser();
        } else if (e.getSource() == btnManageUser) {
            ViewUtils.handleManageUser();
        } else if (e.getSource() == btnLogout) {
            ViewUtils.handleLogout(this);
        } else if (e.getSource() == btnPrintUsers) {
            handlePrintUsers();
        }
    }

    /**
     * Handles the action of printing users.
     */
    private void handlePrintUsers() {
        JFileChooser fc = new JFileChooser();
        int response = fc.showSaveDialog(this);
    
        if (response != JFileChooser.APPROVE_OPTION) {
            return;
        }
    
        File chosenFile = fc.getSelectedFile();
        String outputPath = chosenFile.getAbsolutePath() + Constants.PDF_EXTENSION;
    
        try {
            List<User> users = fetchAllUsers();
            generateUserPDFReport(users, outputPath);
            JOptionPane.showMessageDialog(null, Constants.USER_LIST_CREATED_SUCCESSFULLY);
        } catch (IOException | DocumentException | SQLException ex) {
            JOptionPane.showMessageDialog(null, Constants.GENERATE_ERROR_PDF);
        }
    }
    
    /**
     * Fetches all users from the database.
     *
     * @throws SQLException if there is an error accessing the database
     * @return a list of User objects representing all the users in the database
     */
    private List<User> fetchAllUsers() throws SQLException {
        UserController userController = new UserController(new UserDAO());
        return userController.getAllUsers();
    }
    
    /**
     * Generates a PDF report for a list of users and saves it to the specified output path.
     *
     * @param  users      the list of users
     * @param  outputPath the path where the PDF report will be saved
     * @throws IOException         if there is an error in the I/O operation
     * @throws DocumentException   if there is an error in the PDF document generation
     */
    private void generateUserPDFReport(List<User> users, String outputPath) throws IOException, DocumentException {
        String[] userHeaders = { Users.NAME.getValue(), Users.EMAIL.getValue(), Users.PHONE.getValue(),
            Users.LEVEL.getValue(), Users.STATUS.getValue(), Users.REGISTERED_BY.getValue() };
        List<String[]> userData = new ArrayList<>();
    
        for (User user : users) {
            String[] userRow = {
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhone(),
                user.getLevelType(),
                user.getStatus(),
                user.getRegisterBy()
            };
            userData.add(userRow);
        }
    
        GeneratePDFReport.generatePDFReport(userData, userHeaders, Constants.USER_LIST, outputPath);
    }
    
}
