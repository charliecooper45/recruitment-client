package gui;

import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import model.ClientModel;
import model.LoginAttempt;
import controller.ClientController;

/**
 * View part of MVC, responsible for managing the GUI elements. Includes the main method that runs the client on the EDT.
 * @author Charlie
 */
public class ClientView {
	private LoginWindow loginWindow;
	private MainWindow mainWindow;
	
	public void showGUI(ActionListener loginListener) {
		this.loginWindow = new LoginWindow(loginListener);
		loginWindow.setVisible(true);
	}

	public LoginAttempt getLoginAttempt() {
		return loginWindow.getLoginAttempt();
	}
	
	public void showLoginErrorMessage(String message) {
		loginWindow.showErrorMessage(message);
	}
	
	public void displayMainWindow() {
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		loginWindow.dispose();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ClientModel model = new ClientModel();
				ClientView view = new ClientView();
				@SuppressWarnings("unused")
				ClientController controller = new ClientController(view, model);
			}
		});
	}
}
