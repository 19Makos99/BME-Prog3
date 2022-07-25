package model.gamemode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Position;
import model.unit.Empty;
import model.unit.Unit;

public abstract class GameMode implements Serializable {
	private static final long serialVersionUID = 5564579062492882833L;
	
	protected Unit[][] table;
	protected boolean whitesTurn = true;
	
	/***
	 * A játéktábla feltöltése a leszármazott által megadott szabály szerint.
	 */
	public GameMode() {
		table = new Unit[getTableSize()][getTableSize()];
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				table[y][x] = getStartingUnitAt(x, y);
	}
	
	/***
	 * Kell a teszeléshez.
	 * @return Egy másolat a tábláról.
	 */
	public abstract GameMode clone();
	
	/***
	 * @return Megadja, hogy az adott kordinátákon lévõ mezõ világos szinû-e.
	 */
	public boolean isEven(int x, int y) {
		return (x + y) % 2 == 0;
	}
	
	/***
	 * @return A leszármazott által definiált táblaméretet adja vissza.
	 */
	public abstract int getTableSize();
	
	/***
	 * @return Megadja, hogy a játék elején az adott mezõn milyen egység van.
	 */
	public abstract Unit getStartingUnitAt(int x, int y);
	
	/***
	 * @return  Megadja a leszármazott játékmódnak a nevét.
	 */
	public abstract String gameModeName();
	
	/***
	 * @return Megadja a leszármazott játékmódnak a leírását.
	 */
	public abstract String description();
	
