package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.WinScreenView;
import view.Window;

public class WinScreen {
	
	private Window window;
	
	public WinScreen(Window window, String winner) {
		this.window = window;
		this.window.displayPanel(new WinScreenView(winner, new BackToMenuActionListener()));
	}

	/***
	 * Átirányít a menübe.
	 */
	public class BackToMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Menu(window);
		}
	}
}
