package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public GameView(int tabSize) {
		super(new GridLayout(tabSize, tabSize, 0, 0));
	}
}
