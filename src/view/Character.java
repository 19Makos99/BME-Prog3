package view;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import model.unit.Unit;

/***
 * A gombokon lévõ egységeket jelképezi.
 */
public class Character implements Icon {
	
	Unit unit;
	
	public Character(Unit unit) {
		super();
		this.unit = unit;
	}

	/***
	 * Rárajzolja az ikonra az adott egység szimbólumát.
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		unit.draw(c, g, x, y);
	}

	@Override
	public int getIconWidth() {
		return 50;
	}

	@Override
	public int getIconHeight() {
		return 50;
	}
}
