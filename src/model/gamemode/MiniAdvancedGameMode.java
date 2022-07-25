package model.gamemode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Position;
import model.unit.Black;
import model.unit.Empty;
import model.unit.Unit;
import model.unit.White;

public class MiniAdvancedGameMode extends GameMode {
	
	private static final long serialVersionUID = 1L;
	
	private static final int tableSize = 4;
	
	/***
	 * Kell a teszeléshez.
	 */
	public GameMode clone() {
		return new MiniAdvancedGameMode();
	}
	
	@Override
	public void moveTo(int x, int y) throws NoSelectedUnit, UnitsNotInLine {
		Position selectedUnitPos = getSelectedUnitPosition();
		if (selectedUnitPos == null)
			throw new NoSelectedUnit();
		
		if (Math.abs(selectedUnitPos.getY() - y) != Math.abs(selectedUnitPos.getX() - x)
				&& Math.abs(selectedUnitPos.getY()) != Math.abs(y)
				&& Math.abs(selectedUnitPos.getX()) != Math.abs(x))
			throw new UnitsNotInLine(x, y, selectedUnitPos.getX(), selectedUnitPos.getY());
		
		Unit selectedUnit = this.getUnitAt(selectedUnitPos.getX(), selectedUnitPos.getY());
		selectedUnit.setSelected(false);
		
		if (Math.abs(selectedUnitPos.getX() - x) == 2 || Math.abs(selectedUnitPos.getY() - y) == 2) {
			whitesTurn = !whitesTurn;
			this.setUnitAt((selectedUnitPos.getX() + x) / 2, (selectedUnitPos.getY() + y) / 2, new Empty());
		}
			
		this.setUnitAt(selectedUnitPos.getX(), selectedUnitPos.getY(), new Empty());
		this.setUnitAt(x, y, selectedUnit);
		
		whitesTurn = !whitesTurn;
	}
	
	/***
	 * Visszaadja a fekete játékos által tehetõ összes lépést az adott mezõn.
	 */
	@Override
	public List<Position> getPossibleBlackMovesAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		Unit current = table[y][x];
		
		if (!current.isBlack())
			return new ArrayList<Position>();
		
		Set<Position> result = new HashSet<Position>();
		
		// 1 hosszú lépés
		// 1. sor
		if (isEmpty(x-1,y-1))											result.add(new Position(x-1,y-1));
		if (isEmpty(x+0,y-1) && current.isQueen())						result.add(new Position(x+0,y-1));
		if (isEmpty(x+1,y-1))											result.add(new Position(x+1,y-1));
		
		// 2. sor
		if (isEmpty(x+1,y+0) && current.isQueen())						result.add(new Position(x+1,y+0));
		if (isEmpty(x-1,y+0) && current.isQueen())						result.add(new Position(x-1,y+0));
		
		// 3. sor
		if (isEmpty(x-1,y+1) && current.isQueen())						result.add(new Position(x-1,y+1));
		if (isEmpty(x+0,y+1) && current.isQueen())						result.add(new Position(x+0,y+1));
		if (isEmpty(x+1,y+1) && current.isQueen())						result.add(new Position(x+1,y+1));
		
		// 2 hosszú lépés
		// 1. sor
		if (isWhite(x-1,y-1) && isEmpty(x-2,y-2))						result.add(new Position(x-2,y-2));
		if (isWhite(x+0,y-1) && isEmpty(x+0,y-2) && current.isQueen())	result.add(new Position(x+0,y-2));
		if (isWhite(x+1,y-1) && isEmpty(x+2,y-2))						result.add(new Position(x+2,y-2));
		
		// 2. sor
		if (isWhite(x-1,y+0) && isEmpty(x-2,y) && current.isQueen())	result.add(new Position(x-2,y+0));
		if (isWhite(x+1,y+0) && isEmpty(x+2,y) && current.isQueen())	result.add(new Position(x+2,y+0));
		
		// 3. sor
		if (isWhite(x-1,y+1) && isEmpty(x-2,y+2) && current.isQueen())	result.add(new Position(x-2,y+2));
		if (isWhite(x+0,y+1) && isEmpty(x+0,y+2) && current.isQueen())	result.add(new Position(x+0,y+2));
		if (isWhite(x+1,y+1) && isEmpty(x+2,y+2) && current.isQueen())	result.add(new Position(x+2,y+2));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * Visszaadja a fehér játékos által tehetõ összes lépést az adott mezõn.
	 */
	@Override
	public List<Position> getPossibleWhiteMovesAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		Unit current = table[y][x];
		
		if (!current.isWhite())
			return new ArrayList<Position>();
		
		Set<Position> result = new HashSet<Position>();
		
		// 1 hosszú lépés
		// 1. sor
		if (isEmpty(x-1,y+1))											result.add(new Position(x-1,y+1));
		if (isEmpty(x+0,y+1) && current.isQueen())						result.add(new Position(x+0,y+1));
		if (isEmpty(x+1,y+1))											result.add(new Position(x+1,y+1));
		
		// 2. sor
		if (isEmpty(x-1,y+0) && current.isQueen())						result.add(new Position(x-1,y+0));
		if (isEmpty(x+1,y+0) && current.isQueen())						result.add(new Position(x+1,y+0));
		
		//3. sor
		if (isEmpty(x-1,y-1) && current.isQueen())						result.add(new Position(x-1,y-1));
		if (isEmpty(x+0,y-1) && current.isQueen())						result.add(new Position(x+0,y-1));
		if (isEmpty(x+1,y-1) && current.isQueen())						result.add(new Position(x+1,y-1));
		
		// 2 hosszú lépés
		// 1. sor
		if (isBlack(x-1,y+1) && isEmpty(x-2,y+2))						result.add(new Position(x-2,y+2));
		if (isBlack(x+0,y+1) && isEmpty(x+0,y+2) && current.isQueen())	result.add(new Position(x+0,y+2));
		if (isBlack(x+1,y+1) && isEmpty(x+2,y+2))						result.add(new Position(x+2,y+2));
		
		// 2. sor
		if (isBlack(x-1,y+0) && isEmpty(x-2,y+0) && current.isQueen())	result.add(new Position(x-2,y+0));
		if (isBlack(x+1,y+0) && isEmpty(x+2,y+0) && current.isQueen())	result.add(new Position(x+2,y+0));
		
		//3. sor
		if (isBlack(x-1,y-1) && isEmpty(x-2,y-2) && current.isQueen())	result.add(new Position(x-2,y-2));
		if (isBlack(x+0,y-1) && isEmpty(x+0,y-2) && current.isQueen())	result.add(new Position(x+0,y-2));
		if (isBlack(x+1,y-1) && isEmpty(x+2,y-2) && current.isQueen())	result.add(new Position(x+2,y-2));
		
		return new ArrayList<Position>(result);
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

	/*
	 * Megadja a leszármazott játékmódnak a nevét.
	 */
	@Override
	public String gameModeName() {
		return "Mini haladó játékmód";
	}

	/*
	 * Megadja a leszármazott játékmódnak a leírását.
	 */
	@Override
	public String description() {
		return "<html>"
				+ "4x4-es pálya.<br>"
				+ "1 sor egység minden szinen<br>"
				+ "A sima egységek csak elõre átlósan tudnak lépni.<br>"
				+ "A királynõ minden irányba tud lépni<br>"
				+ "Ellenfél leütése után újra az aktuális játékos következik."
				+ "</html>";
	}
}
