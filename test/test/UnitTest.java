package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.unit.Black;
import model.unit.Empty;
import model.unit.White;

public class UnitTest {

	@Test
	public void isBlackTest() {
		assertTrue(new Black().isBlack());
		assertFalse(new White().isBlack());
		assertFalse(new Empty().isBlack());
	}
	
	@Test
	public void isWhiteTest() {
		assertFalse(new Black().isWhite());
		assertTrue(new White().isWhite());
		assertFalse(new Empty().isWhite());
	}
	
	@Test
	public void isEmptyTest() {
		assertFalse(new Black().isEmpty());
		assertFalse(new White().isEmpty());
		assertTrue(new Empty().isEmpty());
	}
}
