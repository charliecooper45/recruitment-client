package controller;

import gui.ClientView;
import gui.listeners.ClientViewListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
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
				
				if(message != null) {
					ClientController.this.view.showLoginErrorMessage(message);
				} else {
					// the user has logged in successfully
					ClientController.this.view.displayMainWindow();
				}
			}
		}, new ClientViewListener() {
			@Override
			public List<Vacancy> getVacancies(boolean open, User user) {
				return ClientController.this.model.getVacancies(open, user);
			}

			@Override
			public List<User> getUsers(String userType, boolean status) {
				return ClientController.this.model.getUsers(userType, status);
				//return ClientController.this.model.listUsers();
			}
		});
		
		// sets the listeners for the GUI
		view.setVacanciesListener(new ActionListener() {
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
		}); 
	}
}
