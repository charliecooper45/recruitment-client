package controller;

import gui.ClientView;
import gui.listeners.ClientViewListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
			public List<Vacancy> listVacancies(boolean open, User user) {
				return ClientController.this.model.listVacancies(open, user);
			}
		});
		
		// sets the listeners for the GUI
		view.setVacanciesListener(new ActionListener() {
			private boolean openVacancies = true;
			private User user = null;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				
				if(source instanceof JRadioButton) {
					JRadioButton button = (JRadioButton) source;
					if(button.getText().equals("All Vacancies")) {
						openVacancies = false;
						List<Vacancy> vacancies = ClientController.this.model.listVacancies(openVacancies, user);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					} else {
						openVacancies = true;
						List<Vacancy> vacancies = ClientController.this.model.listVacancies(openVacancies, user);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					}
				} else {
					//TODO NEXT: add code to deal with a change in user
				}
			}
		}); 
	}
}
