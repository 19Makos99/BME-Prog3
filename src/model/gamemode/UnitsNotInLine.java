package model.gamemode;

public class UnitsNotInLine extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnitsNotInLine(int x1, int y1, int x2, int y2) {
		super("Units are not in line x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
	}
}
