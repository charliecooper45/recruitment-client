package gui;

import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import model.ClientModel;
import controller.ClientController;

/**
 * View part of MVC, responsible for managing the GUI elements. Includes the main method that runs the client on the EDT.
 * @author Charlie
 */
public class ClientView {
	private LoginWindow loginWindow;
	
	public void showGUI(ActionListener loginListener) {
		this.loginWindow = new LoginWindow(loginListener);
		loginWindow.setVisible(true);
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

	public String getUserId() {
		return loginWindow.getUserId();
	}
	
	public String getPassword() {
		return loginWindow.getPassword();
	}
}
