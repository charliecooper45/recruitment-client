package gui.listeners;

import interfaces.UserType;

import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JTabbedPane;

import controller.ClientController;
import database.beans.Skill;
import database.beans.User;

/**
 * Listener for events on the admin panel.
 * @author Charlie
 */
public class AdminPanelListener extends ClientListener {
	public AdminPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();

		if (source instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) source;

			int index = tabbedPane.getSelectedIndex();

			try {
			switch (index) {
			case 0:
				UserType userType = controller.getUserManagementPanelListener().getDisplayedUserType();
				boolean status = controller.getUserManagementPanelListener().getDisplayedUserStatus();
				List<User> users;
				users = controller.getModel().getUsers(userType, status);
				controller.getView().updateDisplayedUsers(users);
				break;
			case 1:
				List<Skill> skills = controller.getModel().getSkills();
				controller.getView().updateDisplayedSkills(skills);
				break;
			}
			} catch(RemoteException ex) {
				controller.exitApplication();
			}

		}
	}
}
