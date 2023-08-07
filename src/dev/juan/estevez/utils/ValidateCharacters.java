package dev.juan.estevez.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Se encarga de validar que no se ingresen caracteres al campo de texto, y que
 * la longitud no supere cierto tamaño.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class ValidateCharacters extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent ev) {
		int key = ev.getKeyChar();
		boolean mayusculas = key >= 65 && key <= 90;
		boolean minusculas = key >= 97 && key <= 122;
		boolean espacio = key == 32;

		if (!(minusculas || mayusculas || espacio)) {
			ev.consume();
		}
	}
}
