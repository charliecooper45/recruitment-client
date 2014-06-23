package gui.listeners;

import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.LoginAttempt;
import controller.ClientController;
import database.beans.Task;
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
		List<Vacancy> vacancies = null;
		List<User> users = null;
		List<Task> tasks = null;
		LoginAttempt attempt = controller.getView().getLoginAttempt();
		String message = controller.getModel().login(attempt);

		if (message.equals(UserType.ADMINISTRATOR.toString())) {
			vacancies = controller.getModel().getVacancies(true, null);
			users = controller.getModel().getUsers(null, true);
			tasks = controller.getModel().getTasks(attempt.getUserId());
			// the user is an administrator
			controller.getView().displayMainWindow(UserType.ADMINISTRATOR, vacancies, users, tasks);
		} else if (message.equals(UserType.STANDARD.toString())) {
			vacancies = controller.getModel().getVacancies(true, null);
			users = controller.getModel().getUsers(null, true);
			tasks = controller.getModel().getTasks(attempt.getUserId());
			// the user is a standard user
			controller.getView().displayMainWindow(UserType.STANDARD, vacancies, users, tasks);
		} else {
			controller.getView().showLoginErrorMessage(message);
		}
	}
}
