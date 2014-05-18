package controller;

import gui.ClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ClientModel;

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
			public void actionPerformed(ActionEvent arg0) {
				String userId = ClientController.this.view.getUserId();
				String password = ClientController.this.view.getPassword();
				
				String error = ClientController.this.model.login(userId, password);
				
				if(error != null) {
					System.out.println(error);
				}
			}
		});
	}
}
