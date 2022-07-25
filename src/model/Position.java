package model;

public class Position {
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/***
	 * Összehasonlító operátor.
	 */
	public boolean equals(Object o) {
		Position p = (Position)o;
		return this.x == p.x && this.y == p.y;
	}
	
	/***
	 * Megadja, hogy az adott pozíció világos mezõn van-e.
	 */
	public Boolean isEven() {
		return (x + y) % 2 == 0;
	}
	
	/***
	 * X getter.
	 */
	public int getX() {
		return x;
	}
	
	/***
	 * Y getter.
	 */
	public int getY() {
		return y;
	}
}
