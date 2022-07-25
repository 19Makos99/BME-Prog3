package model.unit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Empty extends Unit {
	
	private static final long serialVersionUID = 1L;

	/***
	 * Visszaadja, hogy az osztály üres egység-e.
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}

	/***
	 * Visszaadja, hogy az osztály fehér egység-e.
	 */
	@Override
	public boolean isWhite() {
		return false;
	}

	/***
	 * Visszaadja, hogy az osztály fekete egység-e.
	 */
	@Override
	public boolean isBlack() {
		return false;
	}

	@Override
	public void draw(Component c, Graphics g, int x, int y) {
		if (this.isMoveOption()) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(Color.yellow);
		    g2.drawOval(x, y, 50, 50);
		    g2.dispose();
		}
	}
}
