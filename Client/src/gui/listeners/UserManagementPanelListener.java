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
		Object source = e.getSource();

		if (source instanceof JComboBox<?>) {
			List<User> users = null;
			JComboBox<?> comboBox = (JComboBox<?>) source;
			String selectedItem = (String) comboBox.getSelectedItem();

			switch (selectedItem) {
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
			controller.getView().updateDisplayedUsers(users);
		} else if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			if (text.equals("Add User")) {
				controller.getView().showDialog(DialogType.ADD_USER);
			} else if (text.equals("Remove User")) {
				UserType userType = controller.getUserManagementPanelListener().getDisplayedUserType();
				boolean status = controller.getUserManagementPanelListener().getDisplayedUserStatus();
				List<User> users = controller.getModel().getUsers(userType, status);
				controller.getView().setDisplayedUsersInDialog(DialogType.REMOVE_USER, users);
				controller.getView().showDialog(DialogType.REMOVE_USER);
			} else if (text.equals("Edit User")) {
				User user = controller.getView().getAdminPanelUser();

				// update the user if there is one selected in the GUI
				if (user != null)
					user = controller.getModel().getUser(user.getUserId());

				if (user != null) {
					controller.getView().setDisplayedUserInDialog(DialogType.EDIT_USER, user);
					controller.getView().showDialog(DialogType.EDIT_USER);
				}
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
