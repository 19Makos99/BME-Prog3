package model.unit;

import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Unit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean queen = false;
	private boolean selected = false;
	private boolean moveOption = false;
	
	public boolean isQueen() {
		return queen;
	}
	
	public void setQueen(boolean b) {
		queen = b;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}
	
	public boolean isMoveOption() {
		return moveOption;
	}
	
	public void setMoveOption(boolean b) {
		moveOption = b;
	}
	
	/***
	 * Visszaadja, hogy az osztály üres egység-e.
	 */
	public abstract boolean isEmpty();
	
	/***
	 * Visszaadja, hogy az osztály fehér egység-e.
	 */
	public abstract boolean isWhite();
	
	/***
	 * Visszaadja, hogy az osztály fekete egység-e.
	 */
	public abstract boolean isBlack();

	public abstract void draw(Component c, Graphics g, int x, int y);
}
