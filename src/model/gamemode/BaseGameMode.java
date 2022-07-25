package model.gamemode;

import model.unit.Black;
import model.unit.Empty;
import model.unit.Unit;
import model.unit.White;

public class BaseGameMode extends GameMode{
	
	private static final long serialVersionUID = 1L;
	
	private static final int tableSize = 8;
	
	/***
	 * Kell a teszel�shez.
	 */
	public GameMode clone() {
		return new BaseGameMode();
	}
	
	/***
	 * Megadja, hogy a j�t�k elej�n az adott mez�n milyen egys�g van.
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
	 * A lesz�rmazott �ltal defini�lt t�blam�retet adja vissza.
	 */
	@Override
	public int getTableSize() {
		return tableSize;
	}

	/***
	 * Megadja a lesz�rmazott j�t�km�dnak a nev�t.
	 */
	@Override
	public String gameModeName() {
		return "Alap j�t�km�d";
	}

	/***
	 * Megadja a lesz�rmazott j�t�km�dnak a le�r�s�t.
	 */
	@Override
	public String description() {
		return "<html>"
				+ "8x8-es p�lya.<br>"
				+ "A sima egys�gek csak el�re �tl�san tudnak l�pni.<br>"
				+ "A kir�lyn� minden ir�nyba tud �tl�san l�pni."
				+ "</html>";
	}
}
