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
	 * Bet�lti a mentett j�t�kokat.
	 * Ha nincsenek akkor visszair�ny�t a men�be.
	 */
	public SelectSavedGame(Window w) {
		this.window = w;
		
		File f = new File("Saves");
		if (!f.exists() || f.listFiles().length == 0 || readSaves(f.listFiles())) {
			JOptionPane.showMessageDialog(window, "Nincsenek ment�sek a Saves mapp�ban.");
			new Menu(window);
			return;
		}
		
		SelectSavedGameView view = new SelectSavedGameView(buttons, new SelectSavedGameActionListener(), new BackToMenuActionListener());
		this.window.displayPanel(view);
	}
	
	/***
	 * Bet�lti az adott f�jlokb�l a mentett j�t�kokat.
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
	 * A kiv�lasztott mentett j�t�kot elind�tja.
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
	 * Visszair�ny�t a men�be.
	 */
	public class BackToMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Menu(window);
		}
	}
}
