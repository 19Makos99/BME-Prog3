package model.unit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class White extends Unit {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "White";
	}
	
	/***
	 * Visszaadja, hogy az osztály üres egység-e.
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/***
	 * Visszaadja, hogy az osztály fehér egység-e.
	 */
	@Override
	public boolean isWhite() {
		return true;
	}

	/***
	 * Visszaadja, hogy az osztály fekete egység-e.
	 */
	@Override
	public boolean isBlack() {
		return false;
	}
	
	/***
	 * Rajzol egy korongot.
	 * Ha az egység ki van választva akkor pirosat egyébként pedig fehéret.
	 * Ha királynõ az egység akkor rajzol rá egy koronát.
	 */
	@Override
	public void draw(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(this.isSelected() ? Color.RED : Color.WHITE);
	    g2.fillOval(x, y, 50, 50);
	    if (this.isQueen()) {
	    	g2.setColor(Color.CYAN);
	    	g2.translate(c.getWidth() / 2, c.getHeight() / 2);
	    	g2.drawPolygon(new int[]{-10, -10, -15, -5, 0, 5, 15, 10, 10}, new int[] {10, 0, -10, -5, -15, -5, -10, 0, 10}, 9);
	    }
	    g2.dispose();
	}
}
