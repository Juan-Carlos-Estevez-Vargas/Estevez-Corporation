package dev.juan.estevez.utils.validators;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Juan Carlos Estevez Vargas
 */
public class ValidateCharacters extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent event) {
		int key = event.getKeyChar();
		boolean isLetter = (key >= 'a' && key <= 'z') || (key >= 'A' && key <= 'Z');
		boolean isSpace = key == ' ';

		if (!isLetter && !isSpace) {
			event.consume();
		}
	}
}
