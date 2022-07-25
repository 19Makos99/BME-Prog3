package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import model.gamemode.GameMode;
import view.SaveOptionMenuView;
import view.Window;

public class SaveOptionMenu {

	private Window window;
	private GameMode currentGameMode;
	
	public SaveOptionMenu(Window window, GameMode gameMode) {
		this.window = window;
		this.currentGameMode = gameMode;
		this.window.displayPanel(new SaveOptionMenuView(new SaveGameActionListener(), new ContinueGameActionListener(), new BackToMenuActionListener()));
	}

	/***
	 * A jelenlegi játékot elmenti egy fájlba a Saves mappán belül.
	 */
	public class SaveGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File f = new File("Saves");
			if (!f.exists())
				f.mkdir();
			
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
				File save = new File("Saves/" + format.format(new Date()) + ".save");
				
				if (save.exists()) {
					JOptionPane.showMessageDialog(window, "A fájl már létezik.");
					return;
				}
				
				FileOutputStream fileOutputStream = new FileOutputStream(save);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(currentGameMode);
				
				fileOutputStream.close();
				new Menu(window);
			} catch (IOException e1){
				e1.printStackTrace();
			}
		}
	}
	
	/***
	 * Visszairányít a játékba.
	 */
	public class ContinueGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Game(window, currentGameMode);
		}
	}
	
	/***
	 * Visszairányít a menübe.
	 */
	public class BackToMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Menu(window);
		}
	}
}
