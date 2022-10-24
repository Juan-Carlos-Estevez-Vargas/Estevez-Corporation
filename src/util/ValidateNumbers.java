package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ValidateNumbers extends KeyAdapter {
	@Override
	public void keyTyped(KeyEvent ev) {
		int key = ev.getKeyChar();
		boolean numeros = key >= 48 && key <= 57;

		if (!numeros) {
			ev.consume();
		}
	}
}
