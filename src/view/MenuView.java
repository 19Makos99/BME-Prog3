package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Menu.ExitActionListener;
import controller.Menu.GameModeSelectionActionListener;
import controller.Menu.OpenSavedGameActionListener;
import controller.Menu.StartNewGameActionListener;

public class MenuView extends JPanel {

	private static final long serialVersionUID = 1L;

	public MenuView(StartNewGameActionListener startNewGameActionListener, GameModeSelectionActionListener gameModeSelectionActionListener, OpenSavedGameActionListener openSavedGameActionListener, ExitActionListener exitActionListener)  {
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		JPanel spaceFiller = new JPanel();
		spaceFiller.setPreferredSize(new Dimension(400, 50));
		JButton startGame = addAndAlignCenter("Új játék indítása", panel);
		JButton selectGameMode = addAndAlignCenter("Játékmód választása", panel);
		JButton openSavedGame = addAndAlignCenter("Mentett játék folytatása", panel);
		JButton exit = addAndAlignCenter("Kilépés", panel);
		
		startGame.addActionListener(startNewGameActionListener);
		selectGameMode.addActionListener(gameModeSelectionActionListener);
		openSavedGame.addActionListener(openSavedGameActionListener);
		exit.addActionListener(exitActionListener);
		
		GroupLayout layout = new GroupLayout(panel);
		
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(spaceFiller)
			.addComponent(startGame)
			.addComponent(selectGameMode)
			.addComponent(openSavedGame)
			.addComponent(exit)
		);

		layout.setVerticalGroup(
			layout.createSequentialGroup()
		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
		    .addComponent(spaceFiller)
			.addComponent(startGame)
			.addComponent(selectGameMode)
			.addComponent(openSavedGame)
			.addComponent(exit)
		);

		panel.setLayout(layout);
		
		this.add(panel);
	}
	
	private JButton addAndAlignCenter(String text, JPanel jp) {
		JButton jb = new JButton(text);
		jb.setFont(jb.getFont().deriveFont(32.0f));
		jb.setMinimumSize(new Dimension(400, 150));
		return jb;
	}
}
