package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * Vista con le información de un cliente previamente seleccionado.
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class ClientInformation extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables.
	 */
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

	/**
	 * Constructor de clase.
	 */
	public ClientInformation() {
		initComponents();
		this.user = Login.user;
		this.user_update = ManagementClients.user_update; // Guardamos el usuario seleccionado en la tabla usuarios
		idClient = ManagementClients.id_cliente_update;
		this.setResizable(false);
		this.setSize(680, 405);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		/**
		 * Conexion a la base de datos
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM clientes WHERE id_cliente = '" + idClient + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.setTitle("Información del cliente " + rs.getString("nombre_cliente") + " - Sesión de " + user);
				this.labelTittle.setText("Información del cliente " + rs.getString("nombre_cliente"));

				/**
				 * Llenado de los campos con la base de datos
				 */
				this.txtName.setText(rs.getString("nombre_cliente"));
				this.txtEmail.setText(rs.getString("mail_cliente"));
				this.txtPhone.setText(rs.getString("tel_cliente"));
				this.txtAdress.setText(rs.getString("dir_cliente"));
				this.txtModifyBy.setText(rs.getString("ultima_modificacion"));
			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error al cargar usuario " + e);
			JOptionPane.showMessageDialog(null, "¡¡Error al cargar!! Contacte al Administrador");
		}

		/**
		 * LLenado de la tabla equipos
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
					"SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos WHERE id_cliente = '" + idClient + "'");
			ResultSet rs = pst.executeQuery();

			this.tableEquipment = new JTable(this.model);
			this.tableEquipment.setFont(new Font("serif", Font.BOLD, 14));
			this.tableEquipment.setForeground(Color.BLACK);
			this.scrollEquipment = new JScrollPane(this.tableEquipment);
			this.scrollEquipment.setBounds(270, 65, 370, 170);
			this.scrollEquipment.setViewportView(this.tableEquipment);

			this.model.addColumn("ID equipo");
			this.model.addColumn("Tipo de Equipo");
			this.model.addColumn("Marca");
			this.model.addColumn("Estatus");

			while (rs.next()) {
				Object[] row = new Object[4];
				for (int i = 0; i < 4; i++) {
					row[i] = rs.getObject(i + 1);
				}
				model.addRow(row);
			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en el llenado de la tabla equipos " + e);
		}

		/**
		 * Evento de accion para seleccionar cliente
		 */
		tableEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowPoint = tableEquipment.rowAtPoint(e.getPoint());
				int columnPoint = 0;

				if (rowPoint > -1) {
					idEquipment = (int) model.getValueAt(rowPoint, columnPoint);
					EquipmentInformation equipmentInformation = new EquipmentInformation();
					equipmentInformation.setVisible(true);
				}
			}
		});
		this.container.add(this.scrollEquipment);
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(680, 405, 680, 405);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Información del Cliente - " + this.user_update);
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(10, 10, 400, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Nombre.
		 */
		this.labelName = new JLabel("Nombre :");
		this.labelName.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelName.setForeground(new java.awt.Color(192, 192, 192));
		this.labelName.setBounds(20, 50, 100, 20);
		this.container.add(this.labelName);

		/**
		 * Label Email.
		 */
		this.labelEmail = new JLabel("Email :");
		this.labelEmail.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelEmail.setForeground(new java.awt.Color(192, 192, 192));
		this.labelEmail.setBounds(20, 110, 100, 20);
		this.container.add(this.labelEmail);

		/**
		 * Label Phone.
		 */
		this.labelPhone = new JLabel("Teléfono :");
		this.labelPhone.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelPhone.setForeground(new java.awt.Color(192, 192, 192));
		this.labelPhone.setBounds(20, 170, 100, 20);
		this.container.add(this.labelPhone);

		/**
		 * Label Dirección.
		 */
		this.labelAdress = new JLabel("Dirección :");
		this.labelAdress.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelAdress.setForeground(new java.awt.Color(192, 192, 192));
		this.labelAdress.setBounds(20, 230, 100, 20);
		this.container.add(this.labelAdress);

		/**
		 * Label Modificado por.
		 */
		this.labelModifyBy = new JLabel("Última modificación por :");
		this.labelModifyBy.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelModifyBy.setForeground(new java.awt.Color(192, 192, 192));
		this.labelModifyBy.setBounds(20, 290, 100, 20);
		this.container.add(this.labelModifyBy);

		/**
		 * Campo de texto con la información del nombre del cliente.
		 */
		this.txtName = new JTextField();
		this.txtName.setBounds(20, 70, 230, 30);
		this.txtName.setBackground(new Color(127, 140, 141));
		this.txtName.setFont(new Font("serif", Font.BOLD, 20));
		this.txtName.setHorizontalAlignment(JLabel.CENTER);
		this.txtName.setForeground(Color.WHITE);
		this.container.add(this.txtName);

		/**
		 * Campo de texto con la información del email del cliente.
		 */
		this.txtEmail = new JTextField();
		this.txtEmail.setBounds(20, 130, 230, 30);
		this.txtEmail.setBackground(new Color(127, 140, 141));
		this.txtEmail.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmail.setHorizontalAlignment(JLabel.CENTER);
		this.txtEmail.setForeground(Color.WHITE);
		this.container.add(this.txtEmail);

		/**
		 * Campo de texto con la información del teléfono del cliente.
		 */
		this.txtPhone = new JTextField();
		this.txtPhone.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtPhone.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.txtPhone.setBounds(20, 190, 230, 30);
		this.txtPhone.setBackground(new Color(127, 140, 141));
		this.txtPhone.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhone.setHorizontalAlignment(JLabel.CENTER);
		this.txtPhone.setForeground(Color.WHITE);
		this.container.add(this.txtPhone);

		/**
		 * Campo de texto con la información de la dirección del cliente.
		 */
		this.txtAdress = new JTextField();
		this.txtAdress.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtAdress.setBounds(20, 250, 230, 30);
		this.txtAdress.setBackground(new Color(127, 140, 141));
		this.txtAdress.setFont(new Font("serif", Font.BOLD, 20));
		this.txtAdress.setForeground(Color.WHITE);
		this.container.add(this.txtAdress);

		/**
		 * Campo de texto con la información de quién modificó el cliente.
		 */
		this.txtModifyBy = new JTextField();
		this.txtModifyBy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtModifyBy.setEnabled(false);
		this.txtModifyBy.setBounds(20, 310, 230, 30);
		this.txtModifyBy.setBackground(new Color(127, 140, 141));
		this.txtModifyBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtModifyBy.setForeground(Color.WHITE);
		this.container.add(this.txtModifyBy);

		/**
		 * Botón encargado de registrar un equipo a un cliente determinado.
		 */
		this.btnRegisterEquipment = new JButton("Registrar Equipo");
		this.btnRegisterEquipment.setBounds(275, 255, 210, 35);
		this.btnRegisterEquipment.setFont(new Font("serif", Font.BOLD, 20));
		this.btnRegisterEquipment.setBackground(new Color(8, 85, 224));
		this.btnRegisterEquipment.setForeground(Color.WHITE);
		this.btnRegisterEquipment.setHorizontalAlignment(JButton.CENTER);
		this.btnRegisterEquipment.addActionListener(this);
		this.container.add(this.btnRegisterEquipment);

		/**
		 * Botón para actualizar un cliente.
		 */
		this.btnUpdateClient = new JButton("Actualizar Cliente");
		this.btnUpdateClient.setBounds(275, 305, 210, 35);
		this.btnUpdateClient.setFont(new Font("serif", Font.BOLD, 20));
		this.btnUpdateClient.setBackground(new Color(8, 85, 224));
		this.btnUpdateClient.setForeground(Color.WHITE);
		this.btnUpdateClient.setHorizontalAlignment(JButton.CENTER);
		this.btnUpdateClient.addActionListener(this);
		this.container.add(this.btnUpdateClient);

		/**
		 * Botón para imprimir los clientes.
		 */
		this.btnPrint = new JButton();
		this.btnPrint.setBounds(500, 250, 100, 100);
		this.btnPrint.setIcon(new ImageIcon("src/img/impresora.png"));
		this.btnPrint.addActionListener(this);
		this.btnPrint.setBorder(null);
		this.btnPrint.setBackground(new Color(46, 59, 104));
		this.btnPrint.setOpaque(true);
		this.container.add(this.btnPrint);

	}

	/**
	 * Limpia los campos de texto.
	 */
	public void clean() {
		this.txtName.setText("");
		this.txtAdress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Registro del nuevo cliente en la base de datos.
		 */
		if (e.getSource() == this.btnRegisterEquipment) {
			RegisterEquipment RegisterEquipment = new RegisterEquipment();
			RegisterEquipment.setVisible(true);
		}

		/**
		 * Actualización de un cliente en la base de datos.
		 */
		if (e.getSource() == this.btnUpdateClient) {
			int validation = 0;
			String name, mail, phone, adress;

			name = this.txtName.getText().trim();
			mail = this.txtEmail.getText().trim();
			phone = this.txtPhone.getText().trim();
			adress = this.txtAdress.getText().trim();

			/*
			 * Validación de campos vacios
			 */
			if (name.equals("")) {
				this.txtName.setBackground(Color.red);
				validation++;
			}
			if (mail.equals("")) {
				this.txtEmail.setBackground(Color.red);
				validation++;
			}
			if (phone.equals("")) {
				this.txtPhone.setBackground(Color.red);
				validation++;
			}
			if (adress.equals("")) {
				this.txtAdress.setBackground(Color.red);
				validation++;
			}

			if (validation == 0) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
							"UPDATE clientes SET nombre_cliente = ?, mail_cliente = ?, tel_cliente = ?, dir_cliente = ?, ultima_modificacion = ? "
									+ "WHERE id_cliente = '" + idClient + "'");

					pst.setString(1, name);
					pst.setString(2, mail);
					pst.setString(3, phone);
					pst.setString(4, adress);
					pst.setString(5, user);
					pst.executeUpdate();
					cn.close();

					clean();

					this.txtName.setBackground(Color.green);
					this.txtEmail.setBackground(Color.green);
					this.txtAdress.setBackground(Color.green);
					this.txtPhone.setBackground(Color.green);
					this.txtModifyBy.setText(user);

					JOptionPane.showMessageDialog(null, "Actualización correcta");
					this.dispose();
				} catch (Exception ex) {
					System.err.println("Error en actualizar cliente " + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al actualizar cliente!! Contacta al Administrador");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
			}
		}

		/**
		 * Impresión de los usuarios existentes en el sistema.
		 */
		if (e.getSource() == this.btnPrint) {

			/**
			 * Creamos el documento pdf.
			 */
			Document document = new Document();
			try {
				String ruta = System.getProperty("user.home"); // Ruta donde guardar el documento

				/**
				 * Escribimos el documento en la computadora y le asignamos el nombre que
				 * recuperemos del txt nombre.
				 */
				PdfWriter.getInstance(document,
						new FileOutputStream(ruta + "\\" + this.txtName.getText().trim() + ".pdf"));

				/**
				 * Creamos una instancia de la clase image de itext, y colocamos la imagen que
				 * queremos
				 */
				com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/img/BannerPDF2.jpg");
				header.scaleToFit(650, 1000); // Dimensiones de la imagen
				header.setAlignment(Chunk.ALIGN_CENTER); // Alineacion de la imagen

				Paragraph paragraph = new Paragraph(); // Creamos un parrafo
				paragraph.setAlignment(Paragraph.ALIGN_CENTER); // Alineación del parrafo
				paragraph.add("Información del cliente.\n \n"); // Agregamos el título
				paragraph.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

				document.open(); // Abrimos el documento
				document.add(header);
				document.add(paragraph);

				/**
				 * Creamos una tabla donde se mostraran los datos y añadimos las columnas
				 */
				PdfPTable tableClients = new PdfPTable(5);
				tableClients.addCell("ID");
				tableClients.addCell("Nombre");
				tableClients.addCell("Email");
				tableClients.addCell("Teléfono");
				tableClients.addCell("Dirección");

				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn
							.prepareStatement("SELECT * FROM clientes WHERE id_cliente = '" + idClient + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {
						do {
							/**
							 * Recuperamos los campos de la base de datos
							 */
							tableClients.addCell(rs.getString(1));
							tableClients.addCell(rs.getString(2));
							tableClients.addCell(rs.getString(3));
							tableClients.addCell(rs.getString(4));
							tableClients.addCell(rs.getString(5));
						} while (rs.next());
						document.add(tableClients);
					}

					/**
					 * Creación de un nuevo parrafo
					 */
					Paragraph paragraph2 = new Paragraph();
					paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
					paragraph2.add("\n\nEquipos registrados.\n\n");
					paragraph2.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

					document.add(paragraph2);

					/**
					 * Creación tabla equipos registrados
					 */
					PdfPTable tableEquipments = new PdfPTable(4);
					tableEquipments.addCell("ID equipo");
					tableEquipments.addCell("Tipo");
					tableEquipments.addCell("Marca");
					tableEquipments.addCell("Estatus");

					try {
						Connection cn2 = (Connection) DatabaseConnection.conectar();
						PreparedStatement pst2 = (PreparedStatement) cn2.prepareStatement(
								"SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos WHERE id_cliente = '"
										+ idClient + "'");
						ResultSet rs2 = pst2.executeQuery();

						if (rs2.next()) {
							do {
								tableEquipments.addCell(rs2.getString(1));
								tableEquipments.addCell(rs2.getString(2));
								tableEquipments.addCell(rs2.getString(3));
								tableEquipments.addCell(rs2.getString(4));
							} while (rs.next());
							document.add(tableEquipments);
						}
					} catch (DocumentException | SQLException ex) {
						System.err.println("Error al obtener datos de equipos " + e);
					}
				} catch (DocumentException | SQLException ex) {
					System.err.println("Error al obtener datos del cliente " + e);
				}
				document.close();
				JOptionPane.showMessageDialog(null, "Reporte creado correctamente");
			} catch (DocumentException | IOException ex) {
				System.err.println("Error en pdf o ruta de imagen " + ex);
				JOptionPane.showMessageDialog(null, "¡¡Error al generar pdf!! Contacte al Administrador");
			}
		}
	}
}
