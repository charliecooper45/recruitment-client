package gui;

import javax.swing.SwingUtilities;

/**
 * Starts the application on the Event Dispatch Thread
 * @author Charlie
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//new LoginWindow().setVisible(true);
				new MainWindow().setVisible(true);
			}
		});
	}
}
