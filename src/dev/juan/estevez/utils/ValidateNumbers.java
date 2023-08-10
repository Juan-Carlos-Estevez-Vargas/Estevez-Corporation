package dev.juan.estevez.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
