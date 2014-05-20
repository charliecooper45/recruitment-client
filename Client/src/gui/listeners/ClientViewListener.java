package gui.listeners;

import java.util.List;

import database.beans.User;
import database.beans.Vacancy;

public interface ClientViewListener {
	public List<Vacancy> getVacancies(boolean open, User user);

	public List<User> getUsers(String userType, boolean status);
}
