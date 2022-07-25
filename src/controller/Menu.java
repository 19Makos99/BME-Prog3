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
	 * Elind�t egy �j j�t�kot a kiv�lasztott j�t�km�dban.
	 */
	public class StartNewGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Game(window, selectedGameMode);
		}
	}
	
	/***
	 * �tir�ny�t a j�t�km�d v�laszt� men�be.
	 */
	public class GameModeSelectionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new GameModeSelector(window);
		}
	}
	
	/***
	 * �tir�ny�t a mentett j�t�kok men�be.
	 */
	public class OpenSavedGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SelectSavedGame(window);
		}
	}
	
	/***
	 * Az alkalmaz�st bez�rja.
	 */
	public class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispose();
		}
	}
}
