package gui;

import gui.TopMenuPanel.MenuPanel;
import gui.listeners.OrganisationsPanelListener;
import gui.listeners.RemoveVacancyDialogListener;
import gui.listeners.TopMenuListener;
import gui.listeners.VacanciesPanelListener;
import interfaces.UserType;

import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.SwingUtilities;

import model.ClientModel;
import model.LoginAttempt;
import controller.ClientController;
import database.beans.Contact;
import database.beans.Organisation;
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

	// LoginWindow and MainWindow methods
	public LoginAttempt getLoginAttempt() {
		return loginWindow.getLoginAttempt();
	}

	public void showLoginErrorMessage(String message) {
		loginWindow.showErrorMessage(message);
	}

	public void displayMainWindow(UserType userType, List<Vacancy> vacancies, List<User> users) {
		mainWindow.setVisible(loginWindow.getUserId(), true, userType, vacancies, users);
		loginWindow.dispose();
	}

	// PanelType methods
	public PanelType getDisplayedPanel() {
		return mainWindow.getDisplayedPanel();
	}
	
	// VacanciesPanel methods
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		mainWindow.showVacanciesPanel(vacancies, users);
	}
	
	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		mainWindow.updateVacanciesPanel(vacancies);
	}

	public Vacancy getSelectedVacancy() {
		return mainWindow.getSelectedVacancy();
	}
	
	// VacancyPanel methods
	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile) {
		mainWindow.showVacancyPanel(updatedVacancy, tempFile);
	}

	public Vacancy getDisplayedVacancy() {
		return mainWindow.getDisplayedVacancy();
	}
	
	// OrganisationsPanel methods
	public void showOrganisationsPanel(List<Organisation> organisations) {
		mainWindow.showOrganisationsPanel(organisations);
	}
	
	public void updateOrganisationsPanel(List<Organisation> organisations) {
		mainWindow.updateOrganisationsPanel(organisations);
	}
	
	public String getOrganisationSearchTerm() {
		return mainWindow.getOrganisationSearchTerm();
	}
	
	public void removeOrganisationSearchTerm() {
		mainWindow.removeOrganisationSearchTerm();
	}
	
	// Generic methods (dialogs, file choosers)
	public File showFileChooser(String title) {
		return mainWindow.showFileChooser(title);
	}

	public void setSelectedTopMenuPanel(MenuPanel panel) {
		mainWindow.setSelectedTopMenuPanel(panel);
	}
	
	public boolean showDialog(DialogType dialogType) {
		return mainWindow.showDialog(dialogType);
	}

	public void showErrorDialog(ErrorDialogType errorDialog) {
		mainWindow.showErrorDialog(errorDialog);
	}

	public void showConfirmDialog(ConfirmDialogType confirmDialog) {
		mainWindow.showConfirmDialog(confirmDialog);		
	}
	
	public void showMenuDialog(MenuDialogType menuDialog) {
		mainWindow.showMenuDialog(menuDialog);
	}

	public void hideMenuDialog(MenuDialogType menuDialog) {
		mainWindow.hideMenuDialog(menuDialog);
	}

	public void setDisplayedOrganisationsInDialog(MenuDialogType menuDialog, List<Organisation> organisations) {
		mainWindow.setDisplayedOrganisationsInDialog(menuDialog, organisations);
	}
	
	public void setDisplayedOContactsInDialog(MenuDialogType menuDialog, List<Contact> contacts) {
		mainWindow.setDisplayedContactsInDialog(menuDialog, contacts);
	}

	public void setDisplayedVacanciesInDialog(MenuDialogType menuDialog, List<Vacancy> vacancies) {
		mainWindow.setDisplayedVacanciesInDialog(menuDialog, vacancies);
	}
	
	public void displayFileInDialog(MenuDialogType menuDialogType, File file) {
		mainWindow.displayFileInDialog(menuDialogType, file);
	}

	public Vacancy getVacancyDialogVacancy(MenuDialogType menuDialog) {
		return mainWindow.getVacancyDialogVacancy(menuDialog);
	}
	
	// methods to set listeners and controller
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	public void setMenuListener(ActionListener actionListener) {
		mainWindow.setMenuListener(actionListener);
	}
	
	public void setTopMenuListener(TopMenuListener topMenuListener) {
		mainWindow.setTopMenuListener(topMenuListener);
	}
	
	public void setVacanciesPanelListeners(VacanciesPanelListener vacanciesPanelListener) {
		mainWindow.setVacanciesPanelListeners(vacanciesPanelListener);
	}

	public void setVacancyPanelListener(ActionListener actionListener) {
		mainWindow.setVacancyPanelListener(actionListener);
	}

	public void setOrganisationsPanelListener(OrganisationsPanelListener organisationsPanelListener) {
		mainWindow.setOrganisationsPanelListener(organisationsPanelListener);
	}
	
	public void setAddVacancyDialogListener(ActionListener actionListener) {
		mainWindow.setAddVacancyDialogListener(actionListener);
	}
	
	public void setRemoveVacancyDialogListener(RemoveVacancyDialogListener removeVacancyDialogListener) {
		mainWindow.setRemoveVacancyDialogListener(removeVacancyDialogListener);
	}

	// main method
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
