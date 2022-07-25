package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.SaveOptionMenu.BackToMenuActionListener;
import controller.SaveOptionMenu.ContinueGameActionListener;
import controller.SaveOptionMenu.SaveGameActionListener;


public class SaveOptionMenuView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public SaveOptionMenuView(SaveGameActionListener saveGameActionListener, ContinueGameActionListener continueGameActionListener, BackToMenuActionListener backToMenuActionListener) {
		super(new GridLayout(3, 1));
		JButton saveGame = new JButton("Játék mentése");
		JButton back = new JButton("Vissza");
		JButton backToMenu = new JButton("Vissza a menübe");
		
		saveGame.addActionListener(saveGameActionListener);
		back.addActionListener(continueGameActionListener);
		backToMenu.addActionListener(backToMenuActionListener);
		
		this.add(back);
		this.add(saveGame);
		this.add(backToMenu);
	}
}
