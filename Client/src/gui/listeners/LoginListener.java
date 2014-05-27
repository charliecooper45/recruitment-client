package gui.listeners;

import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.LoginAttempt;
import controller.ClientController;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Listener for events on the login window.
 * @author Charlie
 */
public class LoginListener extends ClientListener implements ActionListener {
	
	public LoginListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		LoginAttempt attempt = controller.getView().getLoginAttempt();
		String message = controller.getModel().login(attempt);

		List<Vacancy> vacancies = controller.getModel().getVacancies(true, null);
		List<User> users = controller.getModel().getUsers(null, true);

		if (message.equals(UserType.ADMINISTRATOR.toString())) {
			// the user is an administrator
			controller.getView().displayMainWindow(UserType.ADMINISTRATOR, vacancies, users);
		} else if (message.equals(UserType.STANDARD.toString())) {
			// the user is a standard user
			controller.getView().displayMainWindow(UserType.STANDARD, vacancies, users);
		} else {
			controller.getView().showLoginErrorMessage(message);
		}
	}
}
