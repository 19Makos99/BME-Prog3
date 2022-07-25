package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import model.Position;
import model.gamemode.AdvancedGameMode;
import model.gamemode.BaseGameMode;
import model.gamemode.BlackSpartanGameMode;
import model.gamemode.GameMode;
import model.gamemode.MiniAdvancedGameMode;
import model.gamemode.MiniGameMode;
import model.gamemode.NoSelectedUnit;
import model.gamemode.UnitAlreadySelected;
import model.gamemode.UnitsNotInLine;
import model.gamemode.WhiteSpartanGameMode;
import model.unit.Black;
import model.unit.Empty;
import model.unit.White;

@RunWith(value = Parameterized.class)
public class GenericGameModeTest {
	
	GameMode testSubject;
	
	static int counter = 0;
	public GenericGameModeTest(GameMode testSubject) {
		this.testSubject = testSubject.clone();
	}
		
	@Test
	public void tableSizeTest() {
		testSubject.getUnitAt(0, 0);
		testSubject.getUnitAt(0, testSubject.getTableSize() - 1);
		testSubject.getUnitAt(testSubject.getTableSize() - 1, 0);
		testSubject.getUnitAt(testSubject.getTableSize() - 1, testSubject.getTableSize() - 1);
		
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {testSubject.getUnitAt(-1, 0);});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {testSubject.getUnitAt(testSubject.getTableSize(), 0);});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {testSubject.getUnitAt(0, testSubject.getTableSize());});
	}
	
	@Test
	public void notRandomTableTest() {
		for (int y = 0; y < testSubject.getTableSize(); ++y) {
			for (int x = 0; x < testSubject.getTableSize(); ++x) {
				assertEquals(testSubject.getStartingUnitAt(x, y).isEmpty(), testSubject.getStartingUnitAt(x, y).isEmpty());
				assertEquals(testSubject.getStartingUnitAt(x, y).isBlack(), testSubject.getStartingUnitAt(x, y).isBlack());
				assertEquals(testSubject.getStartingUnitAt(x, y).isWhite(), testSubject.getStartingUnitAt(x, y).isWhite());
			}
		}
	}
	
	@Test
	public void noSelectedUnitTest() {
		assertThrows(NoSelectedUnit.class, () -> {testSubject.moveTo(0, 0);});
	}
	
	@Test
	public void unitSelectTest() {
		final Position firstUnitPos = findFist();
		
		try {
			testSubject.setSelectedUnit(firstUnitPos.getX(), firstUnitPos.getY());
		} catch (UnitAlreadySelected e) {
			fail();
		}
		assertThrows(UnitAlreadySelected.class, () -> {
			testSubject.setSelectedUnit(firstUnitPos.getX(), firstUnitPos.getY());
		});
		
		assertTrue(testSubject.getSelectedUnitPosition().equals(firstUnitPos));
	}
	
	@Test
	public void unitNotInLineTest() throws NoSelectedUnit, UnitsNotInLine, UnitAlreadySelected {
		clearTable(testSubject);
		testSubject.setUnitAt(0, 0, new White());
		testSubject.setSelectedUnit(0, 0);
		assertThrows(UnitsNotInLine.class, () -> {testSubject.moveTo(2, 1);});
	}
	
	@Test
	public void canBlackMoveTest() {
		for (int i = 0; i < 2; ++i) {
			for (Position p : testSubject.getAllBlackMoves()) {
				testSubject.setUnitAt(p.getX(), p.getY(), new White());
			}
		}
		assertFalse(testSubject.canBlackMove());
	}
	
	@Test
	public void canWhiteMoveTest() {
		for (int i = 0; i < 2; ++i) {
			for (Position p : testSubject.getAllWhiteMoves()) {
				testSubject.setUnitAt(p.getX(), p.getY(), new Black());
			}
		}
		assertFalse(testSubject.canWhiteMove());
	}
	
	public Position findFist() {
		for (int y = 0; y < testSubject.getTableSize(); ++y)
			for (int x = 0; x < testSubject.getTableSize(); ++x)
				if (!testSubject.getUnitAt(x, y).isEmpty())
					return new Position(x, y);
		return null;
	}
	
	public void clearTable(GameMode gameMode) {
		for (int y = 0; y < gameMode.getTableSize(); ++y)
			for (int x = 0; x < gameMode.getTableSize(); ++x)
				gameMode.setUnitAt(x, y, new Empty());
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
			{new BaseGameMode()},
			{new MiniGameMode()},
			{new AdvancedGameMode()},
			{new MiniAdvancedGameMode()},
			{new BlackSpartanGameMode()},
			{new WhiteSpartanGameMode()}
		});
	}
}
