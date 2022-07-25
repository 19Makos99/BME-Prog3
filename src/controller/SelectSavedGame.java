package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.gamemode.GameMode;
import view.SelectSavedGameView;
import view.Window;

public class SelectSavedGame {
	
	private Window window;
	
	// Public a teszt miatt
	public List<GameMode> saves = new ArrayList<GameMode>();
	public List<JButton> buttons = new ArrayList<JButton>();

	/***
	 * Betölti a mentett játékokat.
	 * Ha nincsenek akkor visszairányít a menübe.
	 */
	public SelectSavedGame(Window w) {
		this.window = w;
		
		File f = new File("Saves");
		if (!f.exists() || f.listFiles().length == 0 || readSaves(f.listFiles())) {
			JOptionPane.showMessageDialog(window, "Nincsenek mentések a Saves mappában.");
			new Menu(window);
			return;
		}
		
		SelectSavedGameView view = new SelectSavedGameView(buttons, new SelectSavedGameActionListener(), new BackToMenuActionListener());
		this.window.displayPanel(view);
	}
	
	/***
	 * Betölti az adott fájlokból a mentett játékokat.
	 */
	private boolean readSaves(File[] files) {
		
		for (int i = 0; i < files.length; ++i) {
			try {
				String currentFileName = files[i].getName();
				FileInputStream fileInputStream = new FileInputStream("Saves/" + currentFileName);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				GameMode readIn = (GameMode)objectInputStream.readObject();
				saves.add(readIn);
				JButton jButton = new JButton(currentFileName.substring(0, currentFileName.length() - 5));
				jButton.setToolTipText(readIn.gameModeName());
				buttons.add(jButton);
				objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
		return saves.size() == 0;
	}
	
	/***
	 * A kiválasztott mentett játékot elindítja.
	 */
	public class SelectSavedGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < buttons.size(); ++i) {
				if (e.getSource() == buttons.get(i)) {
					new Game(window, saves.get(i));
					return;
				}
			}
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
