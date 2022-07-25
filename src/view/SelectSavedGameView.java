package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.SelectSavedGame.BackToMenuActionListener;
import controller.SelectSavedGame.SelectSavedGameActionListener;

public class SelectSavedGameView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public SelectSavedGameView(List<JButton> buttons, SelectSavedGameActionListener selectSavedGameActionListener, BackToMenuActionListener backToMenuActionListener) {
		super(new GridLayout((int)Math.ceil(Math.sqrt(buttons.size())), (int)Math.ceil(Math.sqrt(buttons.size())), 5, 5));
		
		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).addActionListener(selectSavedGameActionListener);
			this.add(buttons.get(i));
		}
		
		JButton backToMenu = new JButton("Vissza a menübe");
		backToMenu.addActionListener(backToMenuActionListener);
		this.add(backToMenu);
	}
}
