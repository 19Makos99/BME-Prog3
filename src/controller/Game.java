package controller;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import model.Position;
import model.gamemode.GameMode;
import model.gamemode.NoSelectedUnit;
import model.gamemode.UnitsNotInLine;
import model.unit.Unit;
import view.Character;
import view.GameHeaderView;
import view.GameView;
import view.Window;

public class Game {
	
	private GameView view;
	private GameHeaderView headerView;
	private Window window;
	private GameMode gameMode;
	private List<JButton> buttonTiles = new ArrayList<JButton>();
	private List<Position> selectableMoves = new ArrayList<Position>();
	
	/***
	 * Indít egy új játékot az adott játékmódban.
	 */
	public Game(Window window, GameMode gameMode) {
		this.window = window;
		this.gameMode = gameMode;
		
		view = new GameView(gameMode.getTableSize());
		headerView = new GameHeaderView(view);
		
				
		for (int y = 0; y < gameMode.getTableSize(); ++y) {
			for (int x = 0; x < gameMode.getTableSize(); ++x) {
				JButton jb = new JButton();
				jb.setFocusable(false);
				jb.setBackground((x + y) % 2 == 0 ? new Color(255, 200, 150) : new Color(100, 70, 0));
				jb.addActionListener(new CharacterSelect());
				buttonTiles.add(jb);
			}
		}
		window.displayPanel(headerView);
		render();
		
		headerView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), "myAction");
		headerView.getActionMap().put("myAction", new StopMenuListener());
	}
	
	/***
	 * Frissíti a játékfelületet.
	 */
	private void render() {
		view.removeAll();
		headerView.updateCurrentPlayerLabel((gameMode.isWhitesTurn() ? "Fehér" : "Fekete") + " játékos van soron.");
		for (int y = 0; y < gameMode.getTableSize(); ++y) {
			for (int x = 0; x < gameMode.getTableSize(); ++x) {
				Unit u = gameMode.getUnitAt(x, y);
				
				JButton jb = buttonTiles.get(y * gameMode.getTableSize() + x);
				jb.setIcon(new Character(u));
				view.add(jb);
			}
		}
		view.validate();
		view.repaint();
	}
	
	/***
	 * Figyeli, hogy az ESC gomb meg lett-e nyomva.
	 * Ha igen akkor megnyitja a játékbeli opciókat.
	 */
	class StopMenuListener implements Action {

		@Override
		public boolean isEnabled() {
			gameMode.clearMoveOptions();
			gameMode.clearSelected();
			new SaveOptionMenu(window, gameMode);
			return false;
		}

		// Fölös/kötelezõ dolgok
		@Override
		public void actionPerformed(ActionEvent e) {}
		
		@Override
		public Object getValue(String key) {return null;}

		@Override
		public void putValue(String key, Object value) {}

		@Override
		public void setEnabled(boolean b) {}
		
		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {}
	}
	
	/***
	 * A mezõkre kattintást kezeli le.
	 * Ha a mezõn egy soron következõ színû egség van akkor megjeleníti az általa léphetõ opciókat.
	 * Ha az egyik játékos nem tud lépni akkor átirányít a nyerés nézetre.
	 */
	class CharacterSelect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = buttonTiles.indexOf(e.getSource());
			int x = index % gameMode.getTableSize();
			int y = index / gameMode.getTableSize();
			Unit current = gameMode.getUnitAt(x, y);
			
			if ((current.isEmpty() && (selectableMoves == null || selectableMoves.isEmpty())) || (current.isBlack() && gameMode.isWhitesTurn()) || (current.isWhite() && !gameMode.isWhitesTurn()))
				return;
			
			if (current.isEmpty() && selectableMoves.contains(new Position(x, y))) {
				try {
					gameMode.moveTo(x, y);
				} catch (NoSelectedUnit | UnitsNotInLine e1) {
					e1.printStackTrace();
				}
				
				gameMode.clearMoveOptions();
				gameMode.clearSelected();
				selectableMoves.clear();
				if (!gameMode.canWhiteMove() || !gameMode.canBlackMove())
					new WinScreen(window, gameMode.canWhiteMove() ? "Fehér" : "Fekete");
				
				render();
				return;
			}
			
			gameMode.clearMoveOptions();
			gameMode.clearSelected();
			current.setSelected(true);
			
			List<Position> possibleMoves = gameMode.getPossibleMovesAt(x, y);
			gameMode.markPossibleMoves(possibleMoves);
			selectableMoves = possibleMoves;
			
			render();
		}
	}
}
