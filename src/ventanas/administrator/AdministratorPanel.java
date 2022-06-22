package ventanas.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
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
 * Vista principal del administrador.
 * 
 * @author
 *
 */
public class AdministratorPanel extends JFrame implements ActionListener {

	/**
	 * Definici�n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterUser;
	private JLabel labelManageUser;
	private JLabel labelPrintUsers;
	private JPanel panelBack;
	private JButton btnRegisterUser;
	private JButton btnManageUser;
	private JButton btnPrintUsers;
	private JButton btnLogout;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public AdministratorPanel() {
		this.user = Login.user;
		this.setSize(630, 280);
		this.setTitle("Administrador - Sesi�n de " + this.user);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();

		/**
		 * Recuperando el nombre del usuario.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText("Bienvenido " + nameUser);
			}
		} catch (Exception e) {
			System.err.println("Error en conexi�n desde la interfaz Administrador");
		}
	}

	/**
	 * Inicializa y construye los componentes Swing en el Frame principal.
	 */
	public void initComponents() {

		/**
		 * Panel principal.
		 */
		this.panelBack = new JPanel();
		this.panelBack.setBackground(new Color(46, 59, 104));
		this.panelBack.setLayout(null);
		this.setContentPane(this.panelBack);

		/*
		 * Label Principal.
		 */
		this.labelTittle = new JLabel();
		this.labelTittle.setBounds(10, 10, 380, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(this.labelTittle);

		/*
		 * Bot�n para registrar un nuevo Usuario en el sistema.
		 */
		this.btnRegisterUser = new JButton();
		this.btnRegisterUser.setBounds(40, 80, 120, 100);
		this.btnRegisterUser.setIcon(new ImageIcon("src/img/addUser.png"));
		this.btnRegisterUser.addActionListener(this);
		this.btnRegisterUser.setBorder(null);
		this.btnRegisterUser.setBackground(new Color(46, 59, 104));
		this.btnRegisterUser.setOpaque(true);
		this.panelBack.add(this.btnRegisterUser);

		/**
		 * Label Registrar Usuario.
		 */
		this.labelRegisterUser = new JLabel("Registrar Usuario");
		this.labelRegisterUser.setBounds(45, 190, 120, 15);
		this.labelRegisterUser.setForeground(new Color(192, 192, 192));
		this.labelRegisterUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelRegisterUser);

		/**
		 * Bot�n encargado de mostrar la lista de usuarios registrados en el sistema.
		 */
		this.btnManageUser = new JButton();
		this.btnManageUser.setBounds(250, 80, 120, 100);
		this.btnManageUser.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageUser.addActionListener(this);
		this.btnManageUser.setBorder(null);
		this.btnManageUser.setBackground(new Color(46, 59, 104));
		this.btnManageUser.setOpaque(true);
		this.panelBack.add(this.btnManageUser);

		/**
		 * Label Gestionar Usuarios.
		 */
		this.labelManageUser = new JLabel("Gestionar Usuarios");
		this.labelManageUser.setBounds(250, 190, 120, 15);
		this.labelManageUser.setForeground(new Color(192, 192, 192));
		this.labelManageUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManageUser);

		/**
		 * Label para imprimir los usuarios existentes en el sistema.
		 */
		this.btnPrintUsers = new JButton();
		this.btnPrintUsers.setBounds(460, 80, 120, 100);
		this.btnPrintUsers.setIcon(new ImageIcon("src/img/impresora.png"));
		this.btnPrintUsers.setBorder(null);
		this.btnPrintUsers.setBackground(new Color(46, 59, 104));
		this.btnPrintUsers.setOpaque(true);
		this.btnPrintUsers.addActionListener(this);
		this.panelBack.add(this.btnPrintUsers);

