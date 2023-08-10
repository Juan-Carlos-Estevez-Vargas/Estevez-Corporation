package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * Frame principal del empleado (Vendedor, Capturista).
 *
 * @author
 *
 */
public class EmployeePanel extends JFrame implements ActionListener {

	/**
	 * Definici�n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterClient;
	private JLabel labelManageClient;
	private JLabel labelPrintClients;
	private JPanel panelBack;
	private JButton btnRegisterClient;
	private JButton btnManageClient;
	private JButton btnPrintClients;
	private JButton btnRestorePass;
	private JButton btnLogout;
	private String user;
	private String nameUser;

	public EmployeePanel() {
		initializeUI();
		retrieveAndDisplayUserName();
	}

	/**
	 * Initializes the user interface.
	 *
	 * @param None
	 * @return None
	 */
	private void initializeUI() {
		user = LoginView.user;
		setSize(630, 280);
		setTitle("Capturista - Sesión de " + user);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		initComponents();
	}

	/**
	 * Retrieves and displays the user name.
	 *
	 * @param paramName the parameter description
	 * @return the return value description
	 */
	private void retrieveAndDisplayUserName() {
		try {
			Connection cn = (Connection) DatabaseConnection.connect();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = ?");
			pst.setString(1, user);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				labelTittle.setText(nameUser);
			}

			rs.close();
			pst.close();
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en consultar capturista: " + e);
		}
	}

	/**
	 * Initializes the components of the class.
	 *
	 * This method creates the main panel, title label, register client button,
	 * manage client button, print clients button, restore password button,
	 * and logout button.
	 */
	public void initComponents() {
		createMainPanel();
		createTitleLabel();
		createRegisterClientButton();
		createManageClientButton();
		createPrintClientsButton();
		createRestorePasswordButton();
		createLogoutButton();
	}

	/**
	 * Creates the main panel.
	 */
	private void createMainPanel() {
		panelBack = new JPanel();
		panelBack.setBackground(new Color(46, 59, 104));
		panelBack.setLayout(null);
		setContentPane(panelBack);
	}

	/**
	 * Creates a title label for the UI.
	 */
	private void createTitleLabel() {
		labelTittle = new JLabel("", SwingConstants.CENTER);
		labelTittle.setBounds(10, 25, 280, 27);
		labelTittle.setForeground(new Color(192, 192, 192));
		labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		panelBack.add(labelTittle);
	}

	/**
	 * Creates a JButton with the specified parameters.
	 *
	 * @param iconPath       the path to the icon image
	 * @param x              the x-coordinate of the button's position
	 * @param y              the y-coordinate of the button's position
	 * @param width          the width of the button
	 * @param height         the height of the button
	 * @param actionListener the action listener for the button
	 * @return the created JButton
	 */
	private JButton createButton(String iconPath, int x, int y, int width, int height, ActionListener actionListener) {
		JButton button = new JButton();
		button.setBounds(x, y, width, height);
		button.setIcon(new ImageIcon(iconPath));
		button.addActionListener(actionListener);
		button.setBorder(null);
		button.setBackground(new Color(46, 59, 104));
		button.setOpaque(true);
		return button;
	}

	/**
	 * Creates a JLabel with the given text, position, and size.
	 *
	 * @param text   the text to be displayed on the label
	 * @param x      the x-coordinate of the label's position
	 * @param y      the y-coordinate of the label's position
	 * @param width  the width of the label
	 * @param height the height of the label
	 * @return the created JLabel
	 */
	private JLabel createLabel(String text, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		label.setForeground(new Color(192, 192, 192));
		label.setFont(new Font("serif", Font.BOLD, 14));
		return label;
	}

	/**
	 * Creates the register client button and label.
	 */
	private void createRegisterClientButton() {
		btnRegisterClient = createButton("src/img/addClient.png", 40, 80, 120, 100, this);
		panelBack.add(btnRegisterClient);

		labelRegisterClient = createLabel("Registrar Cliente", 45, 190, 120, 15);
		panelBack.add(labelRegisterClient);
	}

	/**
	 * Creates the manage client button.
	 */
	private void createManageClientButton() {
		btnManageClient = createButton("src/img/informationuser.png", 250, 80, 120, 100, this);
		panelBack.add(btnManageClient);

		labelManageClient = createLabel("Gestionar Clientes", 250, 190, 120, 15);
		panelBack.add(labelManageClient);
	}

	/**
	 * Creates the Print Clients button and label.
	 */
	private void createPrintClientsButton() {
		btnPrintClients = createButton("src/img/impresora.png", 460, 80, 120, 100, this);
		panelBack.add(btnPrintClients);

		labelPrintClients = createLabel("Imprimir Clientes", 460, 190, 200, 15);
		panelBack.add(labelPrintClients);
	}

	/**
	 * Creates and adds a restore password button to the panelBack.
	 */
	private void createRestorePasswordButton() {
		btnRestorePass = createButton("src/img/padlock.png", 430, 20, 40, 30, this);
		btnRestorePass.setFocusable(false);
		panelBack.add(btnRestorePass);
	}

	/**
	 * Creates a logout button.
	 */
	private void createLogoutButton() {
		btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(470, 20, 120, 30);
		btnLogout.setFont(new Font("serif", Font.BOLD, 14));
		btnLogout.setBackground(new Color(8, 85, 224));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setHorizontalAlignment(SwingConstants.CENTER);
		btnLogout.addActionListener(this);
		btnLogout.setFocusable(false);
		panelBack.add(btnLogout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRestorePass) {
			openRestorePasswordPanel();
		} else if (e.getSource() == btnRegisterClient) {
			openRegisterClientPanel();
		} else if (e.getSource() == btnLogout) {
			confirmLogout();
		} else if (e.getSource() == btnManageClient) {
			openManagementClientsPanel();
		} else if (e.getSource() == btnPrintClients) {
			createAndPrintClientList();
		}
	}

	/**
	 * Opens the restore password panel.
	 */
	private void openRestorePasswordPanel() {
		RestorePassword restorePassword = new RestorePassword();
		restorePassword.setVisible(true);
		dispose();
	}

	/**
	 * Opens the Register Client panel.
	 */
	private void openRegisterClientPanel() {
		RegisterClient registerClient = new RegisterClient();
		registerClient.setVisible(true);
	}

	/**
	 * Confirm logout by showing a dialog.
	 */
	private void confirmLogout() {
		int response = JOptionPane.showConfirmDialog(null, "¿Está seguro de cerrar la sesión?");
		if (response == JOptionPane.YES_OPTION) {
			openLoginPanel();
		}
	}

	/**
	 * Opens the login panel.
	 */
	private void openLoginPanel() {
		/*Login login = new Login();
		login.setLocationRelativeTo(null);
		login.setVisible(true);
		dispose();*/
	}

	/**
	 * Opens the management clients panel.
	 */
	private void openManagementClientsPanel() {
		ManagementClients managementClients = new ManagementClients();
		managementClients.setVisible(true);
	}

	/**
	 * Creates and prints a client list as a PDF document.
	 */
	private void createAndPrintClientList() {
		Document document = new Document();

		try {
			JFileChooser fc = new JFileChooser();
			int response = fc.showSaveDialog(this);
			if (response == JFileChooser.APPROVE_OPTION) {
				File chosenFile = fc.getSelectedFile();

				PdfWriter.getInstance(document, new FileOutputStream(chosenFile + ".pdf"));

				com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/img/BannerPDF2.jpg");
				header.scaleToFit(650, 1000);
				header.setAlignment(Element.ALIGN_CENTER);

				Paragraph paragraph = new Paragraph();
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.add("Lista de Clientes\n\n");
				paragraph.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));

				document.open();
				document.add(header);
				document.add(paragraph);

				PdfPTable table = new PdfPTable(5);
				table.addCell("ID Cliente");
				table.addCell("Nombre");
				table.addCell("Email");
				table.addCell("Teléfono");
				table.addCell("Dirección");

				try {
					Connection cn = (Connection) DatabaseConnection.connect();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement("SELECT * FROM clientes");
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						table.addCell(rs.getString(1));
						table.addCell(rs.getString(2));
						table.addCell(rs.getString(3));
						table.addCell(rs.getString(4));
						table.addCell(rs.getString(5));
					}
					document.add(table);
				} catch (SQLException ex) {
					System.err.println("Error al generar lista de clientes " + ex);
				}
			}
			document.close();
			JOptionPane.showMessageDialog(null, "Lista de clientes creada correctamente");
		} catch (DocumentException | IOException ex) {
			System.err.println("Error al generar PDF " + ex);
			JOptionPane.showMessageDialog(null, "¡Error al generar PDF! Contacte al Administrador");
		}
	}

}
