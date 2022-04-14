package ventanas.administrator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class RestorePassword extends JFrame implements ActionListener{

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	String user = "", user_update = "";
	private JPanel container;
	private JLabel labelTittle;
	private JLabel labelNewPassword;
	private JLabel labelConfirmPassword;
	private JTextField txtNewPassword;
	private JTextField txtConfirmPassword;
	private JButton btnRestorePassword;
	
	/**
     * Creates new form RestaurarPassword
     */
    public RestorePassword() {
        initComponents();
        //user = Login.user;
        user_update = ManagementUsers.user_update;
        this.setSize(360, 270);
        this.setResizable(false);
        this.setTitle("Cambio de password para " + user_update);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void initComponents() {
    	/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(360, 270, 360, 270);
		this.setContentPane(container);

		/**
		 * Label Principal.
		 */
		labelTittle = new JLabel("Cambio de Password");
		labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 22));
		labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelTittle.setBounds(50, 10, 300, 30);
		this.container.add(labelTittle);	
		
		/**
		 * Label New Password.
		 */
		labelNewPassword = new JLabel();
		labelNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 18));
		labelNewPassword.setForeground(new java.awt.Color(192, 192, 192));
		labelNewPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		labelNewPassword.setText("Nuevo Password");
		labelNewPassword.setBounds(20, 50, 200, 20);
		this.container.add(labelNewPassword);
		
		/**
		 * Campo de texto para el nuevo password del usuario en cuestión.
		 */
		txtNewPassword = new JTextField();
		txtNewPassword.setFont(new java.awt.Font("Segoe UI", 1, 16));
		txtNewPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtNewPassword.setBounds(20, 70, 300, 30);
		this.txtNewPassword.setBackground(new Color(127, 140, 141));
		this.txtNewPassword.setForeground(Color.WHITE);
		this.container.add(txtNewPassword);
		
		/**
		 * Label Confirm Password.
		 */
		labelConfirmPassword = new JLabel();
		labelConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 18));
		labelConfirmPassword.setForeground(new java.awt.Color(192, 192, 192));
		labelConfirmPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		labelConfirmPassword.setText("Confirmar Password");
		labelConfirmPassword.setBounds(20, 110, 200, 20);
		this.container.add(labelConfirmPassword);
		
		/**
		 * Campo de texto para el confirmar el password del usuario en cuestión.
		 */
		txtConfirmPassword = new JTextField();
		txtConfirmPassword.setFont(new java.awt.Font("Segoe UI", 1, 16));
		txtConfirmPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtConfirmPassword.setBounds(20, 130, 300, 30);
		this.txtConfirmPassword.setBackground(new Color(127, 140, 141));
		this.txtConfirmPassword.setForeground(Color.WHITE);
		this.container.add(txtConfirmPassword);
		
		/**
		 * Botón para restaurar la contraseña del usuario en cuestión.
		 */
		btnRestorePassword = new JButton("Restaurar Password");
		btnRestorePassword.setFont(new java.awt.Font("Tahoma", 1, 18));
		btnRestorePassword.setBorder(null);
		btnRestorePassword.addActionListener(this);
		btnRestorePassword.setBounds(20, 180, 300, 35);
		this.btnRestorePassword.setBackground(new Color(8, 85, 224));
		this.btnRestorePassword.setForeground(Color.WHITE);
		this.container.add(btnRestorePassword);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.btnRestorePassword) {
			String password, confirm_password;

	        password = txtNewPassword.getText().trim();
	        confirm_password = txtConfirmPassword.getText().trim();

	        //Validacion de campos 
	        if (!password.equals("") && !confirm_password.equals("")) {

	            if (password.equals(confirm_password)) {

	                try {

	                    Connection cn = (Connection) DatabaseConnection.conectar();
	                    PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
	                            "UPDATE usuarios SET password = ? WHERE username = '" + user_update + "'");

	                    pst.setString(1, password);
	                    pst.executeUpdate();

	                    cn.close();

	                    txtNewPassword.setBackground(Color.green);
	                    txtConfirmPassword.setBackground(Color.green);

	                    JOptionPane.showMessageDialog(null, "Restauración exitosa");
	                    this.dispose();

	                } catch (Exception ex) {
	                    System.err.println("Error en restaurar password " + ex);
	                    JOptionPane.showConfirmDialog(null, "¡¡Error al restaurar contraseña, contacte al Administrador!!");
	                }

	            } else {
	                txtConfirmPassword.setBackground(Color.red);
	                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
	            }

	        } else {
	            txtNewPassword.setBackground(Color.red);
	            txtConfirmPassword.setBackground(Color.red);
	            JOptionPane.showMessageDialog(null, "No se admiten contraseñas vacias");
	        }
		}
		
	}

}
