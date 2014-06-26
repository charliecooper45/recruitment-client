package gui.listeners;

import gui.DialogType;
import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import controller.ClientController;
import database.beans.User;

/**
 * Listener for events on the user management panel.
 * @author Charlie
 */
public class UserManagementPanelListener extends ClientListener implements ActionListener {
	private UserType displayedUserType = null;
	private boolean displayedUserStatus = false;
	
	public UserManagementPanelListener(ClientController controller) {
		super(controller);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO NEXT: if the source is the combo box with user types in it then update the displayed users to only show these user types
		Object source = e.getSource();
		
		if(source instanceof JComboBox<?>) {
			List<User> users = null;
			JComboBox<?> comboBox = (JComboBox<?>) source;
			String selectedItem = (String) comboBox.getSelectedItem();
			
			switch(selectedItem) {
			case "All Users":
				displayedUserType = null;
				break;
			case "Administrators":
				displayedUserType = UserType.ADMINISTRATOR;
				break;
			case "Standard Users":
				displayedUserType = UserType.STANDARD;
				break;
			case "Any":
				displayedUserStatus = false;
				break;
			case "Active":
				displayedUserStatus = true;
				break;
			}
			
			users = controller.getModel().getUsers(displayedUserType, displayedUserStatus);
			controller.getView().updateAdminPanelUsers(users);
		} else if(source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			if(text.equals("Add User")) {
				controller.getView().showDialog(DialogType.ADD_USER);
			}
		}
	}
	
	public UserType getDisplayedUserType() {
		return displayedUserType;
	}
	
	public boolean getDisplayedUserStatus() {
		return displayedUserStatus;
	}
}
