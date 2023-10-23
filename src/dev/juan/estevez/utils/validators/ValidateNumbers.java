package dev.juan.estevez.utils.validators;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Juan Carlos Estevez Vargas
 */
public class ValidateNumbers extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent event) {
		int key = event.getKeyChar();
		boolean isNumber = key >= 48 && key <= 57;

		if (!isNumber) {
			event.consume();
		}
	}

}
