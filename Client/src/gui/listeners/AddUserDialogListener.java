package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;
import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.User;

/**
 * Listener for events on the add user dialog.
 * @author Charlie
 */
public class AddUserDialogListener extends ClientListener implements ActionListener {
	public AddUserDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		switch (button.getText()) {
		case "Confirm":
			User user = controller.getView().getUserDialogUser(DialogType.ADD_USER);
			
			boolean added = controller.getModel().addUser(user);
			
			if(added) {
				controller.getView().hideDialog(DialogType.ADD_USER);
				controller.getView().showMessageDialog(MessageDialogType.USER_ADDED);
				
				// update the displayed users
				boolean userStatus = controller.getUserManagementPanelListener().getDisplayedUserStatus();
				UserType userType = controller.getUserManagementPanelListener().getDisplayedUserType();
				List<User> users = controller.getModel().getUsers(userType, userStatus);
				controller.getView().updateDisplayedUsers(users);
			} else {
				controller.getView().showErrorDialog(ErrorDialogType.ADD_USER_FAIL);
			}
			break;
		case "Cancel ":
			controller.getView().hideDialog(DialogType.ADD_USER);
			break;
		}
	}
}
