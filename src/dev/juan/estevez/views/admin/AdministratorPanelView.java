package dev.juan.estevez.views.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.reports.CustomPDFReport;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.Bounds;
import dev.juan.estevez.utils.constants.AdminConstants;
import dev.juan.estevez.utils.constants.ReportConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class AdministratorPanelView extends JFrame implements ActionListener, IGui {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton btnRegister;
    private JButton btnManage;
    private JButton btnPrint;
    private JButton btnRestorePass;
    private JButton btnLogout;
    private String user;

    public AdministratorPanelView() {
        user = LoginView.user;
        initializeFrame();
        initComponents();
    }

    @Override
    public void initializeFrame() {
        setSize(630, 280);
        setTitle(String.format(AdminConstants.ADMIN_PANEL_SESION, user));
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
        panel = new JPanel();
        panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
        panel.setLayout(null);
        setContentPane(panel);
    }

    @Override
    public void setupLabels() {
        GUIComponents
            .createLabel(this.user, Bounds.LABEL_ADMIN_TITLE, panel)
            .setFont(Fonts.BUTTON_FONT.getValue());

        GUIComponents
            .createLabel(AdminConstants.USER_REGISTER, Bounds.LABEL_ADMIN_USER_REGISTER, panel)
            .setFont(Fonts.PANEL_LABEL_FONT.getValue());

        GUIComponents
            .createLabel(AdminConstants.MANAGE_USERS, Bounds.LABEL_ADMIN_MANAGE_USERS, panel)
            .setFont(Fonts.PANEL_LABEL_FONT.getValue());

        GUIComponents
            .createLabel(AdminConstants.PRINT_USERS, Bounds.LABEL_ADMIN_PRINT_USERS, panel)
            .setFont(Fonts.PANEL_LABEL_FONT.getValue());
    }

    @Override
    public void setupButtons() {
        btnRegister = GUIComponents.createButton(Icons.ADD_USER.getValue(), Bounds.BUTTON_ADMIN_REGISTER, null, panel);
        btnManage = GUIComponents.createButton(Icons.INFORMATION_USER.getValue(), Bounds.BUTTON_ADMIN_MANAGE_USERS, null, panel);
        btnPrint = GUIComponents.createButton(Icons.PRINT_USERS.getValue(), Bounds.BUTTON_ADMIN_PRINT_USERS, null, panel);
        btnRestorePass = GUIComponents.createButton(Icons.RESTORE_PASS.getValue(), Bounds.BUTTON_RESTORE_PASSWORD, null, panel);
        btnLogout = GUIComponents.createLogoutButton(panel);
    }

    @Override
    public void setupEvents() {
        btnRegister.addActionListener(this);
        btnManage.addActionListener(this);
        btnPrint.addActionListener(this);
        btnRestorePass.addActionListener(this);
        btnLogout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRestorePass) {
            ViewUtils.openPanel(new RestorePassword());
        } else if (e.getSource() == btnRegister) {
            ViewUtils.openPanel(new RegisterUserView());
        } else if (e.getSource() == btnManage) {
            ViewUtils.openPanel(new ManagementUsersView());
        } else if (e.getSource() == btnLogout) {
            ViewUtils.handleLogout(this);
        } else if (e.getSource() == btnPrint) {
            handlePrintUsers();
        }
    }

    private void handlePrintUsers() {
        JFileChooser fc = new JFileChooser();
        int response = fc.showSaveDialog(this);

        if (response != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File chosenFile = fc.getSelectedFile();
        String outputPath = chosenFile.getAbsolutePath() + ReportConstants.PDF_EXTENSION;

        try {
            List<User> users = fetchAllUsers();
            CustomPDFReport.generateUserPDFReport(users, outputPath);
            JOptionPane.showMessageDialog(null, ReportConstants.PDF_REPORT_CREATED);
        } catch (IOException | DocumentException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ReportConstants.GENERATE_PDF_ERROR);
        }
    }

    private List<User> fetchAllUsers() throws SQLException {
        UserService userController = new UserService(new UserDAO());
        return userController.getAllUsers();
    }

    @Override
    public void setupTextFields() {
        throw new UnsupportedOperationException("Unimplemented method 'setupTextFields'");
    }

}
