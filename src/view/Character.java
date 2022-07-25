package view;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import model.unit.Unit;

/***
 * A gombokon l�v� egys�geket jelk�pezi.
 */
public class Character implements Icon {
	
	Unit unit;
	
	public Character(Unit unit) {
		super();
		this.unit = unit;
	}

	/***
	 * R�rajzolja az ikonra az adott egys�g szimb�lum�t.
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
