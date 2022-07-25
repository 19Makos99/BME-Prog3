package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameHeaderView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JLabel currentTurn = new JLabel("Feh�r j�t�kos van soron.");

	public GameHeaderView(JPanel down) {
		super();
		currentTurn.setAlignmentX(CENTER_ALIGNMENT);
		this.add(currentTurn);
		this.add(down);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/***
	 * Friss�ti a feliratot, hogy melyik j�t�kos van soron.
	 */
	public void updateCurrentPlayerLabel(String text) {
		currentTurn.setText(text);
	}
}
