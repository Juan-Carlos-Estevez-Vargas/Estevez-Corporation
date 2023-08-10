package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientInformation extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private DefaultTableModel model = new DefaultTableModel();
	public static int idEquipment = 0;
	public static int idClient = 0;
	private JLabel labelTittle;
	private JLabel labelName;
	private JLabel labelEmail;
	private JLabel labelPhone;
	private JLabel labelAdress;
	private JLabel labelModifyBy;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtAdress;
	private JTextField txtModifyBy;
	private JTable tableEquipment;
	private JScrollPane scrollEquipment;
	private JPanel container;
	private JButton btnRegisterEquipment;
	private JButton btnUpdateClient;
	private JButton btnPrint;

	public ClientInformation() {
		initComponents();
		initializeClientInformation();
	}

	/**
	 * Initializes the client information.
	 */
	private void initializeClientInformation() {
		user = LoginView.user;
		user_update = ManagementClients.user_update;
		idClient = ManagementClients.id_cliente_update;

		setupWindowProperties();
		loadClientData();
		loadEquipmentTable();
		setupEquipmentTableEvent();
	}

	/**
	 * Sets up the properties of the window.
	 */
	private void setupWindowProperties() {
		setResizable(false);
		setSize(680, 405);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * Loads client data from the database and populates the client fields.
	 *
	 * @throws SQLException if an error occurs during the database operation
	 */
	private void loadClientData() {
		try {
			Connection connection = DatabaseConnection.connect();
			PreparedStatement pst = connection.prepareStatement("SELECT * FROM clientes WHERE id_cliente = ?");
			pst.setInt(1, idClient);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				String clientName = rs.getString("nombre_cliente");
				setTitleAndLabels(clientName);
				fillClientFields(rs);
			}

			rs.close();
			pst.close();
			connection.close();
		} catch (SQLException e) {
			handleLoadError(e);
		}
	}

	/**
	 * Sets the title and labels for the client information page.
	 *
	 * @param clientName the name of the client
	 */
	private void setTitleAndLabels(String clientName) {
		setTitle("Información del cliente " + clientName + " - Sesión de " + user);
		labelTittle.setText("Información del cliente " + clientName);
	}

	/**
	 * Fills the client fields with the values retrieved from the given ResultSet.
	 *
	 * @param rs the ResultSet containing the client data
	 * @throws SQLException if a database access error occurs
	 */
	private void fillClientFields(ResultSet rs) throws SQLException {
		txtName.setText(rs.getString("nombre_cliente"));
		txtEmail.setText(rs.getString("mail_cliente"));
		txtPhone.setText(rs.getString("tel_cliente"));
		txtAdress.setText(rs.getString("dir_cliente"));
		txtModifyBy.setText(rs.getString("ultima_modificacion"));
	}

	/**
	 * Loads the equipment table from the database.
	 */
	private void loadEquipmentTable() {
		try {
			Connection connection = DatabaseConnection.connect();
			PreparedStatement pst = connection.prepareStatement(
					"SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos WHERE id_cliente = ?");
			pst.setInt(1, idClient);
			ResultSet rs = pst.executeQuery();

			setupEquipmentTable(rs);

			while (rs.next()) {
				addRowToEquipmentTable(rs);
			}

			rs.close();
			pst.close();
			connection.close();
		} catch (SQLException e) {
			handleEquipmentTableError(e);
		}
	}

	/**
	 * Sets up the equipment table.
	 *
	 * @param rs the ResultSet containing the data for the table
	 * @throws SQLException if an SQL exception occurs
	 */
	private void setupEquipmentTable(ResultSet rs) throws SQLException {
		tableEquipment = new JTable(model);
		tableEquipment.setFont(new Font("serif", Font.BOLD, 14));
		tableEquipment.setForeground(Color.BLACK);

		scrollEquipment = new JScrollPane(tableEquipment);
		scrollEquipment.setBounds(270, 65, 370, 170);
		scrollEquipment.setViewportView(tableEquipment);

		model.addColumn("ID equipo");
		model.addColumn("Tipo de Equipo");
		model.addColumn("Marca");
		model.addColumn("Estatus");
	}

	/**
	 * Adds a row to the equipment table.
	 *
	 * @param rs the ResultSet containing the row data
	 * @throws SQLException if there is an error accessing the database
	 */
	private void addRowToEquipmentTable(ResultSet rs) throws SQLException {
		Object[] row = new Object[4];
		for (int i = 0; i < 4; i++) {
			row[i] = rs.getObject(i + 1);
		}
		model.addRow(row);
	}

	/**
	 * Sets up the event listener for the equipment table.
	 *
	 * @param e the MouseEvent object that triggered the event
	 */
	private void setupEquipmentTableEvent() {
		tableEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowPoint = tableEquipment.rowAtPoint(e.getPoint());
				int columnPoint = 0;

				if (rowPoint > -1) {
					idEquipment = (int) model.getValueAt(rowPoint, columnPoint);
					EquipmentInformation equipmentInformation = new EquipmentInformation();
					equipmentInformation.setVisible(true);
					dispose();
				}
			}
		});

		container.add(scrollEquipment);
	}

	/**
	 * Handles the load error by printing the error message and displaying an error
	 * message dialog.
	 *
	 * @param ex the SQLException that occurred during the load operation
	 */
	private void handleLoadError(SQLException ex) {
		System.err.println("Error al cargar cliente " + ex);
		JOptionPane.showMessageDialog(null, "¡Error al cargar! Contacte al Administrador");
	}

	/**
	 * A description of the handleEquipmentTableError function.
	 *
	 * @param ex the SQLException object that occurred
	 */
	private void handleEquipmentTableError(SQLException ex) {
		System.err.println("Error en el llenado de la tabla equipos " + ex);
	}

	/**
	 * Initializes the components of the class.
	 */
	public void initComponents() {
		setupMainPanel();
		setupLabels();
		setupTextFields();
		setupButtons();
	}

	/**
	 * Sets up the main panel.
	 */
	private void setupMainPanel() {
		container = new JPanel();
		container.setBackground(new Color(46, 59, 104));
		container.setLayout(null);
		container.setBounds(680, 405, 680, 405);
		setContentPane(container);
	}

	/**
	 * Sets up the labels for the user interface.
	 */
	private void setupLabels() {
		labelTittle = new JLabel("Información del Cliente - " + user_update);
		setupLabel(labelTittle, 10, 10, 400, 30);
		labelName = setupLabel("Nombre :", 20, 50);
		labelEmail = setupLabel("Email :", 20, 110);
		labelPhone = setupLabel("Teléfono :", 20, 170);
		labelAdress = setupLabel("Dirección :", 20, 230);
		labelModifyBy = setupLabel("Última modificación por :", 20, 290);
	}

	/**
	 * Sets up and returns a JLabel with the given text, x-coordinate, and
	 * y-coordinate.
	 *
	 * @param text the text to be displayed on the label
	 * @param x    the x-coordinate of the label
	 * @param y    the y-coordinate of the label
	 * @return the created JLabel
	 */
	private JLabel setupLabel(String text, int x, int y) {
		JLabel label = new JLabel(text);
		setupLabel(label, x, y, 100, 20);
		return label;
	}

	/**
	 * Sets up a label with the specified position and size.
	 *
	 * @param label  the JLabel to be set up
	 * @param x      the x-coordinate of the label
	 * @param y      the y-coordinate of the label
	 * @param width  the width of the label
	 * @param height the height of the label
	 */
	private void setupLabel(JLabel label, int x, int y, int width, int height) {
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		label.setForeground(new Color(192, 192, 192));
		label.setBounds(x, y, width, height);
		container.add(label);
	}

	/**
	 * Sets up the text fields for the form.
	 *
	 * @param paramName1 the first parameter description
	 * @param paramName2 the second parameter description
	 * @return the return value description
	 */
	private void setupTextFields() {
		txtName = setupTextField(20, 70);
		txtEmail = setupTextField(20, 130);
		txtPhone = setupTextField(20, 190);
		txtAdress = setupTextField(20, 250);
		txtModifyBy = setupTextField(20, 310);
		txtModifyBy.setEnabled(false);
	}

	/**
	 * Sets up a text field at the specified coordinates.
	 *
	 * @param x the x-coordinate of the text field
	 * @param y the y-coordinate of the text field
	 * @return the created text field
	 */
	private JTextField setupTextField(int x, int y) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, 230, 30);
		textField.setBackground(new Color(127, 140, 141));
		textField.setFont(new Font("serif", Font.BOLD, 20));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.WHITE);
		container.add(textField);
		return textField;
	}

	/**
	 * Sets up the buttons for the user interface.
	 */
	private void setupButtons() {
		btnRegisterEquipment = setupButton("Registrar Equipo", 275, 255);
		btnUpdateClient = setupButton("Actualizar Cliente", 275, 305);
		btnPrint = setupPrintButton(500, 250);
	}

	/**
	 * Sets up a JButton with the given text, x and y position.
	 *
	 * @param text the text to be displayed on the button
	 * @param x    the x position of the button
	 * @param y    the y position of the button
	 * @return the created JButton
	 */
	private JButton setupButton(String text, int x, int y) {
		JButton button = new JButton(text);
		button.setBounds(x, y, 210, 35);
		button.setFont(new Font("serif", Font.BOLD, 20));
		button.setBackground(new Color(8, 85, 224));
		button.setForeground(Color.WHITE);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.addActionListener(this);
		container.add(button);
		return button;
	}

	/**
	 * Generates a JButton and sets its properties.
	 *
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @return the generated JButton
	 */
	private JButton setupPrintButton(int x, int y) {
		JButton button = new JButton();
		button.setBounds(x, y, 100, 100);
		button.setIcon(new ImageIcon("src/img/impresora.png"));
		button.addActionListener(this);
		button.setBorder(null);
		button.setBackground(new Color(46, 59, 104));
		button.setOpaque(true);
		container.add(button);
		return button;
	}

	/**
	 * Clears the text fields for name, address, email, and phone number.
	 */
	public void clean() {
		this.txtName.setText("");
		this.txtAdress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
	}

	/**
	 * Handles the action performed when an event is triggered.
	 *
	 * @param e the ActionEvent object representing the event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterEquipment) {
			openRegisterEquipmentDialog();
		} else if (e.getSource() == btnUpdateClient) {
			updateClientInformation();
		} else if (e.getSource() == btnPrint) {
			createClientInformationPdf();
		}
	}

	/**
	 * Opens the register equipment dialog.
	 */
	private void openRegisterEquipmentDialog() {
		RegisterEquipment registerEquipment = new RegisterEquipment();
		registerEquipment.setVisible(true);
	}

	/**
	 * Updates the client information in the database.
	 *
	 * @param paramName description of parameter
	 * @return description of return value
	 */
	private void updateClientInformation() {
		String name = txtName.getText().trim();
		String mail = txtEmail.getText().trim();
		String phone = txtPhone.getText().trim();
		String adress = txtAdress.getText().trim();

		if (validateFields(mail, name, phone, adress)) {
			try {
				Connection cn = (Connection) DatabaseConnection.connect();
				PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
						"UPDATE clientes SET nombre_cliente = ?, mail_cliente = ?, tel_cliente = ?, dir_cliente = ?, ultima_modificacion = ? "
								+ "WHERE id_cliente = '" + idClient + "'");

				pst.setString(1, name);
				pst.setString(2, mail);
				pst.setString(3, phone);
				pst.setString(4, adress);
				pst.setString(5, user);
				pst.executeUpdate();

				pst.close();
				cn.close();

				cleanFields();
				setFieldsBackground(Color.green);
				txtModifyBy.setText(user);

				JOptionPane.showMessageDialog(null, "Actualización correcta");
				dispose();
			} catch (Exception ex) {
				handleUpdateError(ex);
			}
		} else {
			handleValidationErrors();
		}
	}

	/**
	 * Validates the fields for a given email, name, phone, and address.
	 *
	 * @param mail   the email to be validated
	 * @param name   the name to be validated
	 * @param phone  the phone number to be validated
	 * @param adress the address to be validated
	 * @return true if all fields are valid, false otherwise
	 */
	private boolean validateFields(String mail, String name, String phone, String adress) {
		boolean isValid = true;
		if (mail.isEmpty() || mail.length() >= 40 || !(mail.contains("@") && mail.contains("."))) {
			isValid = false;
			txtEmail.setBackground(Color.red);
		}
		if (name.isEmpty() || name.length() >= 35) {
			isValid = false;
			txtName.setBackground(Color.red);
		}
		if (phone.isEmpty() || phone.length() >= 12) {
			isValid = false;
			txtPhone.setBackground(Color.red);
		}
		if (adress.isEmpty() || adress.length() >= 60) {
			isValid = false;
			txtAdress.setBackground(Color.red);
		}
		return isValid;
	}

	/**
	 * Cleans the fields by setting their text to an empty string.
	 */
	private void cleanFields() {
		txtName.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		txtAdress.setText("");
	}

	/**
	 * Sets the background color of the fields.
	 *
	 * @param color the color to set the background to
	 */
	private void setFieldsBackground(Color color) {
		txtName.setBackground(color);
		txtEmail.setBackground(color);
		txtAdress.setBackground(color);
		txtPhone.setBackground(color);
	}

	/**
	 * Handles an update error by printing the error message and displaying an error
	 * message dialog.
	 *
	 * @param ex the exception that occurred during the update
	 */
	private void handleUpdateError(Exception ex) {
		System.err.println("Error en actualizar cliente " + ex);
		JOptionPane.showMessageDialog(null, "¡¡Error al actualizar cliente!! Contacta al Administrador");
	}

	/**
	 * Handles validation errors by showing a message dialog with the text "Debes
	 * llenar todos los campos".
	 */
	private void handleValidationErrors() {
		JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
	}

	/**
	 * Creates a PDF document containing client information.
	 *
	 * @throws DocumentException if there is an error in the PDF document generation
	 * @throws IOException       if there is an error in file handling
	 */
	private void createClientInformationPdf() {
		Document document = new Document();

		try {
			JFileChooser fc = new JFileChooser();
			int response = fc.showSaveDialog(this);
			if (response == JFileChooser.APPROVE_OPTION) {
				File chosenFile = fc.getSelectedFile();

				PdfWriter.getInstance(document, new FileOutputStream(chosenFile + ".pdf"));

				document.open();
				addHeaderAndTitleToPdf(document);

				PdfPTable tableClients = createClientsTable();
				populateClientsTable(tableClients);

				document.add(tableClients);
				addEquipmentsSectionToPdf(document);
			}
			document.close();
			JOptionPane.showMessageDialog(null, "Reporte creado correctamente");
		} catch (DocumentException | IOException ex) {
			handlePdfGenerationError(ex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a header and title to the PDF document.
	 *
	 * @param document the PDF document to add the header and title to
	 */
	private void addHeaderAndTitleToPdf(Document document) {
		com.itextpdf.text.Image header;
		try {
			header = com.itextpdf.text.Image.getInstance("src/img/BannerPDF2.jpg");
			header.scaleToFit(650, 1000);
			header.setAlignment(Element.ALIGN_CENTER);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.add("Información del cliente.\n \n");
			paragraph.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
			document.open();
			document.add(header);
			document.add(paragraph);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * Creates a PDF table for clients.
	 *
	 * @return the created PDF table for clients
	 */
	private PdfPTable createClientsTable() {
		PdfPTable tableClients = new PdfPTable(5);
		tableClients.addCell("ID");
		tableClients.addCell("Nombre");
		tableClients.addCell("Email");
		tableClients.addCell("Teléfono");
		tableClients.addCell("Dirección");
		return tableClients;
	}

	/**
	 * Populates the clients table in the PDF with data from the database.
	 *
	 * @param tableClients the PdfPTable object representing the clients table
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private void populateClientsTable(PdfPTable tableClients) throws SQLException {
		Connection cn = (Connection) DatabaseConnection.connect();
		PreparedStatement pst = (PreparedStatement) cn
				.prepareStatement("SELECT * FROM clientes WHERE id_cliente = '" + idClient + "'");
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			do {
				tableClients.addCell(rs.getString(1));
				tableClients.addCell(rs.getString(2));
				tableClients.addCell(rs.getString(3));
				tableClients.addCell(rs.getString(4));
				tableClients.addCell(rs.getString(5));
			} while (rs.next());
		}
		rs.close();
		pst.close();
		cn.close();
	}

	/**
	 * Adds the "Equipos registrados" section to the PDF document.
	 *
	 * @param document the PDF document to add the section to
	 * @throws SQLException if there is an error with the SQL query execution
	 */
	private void addEquipmentsSectionToPdf(Document document) throws SQLException {
		Paragraph paragraph2 = new Paragraph();
		paragraph2.setAlignment(Element.ALIGN_CENTER);
		paragraph2.add("\n\nEquipos registrados.\n\n");
		paragraph2.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
		try {
			document.add(paragraph2);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PdfPTable tableEquipments = new PdfPTable(4);
		tableEquipments.addCell("ID equipo");
		tableEquipments.addCell("Tipo");
		tableEquipments.addCell("Marca");
		tableEquipments.addCell("Estatus");

		Connection cn2 = (Connection) DatabaseConnection.connect();
		PreparedStatement pst2 = (PreparedStatement) cn2.prepareStatement(
				"SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos WHERE id_cliente = '" + idClient + "'");
		ResultSet rs2 = pst2.executeQuery();

		if (rs2.next()) {
			do {
				tableEquipments.addCell(rs2.getString(1));
				tableEquipments.addCell(rs2.getString(2));
				tableEquipments.addCell(rs2.getString(3));
				tableEquipments.addCell(rs2.getString(4));
			} while (rs2.next());
			try {
				document.add(tableEquipments);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs2.close();
		pst2.close();
		cn2.close();
	}

	/**
	 * Handles an error that occurs during PDF generation.
	 *
	 * @param ex the exception that occurred
	 */
	private void handlePdfGenerationError(Exception ex) {
		System.err.println("Error en pdf o ruta de imagen " + ex);
		JOptionPane.showMessageDialog(null, "¡¡Error al generar pdf!! Contacta al Administrador");
	}

}
