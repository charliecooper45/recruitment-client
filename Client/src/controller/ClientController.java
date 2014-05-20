package controller;

import gui.ClientView;
import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import model.ClientModel;
import model.LoginAttempt;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Controller part of MVC, responsible for interaction between the view and the model.
 * @author Charlie
 */
public class ClientController {
	private final ClientView view;
	private final ClientModel model;
	
	public ClientController(ClientView view, ClientModel model) {
		this.view = view;
		this.model = model;
		this.view.setController(this);
		
		// shows the GUI to the user and sets the login listener for the login screen
		view.showGUI(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LoginAttempt attempt = ClientController.this.view.getLoginAttempt();
				String message = ClientController.this.model.login(attempt);
				
				List<Vacancy> vacancies = ClientController.this.model.getVacancies(true, null);
				List<User> users = ClientController.this.model.getUsers(null, true);
				
				if(message.equals(UserType.ADMINISTRATOR.toString())) {
					// the user is an administrator
					ClientController.this.view.displayMainWindow(UserType.ADMINISTRATOR, vacancies, users);
				} else if (message.equals(UserType.STANDARD.toString())) {
					// the user is a standard user
					ClientController.this.view.displayMainWindow(UserType.STANDARD, vacancies, users);
				}else {
					ClientController.this.view.showLoginErrorMessage(message);
				}
			}
		});
		
		// sets the listeners for the GUI
		view.setVacanciesPanelListeners(new ActionListener() {
			private boolean openVacancies = true;
			private User selectedUser = null;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				
				if(source instanceof JRadioButton) {
					// deal with changes to the type of vacancies to be shown
					JRadioButton button = (JRadioButton) source;
					if(button.getText().equals("All Vacancies")) {
						openVacancies = false;
						List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					} else {
						openVacancies = true;
						List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					}
				} else if(source instanceof JComboBox<?>){
					// change whose vacancies are shown 
					JComboBox<?> usersCombo = (JComboBox<?>) source;
					selectedUser = (User) usersCombo.getSelectedItem();
					
					if(selectedUser.getUserId() == null) {
						// this means we need to get all user`s vacancies
						selectedUser = null;
					}
					
					List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
					ClientController.this.view.updateVacanciesPanel(vacancies);
				}
			}
		}, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
					// retrieve the selected vacancy so it`s values can be updated from the server before the user sees it
					Vacancy selectedVacancy = ClientController.this.view.getSelectedVacancy();
					Vacancy updatedVacancy = ClientController.this.model.getVacancy(selectedVacancy.getVacancyId());
					ClientController.this.view.showVacancyPanel(updatedVacancy);
				}
			}
		}); 
	}
}