	/***
	 * Test miatt public.
	 * @return Megadja, hogy a tábla adott mezõjén milyen egység van.
	 */
	public Unit getUnitAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		return table[y][x];
	}
	
	/***
	 * Törli a lépési lehetõség jelölõket a tábláról.
	 */
	public void clearMoveOptions() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				this.getUnitAt(x, y).setMoveOption(false);
	}
	
	/***
	 * Eltávolítja a kiválaszotott jelölést a egységekrõl.
	 */
	public void clearSelected() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				this.getUnitAt(x, y).setSelected(false);
	}
	
	/***
	 * A megadott mezõre elhelyezi a megadott egységet.
	 * Test miatt public
	 */
	public void setUnitAt(int x, int y, Unit u) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		if (y == 0 && u.isBlack())
			u.setQueen(true);
		
		if (y == getTableSize() - 1 && u.isWhite())
			u.setQueen(true);
		
		table[y][x] = u;
	}
	
	/***
	 * Visszaadja a kiválasztott egységet, ha van.
	 */
	public Position getSelectedUnitPosition() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (table[y][x].isSelected())
					return new Position(x, y);
		return null;
	}
	
	/***
	 * Beállítja az adott egységet kiválasztottnak.
	 * @throws Ha már van kiválasztott egység.
	 */
	public void setSelectedUnit(int x, int y) throws UnitAlreadySelected {
		if (getSelectedUnitPosition() != null)
			throw new UnitAlreadySelected();
		
		table[y][x].setSelected(true);
	}

	/***
	 * A kiválasztott egységet mozgatja a megadott mezõre.
	 * Ha a két mezõ között van egy egység akkor azt letüti.
	 * @throws NoSelectedUnit ha már van kiválasztott egység.
	 * @throws UnitsNotInLine ha a kiválasztott egység és a cél mezõ nincs egy vonalban.
	 */
	public void moveTo(int x, int y) throws NoSelectedUnit, UnitsNotInLine {
		Position selectedUnitPos = getSelectedUnitPosition();
		if (selectedUnitPos == null)
			throw new NoSelectedUnit();
		
		if (Math.abs(selectedUnitPos.getY() - y) != Math.abs(selectedUnitPos.getX() - x))
			throw new UnitsNotInLine(x, y, selectedUnitPos.getX(), selectedUnitPos.getY());
		
		Unit selectedUnit = this.getUnitAt(selectedUnitPos.getX(), selectedUnitPos.getY());
		selectedUnit.setSelected(false);
		
		if (Math.abs(selectedUnitPos.getX() - x) == 2)
			this.setUnitAt((selectedUnitPos.getX() + x) / 2, (selectedUnitPos.getY() + y) / 2, new Empty());
		
		this.setUnitAt(selectedUnitPos.getX(), selectedUnitPos.getY(), new Empty());
		this.setUnitAt(x, y, selectedUnit);
		
		whitesTurn = !whitesTurn;
	}
	
	/***
	 * @return Visszaadja a fekete játékos által tehetõ összes lépést.
	 */
	public List<Position> getAllBlackMoves() {
		Set<Position> result = new HashSet<Position>();
		
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				result.addAll(getPossibleBlackMovesAt(x, y));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Visszaadja a fehér játékos által tehetõ összes lépést.
	 */
	public List<Position> getAllWhiteMoves() {
		Set<Position> result = new HashSet<Position>();
		
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				result.addAll(getPossibleWhiteMovesAt(x, y));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Megadja, hogy a fekete játékos tud-e lépni.
	 */
	public boolean canBlackMove() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (getPossibleBlackMovesAt(x, y).size() > 0)
					return true;
		
		return false;
	}
	
	/***
	 * @return Megadja, hogy a fehér játékos tud-e lépni.
	 */
	public boolean canWhiteMove() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (getPossibleWhiteMovesAt(x, y).size() > 0)
					return true;
		
		return false;
	}
	
	/***
	 * @return Visszaadja az adott mezõrõl tehetõ összes lépést.
	 */
	public List<Position> getPossibleMovesAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		Unit current = table[y][x];
		
		if (current.isBlack())
			return getPossibleBlackMovesAt(x, y);
		if (current.isWhite())
			return getPossibleWhiteMovesAt(x, y);
		
		return null;
	}
	
	/***
	 * Beállítja a megadott mezõket, léphetõ típusú mezõkké.
	 */
	public void markPossibleMoves(List<Position> moves) {
		if (moves == null)
			return;
		
		moves.forEach(p -> {
			this.getUnitAt(p.getX(), p.getY()).setMoveOption(true);
		});
	}
	
	/***
	 * @return Visszaadja a fekete játékos által tehetõ összes lépést az adott mezõn.
	 */
	public List<Position> getPossibleBlackMovesAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		Unit current = table[y][x];
		
		if (!current.isBlack())
			return new ArrayList<Position>();
		
		Set<Position> result = new HashSet<Position>();
		
		if (isEmpty(x-1,y-1))											result.add(new Position(x-1,y-1));
		if (isEmpty(x+1,y-1))											result.add(new Position(x+1,y-1));
		if (isEmpty(x-1,y+1) && current.isQueen())						result.add(new Position(x-1,y+1));
		if (isEmpty(x+1,y+1) && current.isQueen())						result.add(new Position(x+1,y+1));
		
		if (isWhite(x-1,y-1) && isEmpty(x-2,y-2))						result.add(new Position(x-2,y-2));
		if (isWhite(x+1,y-1) && isEmpty(x+2,y-2))						result.add(new Position(x+2,y-2));
		if (isWhite(x-1,y+1) && isEmpty(x-2,y+2) && current.isQueen())	result.add(new Position(x-2,y+2));
		if (isWhite(x+1,y+1) && isEmpty(x+2,y+2) && current.isQueen())	result.add(new Position(x+2,y+2));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Visszaadja a fehér játékos által tehetõ összes lépést az adott mezõn.
	 */
	public List<Position> getPossibleWhiteMovesAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		Unit current = table[y][x];
		
		if (!current.isWhite())
			return new ArrayList<Position>();
		
		Set<Position> result = new HashSet<Position>();
		
		if (isEmpty(x-1,y+1))											result.add(new Position(x-1,y+1));
		if (isEmpty(x+1,y+1))											result.add(new Position(x+1,y+1));
		if (isEmpty(x-1,y-1) && current.isQueen())						result.add(new Position(x-1,y-1));
		if (isEmpty(x+1,y-1) && current.isQueen())						result.add(new Position(x+1,y-1));
		
		if (isBlack(x-1,y+1) && isEmpty(x-2,y+2))						result.add(new Position(x-2,y+2));
		if (isBlack(x+1,y+1) && isEmpty(x+2,y+2))						result.add(new Position(x+2,y+2));
		if (isBlack(x-1,y-1) && isEmpty(x-2,y-2) && current.isQueen())	result.add(new Position(x-2,y-2));
		if (isBlack(x+1,y-1) && isEmpty(x+2,y-2) && current.isQueen())	result.add(new Position(x+2,y-2));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Megadja, hogy az adott kordinátán van-e egység.
	 */
	public boolean isEmpty(int x, int y) {
		return isValidPos(x, y) && table[y][x].isEmpty();
	}
	
	/***
	 * @return Megadja, hogy az adott kordinátán fekete egység van-e.
	 */
	public boolean isBlack(int x, int y) {
		return isValidPos(x, y) && table[y][x].isBlack();
	}
	
	/***
	 * @return Megadja, hogy az adott kordinátán fehér egység van-e.
	 */
	public boolean isWhite(int x, int y) {
		return isValidPos(x, y) && table[y][x].isWhite();
	}
	
	/***
	 * @return Megadja, hogy az adott kordináta rajta van-e a táblán.
	 */
	public boolean isValidPos(int x, int y) {
		return 0 <= x && getTableSize() > x && 0 <= y && getTableSize() > y;
	}
	
	/***
	 * @return Megadja, hogy éppen a fehér játékos következik-e.
	 */
	public boolean isWhitesTurn() {
		return whitesTurn;
	}
}
