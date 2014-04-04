package gui;

import javax.swing.SwingUtilities;

//TODO NEXT: Sort GIT configuration
/**
 * Starts the application on the Event Dispatch Thread
 * @author Charlie
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginWindow().setVisible(true);
				//MainWindow window = new MainWindow();
				//window.setVisible(true);
				//window.requestFocus();
			}
		});
	}
}
