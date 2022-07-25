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
	 * A j�t�kt�bla felt�lt�se a lesz�rmazott �ltal megadott szab�ly szerint.
	 */
	public GameMode() {
		table = new Unit[getTableSize()][getTableSize()];
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				table[y][x] = getStartingUnitAt(x, y);
	}
	
	/***
	 * Kell a teszel�shez.
	 * @return Egy m�solat a t�bl�r�l.
	 */
	public abstract GameMode clone();
	
	/***
	 * @return Megadja, hogy az adott kordin�t�kon l�v� mez� vil�gos szin�-e.
	 */
	public boolean isEven(int x, int y) {
		return (x + y) % 2 == 0;
	}
	
	/***
	 * @return A lesz�rmazott �ltal defini�lt t�blam�retet adja vissza.
	 */
	public abstract int getTableSize();
	
	/***
	 * @return Megadja, hogy a j�t�k elej�n az adott mez�n milyen egys�g van.
	 */
	public abstract Unit getStartingUnitAt(int x, int y);
	
	/***
	 * @return  Megadja a lesz�rmazott j�t�km�dnak a nev�t.
	 */
	public abstract String gameModeName();
	
	/***
	 * @return Megadja a lesz�rmazott j�t�km�dnak a le�r�s�t.
	 */
	public abstract String description();
	
	/***
	 * Test miatt public.
	 * @return Megadja, hogy a t�bla adott mez�j�n milyen egys�g van.
	 */
	public Unit getUnitAt(int x, int y) {
		if (!isValidPos(x, y))
			throw new ArrayIndexOutOfBoundsException();
		
		return table[y][x];
	}
	
	/***
	 * T�rli a l�p�si lehet�s�g jel�l�ket a t�bl�r�l.
	 */
	public void clearMoveOptions() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				this.getUnitAt(x, y).setMoveOption(false);
	}
	
	/***
	 * Elt�vol�tja a kiv�laszotott jel�l�st a egys�gekr�l.
	 */
	public void clearSelected() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				this.getUnitAt(x, y).setSelected(false);
	}
	
	/***
	 * A megadott mez�re elhelyezi a megadott egys�get.
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
	 * Visszaadja a kiv�lasztott egys�get, ha van.
	 */
	public Position getSelectedUnitPosition() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (table[y][x].isSelected())
					return new Position(x, y);
		return null;
	}
	
	/***
	 * Be�ll�tja az adott egys�get kiv�lasztottnak.
	 * @throws Ha m�r van kiv�lasztott egys�g.
	 */
	public void setSelectedUnit(int x, int y) throws UnitAlreadySelected {
		if (getSelectedUnitPosition() != null)
			throw new UnitAlreadySelected();
		
		table[y][x].setSelected(true);
	}

	/***
	 * A kiv�lasztott egys�get mozgatja a megadott mez�re.
	 * Ha a k�t mez� k�z�tt van egy egys�g akkor azt let�ti.
	 * @throws NoSelectedUnit ha m�r van kiv�lasztott egys�g.
	 * @throws UnitsNotInLine ha a kiv�lasztott egys�g �s a c�l mez� nincs egy vonalban.
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
	 * @return Visszaadja a fekete j�t�kos �ltal tehet� �sszes l�p�st.
	 */
	public List<Position> getAllBlackMoves() {
		Set<Position> result = new HashSet<Position>();
		
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				result.addAll(getPossibleBlackMovesAt(x, y));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Visszaadja a feh�r j�t�kos �ltal tehet� �sszes l�p�st.
	 */
	public List<Position> getAllWhiteMoves() {
		Set<Position> result = new HashSet<Position>();
		
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				result.addAll(getPossibleWhiteMovesAt(x, y));
		
		return new ArrayList<Position>(result);
	}
	
	/***
	 * @return Megadja, hogy a fekete j�t�kos tud-e l�pni.
	 */
	public boolean canBlackMove() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (getPossibleBlackMovesAt(x, y).size() > 0)
					return true;
		
		return false;
	}
	
	/***
	 * @return Megadja, hogy a feh�r j�t�kos tud-e l�pni.
	 */
	public boolean canWhiteMove() {
		for (int y = 0; y < getTableSize(); ++y)
			for (int x = 0; x < getTableSize(); ++x)
				if (getPossibleWhiteMovesAt(x, y).size() > 0)
					return true;
		
		return false;
	}
	
	/***
	 * @return Visszaadja az adott mez�r�l tehet� �sszes l�p�st.
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
	 * Be�ll�tja a megadott mez�ket, l�phet� t�pus� mez�kk�.
	 */
	public void markPossibleMoves(List<Position> moves) {
		if (moves == null)
			return;
		
		moves.forEach(p -> {
			this.getUnitAt(p.getX(), p.getY()).setMoveOption(true);
		});
	}
	
	/***
	 * @return Visszaadja a fekete j�t�kos �ltal tehet� �sszes l�p�st az adott mez�n.
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
	 * @return Visszaadja a feh�r j�t�kos �ltal tehet� �sszes l�p�st az adott mez�n.
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
	 * @return Megadja, hogy az adott kordin�t�n van-e egys�g.
	 */
	public boolean isEmpty(int x, int y) {
		return isValidPos(x, y) && table[y][x].isEmpty();
	}
	
	/***
	 * @return Megadja, hogy az adott kordin�t�n fekete egys�g van-e.
	 */
	public boolean isBlack(int x, int y) {
		return isValidPos(x, y) && table[y][x].isBlack();
	}
	
	/***
	 * @return Megadja, hogy az adott kordin�t�n feh�r egys�g van-e.
	 */
	public boolean isWhite(int x, int y) {
		return isValidPos(x, y) && table[y][x].isWhite();
	}
	
	/***
	 * @return Megadja, hogy az adott kordin�ta rajta van-e a t�bl�n.
	 */
	public boolean isValidPos(int x, int y) {
		return 0 <= x && getTableSize() > x && 0 <= y && getTableSize() > y;
	}
	
	/***
	 * @return Megadja, hogy �ppen a feh�r j�t�kos k�vetkezik-e.
	 */
	public boolean isWhitesTurn() {
		return whitesTurn;
	}
}
