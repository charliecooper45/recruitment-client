package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MainWindow;
import gui.MessageDialogType;
import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.User;

/**
 * Listener for events on the remove user dialog.
 * @author Charlie
 */
public class RemoveUserDialogListener extends ClientListener implements ActionListener {
	public RemoveUserDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;

			String text = button.getText();
			if (text.equals("Confirm")) {
				User user = controller.getView().getUserDialogUser(DialogType.REMOVE_USER);

				boolean removed = controller.getModel().removeUser(user);

				if (removed) {
					controller.getView().showMessageDialog(MessageDialogType.USER_REMOVED);

					// update the displayed users
					UserType userType = controller.getUserManagementPanelListener().getDisplayedUserType();
					boolean status = controller.getUserManagementPanelListener().getDisplayedUserStatus();
					List<User> users = controller.getModel().getUsers(userType, status);
					controller.getView().updateDisplayedUsers(users);

					controller.getView().hideDialog(DialogType.REMOVE_USER);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.REMOVE_USER_FAILED);
				}
			} else if (text.equals("Cancel ")) {
				controller.getView().hideDialog(DialogType.REMOVE_USER);
			}
		}
	}
}
