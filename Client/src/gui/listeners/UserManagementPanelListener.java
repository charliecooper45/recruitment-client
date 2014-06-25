package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ClientController;

/**
 * Listener for events on the user management panel.
 * @author Charlie
 */
public class UserManagementPanelListener extends ClientListener implements ActionListener {
	public UserManagementPanelListener(ClientController controller) {
		super(controller);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.err.println("button pressed");
		
		//TODO NEXT: if the source is the combo box with user types in it then update the displayed users to only show these user types
	}
}
