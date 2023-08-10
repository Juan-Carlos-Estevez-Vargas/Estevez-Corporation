package dev.juan.estevez.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
