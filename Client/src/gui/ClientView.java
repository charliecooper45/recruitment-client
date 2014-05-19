package gui;

import gui.listeners.ClientViewListener;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;

import model.ClientModel;
import model.LoginAttempt;
import controller.ClientController;
import database.beans.Vacancy;

/**
 * View part of MVC, responsible for managing the GUI elements. Includes the main method that runs the client on the EDT.
 * @author Charlie
 */
public class ClientView {
	private ClientController controller;
	private LoginWindow loginWindow;
	private MainWindow mainWindow;
	
	public ClientView() {
		loginWindow = new LoginWindow();
		mainWindow = new MainWindow();
	}
	
	public void showGUI(ActionListener loginListener, ClientViewListener clientViewListener) {
		loginWindow.setLoginListener(loginListener);
		mainWindow.setClientViewListener(clientViewListener);
		loginWindow.setVisible(true);
	}

	public LoginAttempt getLoginAttempt() {
		return loginWindow.getLoginAttempt();
	}
	
	public void showLoginErrorMessage(String message) {
		loginWindow.showErrorMessage(message);
	}
	
	public void displayMainWindow() {
		mainWindow.setVisible(true);
		loginWindow.dispose();
	}
	
	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		mainWindow.updateVacanciesPanel(vacancies);
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	public void setVacanciesListener(ActionListener listener) {
		mainWindow.setVacanciesListener(listener);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ClientModel model = new ClientModel();
				ClientView view = new ClientView();
				ClientController controller = new ClientController(view, model);
			}
		});
	}
}
