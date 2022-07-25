package model.gamemode;

import model.unit.Black;
import model.unit.Empty;
import model.unit.Unit;
import model.unit.White;

public class MiniGameMode extends GameMode {

	private static final long serialVersionUID = 1L;

	private static final int tableSize = 4;
	
	/***
	 * Kell a teszeléshez.
	 */
	public GameMode clone() {
		return new MiniGameMode();
	}
	
	/***
	 * Megadja, hogy a játék elején az adott mezõn milyen egység van.
	 */
	@Override
	public Unit getStartingUnitAt(int x, int y) {
		if (y < 1)
			return new White();
		
		if (y > 2)
			return new Black();
		
		return new Empty();
	}
	
	/***
	 * A leszármazott által definiált táblaméretet adja vissza.
	 */
	@Override
	public int getTableSize() {
		return tableSize;
	}
	
	/***
	 * Megadja a leszármazott játékmódnak a nevét.
	 */
	@Override
	public String gameModeName() {
		return "Mini játékmód";
	}
	
	/***
	 * Megadja a leszármazott játékmódnak a leírását.
	 */
	@Override
	public String description() {
		return "<html>"
				+ "4x4-es pálya.<br>"
				+ "A sima egységek csak elõre átlósan tudnak lépni.<br>"
				+ "A királynõ minden irányba tud átlósan lépni"
				+ "</html>";
	}
}
