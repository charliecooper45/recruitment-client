package controller;

import gui.ClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ClientModel;
import model.LoginAttempt;

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
		});
	}
}
