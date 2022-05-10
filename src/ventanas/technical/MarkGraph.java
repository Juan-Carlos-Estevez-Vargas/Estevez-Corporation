package ventanas.technical;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import modelo.DatabaseConnection;
import ventanas.Login;

public class MarkGraph extends JFrame{

	private static final long serialVersionUID = 1L;
	String user;
    int[] vector_marcas_cantidad = new int[11];
    String[] vector_marcas_nombre = new String[11];
    int hp, lenovo, dell, acer, apple, toshiba, brother, samsung, asus, alienware, xerox;
    
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
                int posicion = 0;

                do {
                    vector_marcas_nombre[posicion] = rs.getString(1);
                    vector_marcas_cantidad[posicion] = rs.getInt(2);

                    if (vector_marcas_nombre[posicion].equalsIgnoreCase("Acer")) {
                        acer = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("HP")) {
                        hp = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Lenovo")) {
                        lenovo = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Dell")) {
                        dell = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Apple")) {
                        apple = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Toshiba")) {
                        toshiba = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Brother")) {
                        brother = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Samsung")) {
                        samsung = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Asus")) {
                        asus = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Alienware")) {
                        alienware = vector_marcas_cantidad[posicion];
                    } else if (vector_marcas_nombre[posicion].equalsIgnoreCase("Xerox")) {
                        xerox = vector_marcas_cantidad[posicion];
                    }
                    posicion++;
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

        int total_marcas = hp + lenovo + dell + acer + apple + toshiba + brother + samsung + asus + alienware + xerox;

        int grados_acer = acer * 360 / total_marcas;
        int grados_hp = hp * 360 / total_marcas;
        int grados_lenovo = lenovo * 360 / total_marcas;
        int grados_dell = dell * 360 / total_marcas;
        int grados_apple = apple * 360 / total_marcas;
        int grados_toshiba = toshiba * 360 / total_marcas;
        int grados_brother = brother * 360 / total_marcas;
        int grados_samsung = samsung * 360 / total_marcas;
        int grados_asus = asus * 360 / total_marcas;
        int grados_alienware = alienware * 360 / total_marcas;
        int grados_xerox = xerox * 360 / total_marcas;

        //Marca acer Morado
        g.setColor(new Color(175, 122, 197));
        g.fillArc(25, 100, 270, 270, 0, grados_acer);
        g.fillRect(310, 120, 20, 20);
        g.drawString(acer + " de Acer", 340, 135);

        //Marca alienware Verde
        g.setColor(new Color(0, 255, 0));
        g.fillArc(25, 100, 270, 270, grados_acer, grados_alienware);
        g.fillRect(310, 150, 20, 20);
        g.drawString(alienware + " de Alienware", 340, 165);

        //Marca apple Verde Agua
        g.setColor(new Color(0, 255, 255));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware, grados_apple);
        g.fillRect(310, 180, 20, 20);
        g.drawString(apple + " de Apple", 340, 195);

        //Marca asus Azul
        g.setColor(new Color(55, 0, 255));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple, grados_asus);
        g.fillRect(310, 210, 20, 20);
        g.drawString(asus + " de Asus", 340, 225);

        //Marca acer Brother
        g.setColor(new Color(255, 255, 255));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus, grados_brother);
        g.fillRect(310, 240, 20, 20);
        g.drawString(brother + " de Brother", 340, 255);

        //Marca dell Amarillo
        g.setColor(new Color(247, 220, 111));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother, grados_dell);
        g.fillRect(310, 270, 20, 20);
        g.drawString(dell + " de Dell", 340, 285);

        //Marca hp Azul Marino
        g.setColor(new Color(21, 42, 160));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother + grados_dell, grados_hp);
        g.fillRect(310, 300, 20, 20);
        g.drawString(hp + " de HP", 340, 315);

        //Marca lenovo Naranja
        g.setColor(new Color(215, 96, 0));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother + grados_dell + grados_hp, grados_lenovo);
        g.fillRect(310, 330, 20, 20);
        g.drawString(hp + " de HP", 340, 345);

        //Marca samsung Rosa
        g.setColor(new Color(215, 96, 140));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother + grados_dell + grados_hp + grados_lenovo, grados_samsung);
        g.fillRect(430, 120, 20, 20);
        g.drawString(samsung + " de Samsung", 460, 135);

        //Marca toshiba Amarillo Canario
        g.setColor(new Color(215, 196, 25));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother + grados_dell + grados_hp + grados_lenovo + grados_samsung, grados_toshiba);
        g.fillRect(430, 150, 20, 20);
        g.drawString(toshiba + " de Toshiba", 460, 165);

        //Marca xerox Azul Celeste
        g.setColor(new Color(93, 173, 226));
        g.fillArc(25, 100, 270, 270, grados_acer + grados_alienware + grados_apple + grados_asus + grados_brother + grados_dell + grados_hp + grados_lenovo + grados_samsung + grados_toshiba, grados_xerox);
        g.fillRect(430, 180, 20, 20);
        g.drawString(xerox + " de Xerox", 460, 195);
    }
}
