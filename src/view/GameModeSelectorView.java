package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.GameModeSelector.BackToMenuActionListener;

public class GameModeSelectorView extends JPanel {

	private static final long serialVersionUID = 1L;

	public GameModeSelectorView(JButton[] buttons, BackToMenuActionListener backToMenuActionListener) {
		super(new GridLayout(3, 3, 20, 20));
		
		for (JButton jb : buttons) {
			this.add(jb);
		}

		JButton backToMenu = new JButton("Vissza a menübe");
		backToMenu.addActionListener(backToMenuActionListener);
		this.add(backToMenu);
	}
}
