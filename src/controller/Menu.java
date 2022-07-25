package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.gamemode.BaseGameMode;
import model.gamemode.GameMode;
import view.MenuView;
import view.Window;

public class Menu {
	
	private Window window;
	private GameMode selectedGameMode;	

	public Menu(Window window) {
		this(window, new BaseGameMode());
	}

	public Menu(Window window, GameMode gameMode) {
		this.selectedGameMode = gameMode;
		this.window = window;
		this.window.displayPanel(new MenuView(new StartNewGameActionListener(), new GameModeSelectionActionListener(), new OpenSavedGameActionListener(), new ExitActionListener()));
	}
	
	/***
	 * Elindít egy új játékot a kiválasztott játékmódban.
	 */
	public class StartNewGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Game(window, selectedGameMode);
		}
	}
	
	/***
	 * Átirányít a játékmód választó menübe.
	 */
	public class GameModeSelectionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new GameModeSelector(window);
		}
	}
	
	/***
	 * Átirányít a mentett játékok menübe.
	 */
	public class OpenSavedGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SelectSavedGame(window);
		}
	}
	
	/***
	 * Az alkalmazást bezárja.
	 */
	public class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispose();
		}
	}
}
