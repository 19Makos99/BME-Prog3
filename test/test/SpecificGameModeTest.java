package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.gamemode.AdvancedGameMode;
import model.gamemode.BaseGameMode;
import model.gamemode.BlackSpartanGameMode;
import model.gamemode.GameMode;
import model.gamemode.MiniGameMode;
import model.gamemode.WhiteSpartanGameMode;
import model.unit.Black;
import model.unit.Empty;
import model.unit.Unit;
import model.unit.White;

public class SpecificGameModeTest {

	public boolean isSame(Unit a, Unit b) {
		return a.isBlack() == b.isBlack()
				&& a.isEmpty() == b.isEmpty()
				&& a.isMoveOption() == b.isMoveOption()
				&& a.isQueen() == b.isQueen()
				&& a.isSelected() == b.isSelected()
				&& a.isWhite() == b.isWhite();
	}
	
	@Test
	public void startingPositionTest() {
		Unit answer[][] = new Unit[][] {
			{new White(), new White(), new White(), new White()},
			{new Empty(), new Empty(), new Empty(), new Empty()},
			{new Empty(), new Empty(), new Empty(), new Empty()},
			{new Black(), new Black(), new Black(), new Black()}
		};
		GameMode gameMode = new MiniGameMode();
		for (int y = 0; y < gameMode.getTableSize(); ++y) {
			for (int x = 0; x < gameMode.getTableSize(); ++x) {
				assertTrue(isSame(gameMode.getUnitAt(x, y), answer[y][x]));
			}
		}
	}
	
	@Test
	public void queenCanMoveInAllDirectionInAdvancedTest() {
		GameMode gameMode = new AdvancedGameMode();
		clearTable(gameMode);
		
		Unit queen = new White();
		queen.setQueen(true);
		gameMode.setUnitAt(2, 2, queen);
		
		assertEquals(gameMode.getAllWhiteMoves().size(), 8);
		
		for (int y = 1; y < 4; ++y) {
			for (int x = 1; x < 4; ++x) {
				if (y == 2 && x == 2)
					continue;
				
				gameMode.setUnitAt(x, y, new Black());
				assertEquals(gameMode.getAllWhiteMoves().size(), 8);
			}
		}
	}
	
	@Test
	public void queenCanMoveDiagonalInNotAdvancedGameModeTest() {
		queenCanMoveDiagonalInGameModeTest(new BaseGameMode());
		queenCanMoveDiagonalInGameModeTest(new BlackSpartanGameMode());
		queenCanMoveDiagonalInGameModeTest(new WhiteSpartanGameMode());
	}
	
	public void queenCanMoveDiagonalInGameModeTest(GameMode gameMode) {
		clearTable(gameMode);
		
		Unit queen = new White();
		queen.setQueen(true);
		gameMode.setUnitAt(2, 2, queen);
		
		assertEquals(gameMode.getAllWhiteMoves().size(), 4);
		
		for (int y = 1; y < 4; ++y) {
			for (int x = 1; x < 4; ++x) {
				if (y == 2 && x == 2)
					continue;
				
				gameMode.setUnitAt(x, y, new Black());
				assertEquals(gameMode.getAllWhiteMoves().size(), 4);
			}
		}
	}
	
	public void clearTable(GameMode gameMode) {
		for (int y = 0; y < gameMode.getTableSize(); ++y)
			for (int x = 0; x < gameMode.getTableSize(); ++x)
				gameMode.setUnitAt(x, y, new Empty());
	}
}
