package model.gamemode;

import model.unit.Black;
import model.unit.Empty;
import model.unit.Unit;
import model.unit.White;

public class BaseGameMode extends GameMode{
	
	private static final long serialVersionUID = 1L;
	
	private static final int tableSize = 8;
	
	/***
	 * Kell a teszeléshez.
	 */
	public GameMode clone() {
		return new BaseGameMode();
	}
	
	/***
	 * Megadja, hogy a játék elején az adott mezõn milyen egység van.
	 */
	@Override
	public Unit getStartingUnitAt(int x, int y) {
		if (isEven(x, y))
			return new Empty();
		
		if (y < 3)
			return new White();
		
		if (y > 4)
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
		return "Alap játékmód";
	}

	/***
	 * Megadja a leszármazott játékmódnak a leírását.
	 */
	@Override
	public String description() {
		return "<html>"
				+ "8x8-es pálya.<br>"
				+ "A sima egységek csak elõre átlósan tudnak lépni.<br>"
				+ "A királynõ minden irányba tud átlósan lépni."
				+ "</html>";
	}
}
