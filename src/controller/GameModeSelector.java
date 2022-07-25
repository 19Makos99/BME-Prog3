package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.gamemode.AdvancedGameMode;
import model.gamemode.BaseGameMode;
import model.gamemode.BlackSpartanGameMode;
import model.gamemode.GameMode;
import model.gamemode.MiniAdvancedGameMode;
import model.gamemode.MiniGameMode;
import model.gamemode.WhiteSpartanGameMode;
import view.GameModeSelectorView;
import view.Window;

public class GameModeSelector {
	
	private Window window;
	
	public GameModeSelector(Window window) {
		this.window = window;
		
		this.window.displayPanel(new GameModeSelectorView(new JButton[] {
				createButton(new BaseGameMode()),
				createButton(new AdvancedGameMode()),
				createButton(new MiniGameMode()),
				createButton(new MiniAdvancedGameMode()),
				createButton(new WhiteSpartanGameMode()),
				createButton(new BlackSpartanGameMode())
		}, new BackToMenuActionListener(new BaseGameMode())));
	}
	
	/***
	 * K�sz�t egy gombot.
	 */
	private JButton createButton(GameMode gameMode) {
		JButton jb = new JButton(gameMode.gameModeName());
		jb.setToolTipText(gameMode.description());
		jb.addActionListener(new BackToMenuActionListener(gameMode));
		return jb;
	}
	
	/***
	 * Vissza ir�ny�t a men�be �s be�ll�tja, hogy melyik j�t�km�d induljon el �j j�t�k ind�t�sakor.
	 */
	public class BackToMenuActionListener implements ActionListener {
		private GameMode selectedGameMode;
		
		public BackToMenuActionListener(GameMode gameMode) {
			selectedGameMode = gameMode;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Menu(window, selectedGameMode);
		}
	}
}
