package gui;

import interfaces.UserType;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.List;

import javax.swing.SwingUtilities;

import model.ClientModel;
import model.LoginAttempt;
import controller.ClientController;
import database.beans.User;
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
	
	public void showGUI(ActionListener loginListener) {
		loginWindow.setLoginListener(loginListener);
		loginWindow.setVisible(true);
	}

	public LoginAttempt getLoginAttempt() {
		return loginWindow.getLoginAttempt();
	}
	
	public void showLoginErrorMessage(String message) {
		loginWindow.showErrorMessage(message);
	}
	
	public void displayMainWindow(UserType userType, List<Vacancy> vacancies, List<User> users) {
		mainWindow.setVisible(true, userType, vacancies, users);
		loginWindow.dispose();
	}
	
	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		mainWindow.updateVacanciesPanel(vacancies);
	} 
	
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		mainWindow.showVacanciesPanel(vacancies, users);
	}
	
	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile) {
		mainWindow.showVacancyPanel(updatedVacancy, tempFile);
	}
	
	public Vacancy getSelectedVacancy() {
		return mainWindow.getSelectedVacancy();
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	public void setVacanciesPanelListeners(ActionListener actionListener, MouseListener mouseListener) {
		mainWindow.setVacanciesPanelListeners(actionListener, mouseListener);
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
