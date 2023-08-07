package dev.juan.estevez.views.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.utils.DatabaseConnection;
import panel.utilities.Login;

/**
 * Gráfica de torta con la cantidad de equipos registrados en el sistema.
 *
 * @author
 *
 */
public class MarkGraph extends JFrame{

	/**
	 * Variables.
	 */
	private static final long serialVersionUID = 1L;
    private int[] vectorCantMarks = new int[11];
    private int hp, lenovo, dell, acer, apple, toshiba, brother, samsung, asus, alienware, xerox;
    private String[] vectorNameMarks = new String[11];
    private String user;

    /**
     * Constructor de clase.
     */
    public MarkGraph() {
    	user = Login.user;
        this.setSize(550, 460);
        this.setResizable(false);
        this.setTitle("Técnico - Sesión de " + user);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            Connection cn = (Connection) DatabaseConnection.conectar();
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
                    "SELECT marca, COUNT(marca) AS Marcas FROM equipos GROUP BY marca");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int position = 0;

                do {
                    vectorNameMarks[position] = rs.getString(1);
                    vectorCantMarks[position] = rs.getInt(2);

                    if (vectorNameMarks[position].equalsIgnoreCase("Acer")) {
                        acer = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("HP")) {
                        hp = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Lenovo")) {
                        lenovo = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Dell")) {
                        dell = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Apple")) {
                        apple = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Toshiba")) {
                        toshiba = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Brother")) {
                        brother = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Samsung")) {
                        samsung = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Asus")) {
                        asus = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Alienware")) {
                        alienware = vectorCantMarks[position];
                    } else if (vectorNameMarks[position].equalsIgnoreCase("Xerox")) {
                        xerox = vectorCantMarks[position];
                    }
                    position++;
                } while (rs.next());
            }

        } catch (SQLException e) {
            System.err.println("Error en la consulta " + e);
            JOptionPane.showMessageDialog(null, "Error al consultar datos. Contacte al Administrador.");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int totalMarks = hp + lenovo + dell + acer + apple + toshiba + brother + samsung + asus + alienware + xerox;

        int degreesAcer = acer * 360 / totalMarks;
        int degreesHp = hp * 360 / totalMarks;
        int degreesLenovo = lenovo * 360 / totalMarks;
        int degreesDell = dell * 360 / totalMarks;
        int degreesApple = apple * 360 / totalMarks;
        int degreesToshiba = toshiba * 360 / totalMarks;
        int degreesBrother = brother * 360 / totalMarks;
        int degreesSamsung = samsung * 360 / totalMarks;
        int degreesAsus = asus * 360 / totalMarks;
        int degreesAlienware = alienware * 360 / totalMarks;
        int degreesXerox = xerox * 360 / totalMarks;

        g.setColor(new Color(46, 59, 104));
        g.fillRect(0, 0, 550, 460);

        /**
         * Marca acer Morado
         */
        g.setColor(new Color(175, 122, 197));
        g.fillArc(25, 100, 270, 270, 0, degreesAcer);
        g.fillRect(310, 120, 20, 20);
        g.drawString(acer + " de Acer", 340, 135);

        /**
         * Marca alienware Verde
         */
        g.setColor(new Color(0, 255, 0));
        g.fillArc(25, 100, 270, 270, degreesAcer, degreesAlienware);
        g.fillRect(310, 150, 20, 20);
        g.drawString(alienware + " de Alienware", 340, 165);

        /**
         * Marca apple Verde Agua
         */
        g.setColor(new Color(0, 255, 255));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware, degreesApple);
        g.fillRect(310, 180, 20, 20);
        g.drawString(apple + " de Apple", 340, 195);

        /**
         * Marca asus Azul
         */
        g.setColor(new Color(0, 0, 0));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple, degreesAsus);
        g.fillRect(310, 210, 20, 20);
        g.drawString(asus + " de Asus", 340, 225);

        /**
         * Marca acer Brother
         */
        g.setColor(new Color(255, 255, 255));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus, degreesBrother);
        g.fillRect(310, 240, 20, 20);
        g.drawString(brother + " de Brother", 340, 255);

        /**
         * Marca dell Amarillo
         */
        g.setColor(new Color(247, 220, 111));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother, degreesDell);
        g.fillRect(310, 270, 20, 20);
        g.drawString(dell + " de Dell", 340, 285);

        /**
         * Marca hp Azul Marino
         */
        g.setColor(new Color(8, 85, 224));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother + degreesDell, degreesHp);
        g.fillRect(310, 300, 20, 20);
        g.drawString(hp + " de HP", 340, 315);

        /**
         * Marca lenovo Naranja
         */
        g.setColor(new Color(255, 0, 0));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother + degreesDell + degreesHp, degreesLenovo);
        g.fillRect(310, 330, 20, 20);
        g.drawString(lenovo + " de Lenovo", 340, 345);

        /**
         * Marca samsung Rosa
         */
        g.setColor(new Color(215, 96, 140));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother + degreesDell + degreesHp + degreesLenovo, degreesSamsung);
        g.fillRect(430, 120, 20, 20);
        g.drawString(samsung + " de Samsung", 460, 135);

        /**
         * Marca toshiba Amarillo Canario
         */
        g.setColor(new Color(215, 196, 25));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother + degreesDell + degreesHp + degreesLenovo + degreesSamsung, degreesToshiba);
        g.fillRect(430, 150, 20, 20);
        g.drawString(toshiba + " de Toshiba", 460, 165);

        /**
         * Marca xerox Azul Celeste
         */
        g.setColor(new Color(93, 173, 226));
        g.fillArc(25, 100, 270, 270, degreesAcer + degreesAlienware + degreesApple + degreesAsus + degreesBrother + degreesDell + degreesHp + degreesLenovo + degreesSamsung + degreesToshiba, degreesXerox);
        g.fillRect(430, 180, 20, 20);
        g.drawString(xerox + " de Xerox", 460, 195);

        g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Gráfica de marcas registradas", 150, 68);
    }
}
