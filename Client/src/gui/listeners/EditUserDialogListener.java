package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.User;

/**
 * Listener for events on the edit user dialog.
 * @author Charlie
 */
public class EditUserDialogListener extends ClientListener implements ActionListener {
	public EditUserDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		try {
			switch (button.getText()) {
			case "Confirm":
				User user = controller.getView().getUserDialogUser(DialogType.EDIT_USER);
				if (user != null) {
					boolean edited = controller.getModel().updateUserDetails(user);

					if (edited) {
						controller.getView().hideDialog(DialogType.EDIT_USER);
						controller.getView().showMessageDialog(MessageDialogType.USER_UPDATED);

						// update the displayed users
						boolean userStatus = controller.getUserManagementPanelListener().getDisplayedUserStatus();
						UserType userType = controller.getUserManagementPanelListener().getDisplayedUserType();
						List<User> users;
						users = controller.getModel().getUsers(userType, userStatus);
						controller.getView().updateDisplayedUsers(users);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.USER_UPDATE_FAIL);
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.EDIT_USER);
				break;
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}
}