		/**
		 * Bot�n para imprimir los usuarios.
		 */
		this.labelPrintUsers = new JLabel("Imprimir Usuarios");
		this.labelPrintUsers.setBounds(460, 190, 120, 15);
		this.labelPrintUsers.setForeground(new Color(192, 192, 192));
		this.labelPrintUsers.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelPrintUsers);

		/**
		 * Bot�n Cerrar Sesi�n.
		 */
		this.btnLogout = new JButton("Cerrar Sesi�n");
		this.btnLogout.setBounds(470, 20, 120, 30);
		this.btnLogout.setFont(new Font("serif", Font.BOLD, 14));
		this.btnLogout.setBackground(new Color(8, 85, 224));
		this.btnLogout.setForeground(Color.WHITE);
		this.btnLogout.setHorizontalAlignment(JButton.CENTER);
		this.btnLogout.addActionListener(this);
		this.panelBack.add(this.btnLogout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Objetos de las clases (ventanas) a utilizar.
		 */
		RegisterUser registerUser;
		ManagementUsers managementUsers;

		/**
		 * Muestra el panel para registrar un usuario si se seleccion� dicha acci�n.
		 */
		if (e.getSource() == this.btnRegisterUser) {
			registerUser = new RegisterUser();
			registerUser.setVisible(true);
		}

		/**
		 * Muestra el panel con el listado de los usuarios registrados si se seleccion�
		 * dicha acci�n.
		 */
		if (e.getSource() == this.btnManageUser) {
			managementUsers = new ManagementUsers();
			managementUsers.setVisible(true);
		}

		/**
		 * Cierra la sesi�n del usuario en cuesti�n.
		 */
		if (e.getSource() == this.btnLogout) {
			Login login = new Login();
			login.setLocationRelativeTo(null);
			login.setVisible(true);
			this.dispose();
		}

		/**
		 * Imprime los usuarios existentes en el sistema.
		 */
		if (e.getSource() == this.btnPrintUsers) {
			Document document = new Document();
			
			try {

				JFileChooser fc = new JFileChooser();
				// Mostrar la ventana para abrir archivo y recoger la respuesta
				// En el par�metro del showOpenDialog se indica la ventana
				// al que estar� asociado. Con el valor this se asocia a la
				// ventana que la abre.
				int response = fc.showSaveDialog(this);
				// Comprobar si se ha pulsado Aceptar
				if (response == JFileChooser.APPROVE_OPTION) {
					// Crear un objeto File con el archivo elegido
					File chosenFile = fc.getSelectedFile();

					PdfWriter.getInstance(document, new FileOutputStream(chosenFile + ".pdf"));

					com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/img/BannerPDF2.jpg");
					header.scaleToFit(650, 1000);
					header.setAlignment(Chunk.ALIGN_CENTER);

					Paragraph paragraph = new Paragraph();
					paragraph.setAlignment(Paragraph.ALIGN_CENTER);
					paragraph.add("Lista de Usuarios\n\n");
					paragraph.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));

					document.open();
					document.add(header);
					document.add(paragraph);

					PdfPTable table = new PdfPTable(6);
					table.addCell("Nombre");
					table.addCell("Email");
					table.addCell("Tel�fono");
					table.addCell("Nivel");
					table.addCell("Estado");
					table.addCell("Registrado por");

					try {
						Connection cn = (Connection) DatabaseConnection.conectar();
						PreparedStatement pst = (PreparedStatement) cn.prepareStatement("SELECT * FROM usuarios");
						ResultSet rs = pst.executeQuery();

						if (rs.next()) {
							do {
								table.addCell(rs.getString(2));
								table.addCell(rs.getString(3));
								table.addCell(rs.getString(4));
								table.addCell(rs.getString(7));
								table.addCell(rs.getString(8));
								table.addCell(rs.getString(9));
							} while (rs.next());
							document.add(table);
						}
					} catch (SQLException ex) {
						System.err.println("Error al generar lista de usuarios " + ex);
					}
				}
				document.close();
				JOptionPane.showMessageDialog(null, "Listado de usuarios creada correctamente");
			} catch (DocumentException | IOException ex) {
				System.err.println("Error al generar PDF " + ex);
				JOptionPane.showMessageDialog(null, "��Error al generar PDF!! Contacte al Administrador");
			}

		}
	}

}
