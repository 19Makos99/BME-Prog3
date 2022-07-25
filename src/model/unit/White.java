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
	 * Visszaadja, hogy az oszt�ly �res egys�g-e.
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/***
	 * Visszaadja, hogy az oszt�ly feh�r egys�g-e.
	 */
	@Override
	public boolean isWhite() {
		return true;
	}

	/***
	 * Visszaadja, hogy az oszt�ly fekete egys�g-e.
	 */
	@Override
	public boolean isBlack() {
		return false;
	}
	
	/***
	 * Rajzol egy korongot.
	 * Ha az egys�g ki van v�lasztva akkor pirosat egy�bk�nt pedig feh�ret.
	 * Ha kir�lyn� az egys�g akkor rajzol r� egy koron�t.
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
