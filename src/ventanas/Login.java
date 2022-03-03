package ventanas;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public final class Login extends JFrame {

    private JPanel container;
    private JLabel jlLogo, jlUser, jlPassword, jlForgot, jlError;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnEye;
    private JSeparator separator;

    public Login() {
        this.setSize(350, 600);
        this.setTitle("Estevez Corporation");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initComponents();
    }

    public void initComponents() {
        this.container = new JPanel();
        this.container.setBackground(new Color(46, 59, 104));
        this.container.setLayout(null);
        this.setContentPane(container);
        
        this.jlLogo = new JLabel();
        this.jlLogo.setBounds(70, 50, 200, 150);
        this.jlLogo.setIcon(new ImageIcon("src/img/logo.png"));
        this.jlLogo.setHorizontalAlignment(JLabel.CENTER);
        this.container.add(jlLogo);

        this.jlUser = new JLabel("Usuario:");
        this.jlUser.setBounds(45, 250, 210, 30);
        this.jlUser.setForeground(new Color(192, 192, 192));
        this.jlUser.setFont(new Font("serif", Font.BOLD, 20));
        this.jlUser.setHorizontalAlignment(JLabel.LEFT);
        this.container.add(jlUser);

        this.txtUser = new JTextField();
        this.txtUser.setBounds(45, 285, 250, 30);
        this.txtUser.setBackground(new Color(127, 140, 141));
        this.txtUser.setFont(new Font("serif", Font.BOLD, 22));
        this.txtUser.setHorizontalAlignment(JLabel.LEFT);
        this.txtUser.setForeground(Color.WHITE);
        this.container.add(txtUser);

        this.jlPassword = new JLabel("Password:");
        this.jlPassword.setBounds(45, 320, 210, 30);
        this.jlPassword.setForeground(new Color(192, 192, 192));
        this.jlPassword.setFont(new Font("serif", Font.BOLD, 20));
        this.jlPassword.setHorizontalAlignment(JLabel.LEFT);
        this.container.add(jlPassword);

        this.txtPassword = new JPasswordField();
        this.txtPassword.setBounds(45, 355, 250, 30);
        this.txtPassword.setBackground(new Color(127, 140, 141));
        this.txtPassword.setFont(new Font("serif", Font.BOLD, 22));
        this.txtPassword.setForeground(Color.WHITE);
        this.container.add(txtPassword);
        
        this.jlError = new JLabel();
        this.jlError.setBounds(45, 390, 250, 25);
        this.jlError.setForeground(new Color(192, 192, 192));
        this.jlError.setFont(new Font("serif", Font.BOLD, 22));
        this.jlError.setHorizontalAlignment(JLabel.CENTER);
        this.container.add(jlError);
        
        this.btnLogin = new JButton("Iniciar Sesión");
        this.btnLogin.setBounds(45, 435, 250, 45);
        this.btnLogin.setFont(new Font("serif", Font.BOLD, 22));
        this.btnLogin.setBackground(new Color(8, 85, 224));
        this.btnLogin.setForeground(Color.WHITE);
        this.btnLogin.setHorizontalAlignment(JButton.CENTER);
        this.container.add(btnLogin);
        
        this.separator = new JSeparator();
        this.separator.setBackground(new Color(192, 192, 192));
        this.separator.setBounds(45, 510, 250, 5);
        this.container.add(separator);
        
        this.jlForgot = new JLabel("¿Olvidó su contraseña?");
        this.jlForgot.setBounds(45, 515, 250, 35);
        this.jlForgot.setForeground(new Color(192, 192, 192));
        this.jlForgot.setFont(new Font("serif", Font.BOLD, 18));
        this.jlForgot.setHorizontalAlignment(JLabel.CENTER);
        this.container.add(jlForgot);
    }
}
