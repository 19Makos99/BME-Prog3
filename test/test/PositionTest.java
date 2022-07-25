package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Position;

public class PositionTest {
	
	@Test
	public void isPoisitionEvenTest() {
		for (int i = 0; i < 10; i += 2) {
			for (int j = 0; j < 10; j += 2) {
				assertTrue(new Position(i, j).isEven());
			}
		}
	}
	
	@Test
	public void isPoisitionEqualsTest() {
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				assertTrue(new Position(i, j).equals(new Position(i, j)));
			}
		}
	} 
}
