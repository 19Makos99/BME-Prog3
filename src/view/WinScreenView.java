package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.WinScreen.BackToMenuActionListener;

public class WinScreenView extends JPanel {

	private static final long serialVersionUID = 1L;

	public WinScreenView(String color, BackToMenuActionListener backToMenuActionListener) {
		super();
		
		JPanel spaceFiller = new JPanel();
		spaceFiller.setPreferredSize(new Dimension(400, 100));
		this.add(spaceFiller);
		
		JLabel winnerLabel = new JLabel("A " + color + " játékos nyert.");		
		winnerLabel.setFont(winnerLabel.getFont().deriveFont(64.0f));
		this.add(winnerLabel);
		
		spaceFiller = new JPanel();
		spaceFiller.setPreferredSize(new Dimension(400, 100));
		this.add(spaceFiller);
		
		JButton backToMenuButton = new JButton("Vissza a menübe.");
		backToMenuButton.addActionListener(backToMenuActionListener);
		backToMenuButton.setPreferredSize(new Dimension(400, 200));
		backToMenuButton.setFont(backToMenuButton.getFont().deriveFont(32.0f));
		this.add(backToMenuButton);
	}
}
