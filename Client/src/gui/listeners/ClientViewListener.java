package gui.listeners;

import java.util.List;

import database.beans.User;
import database.beans.Vacancy;

public interface ClientViewListener {
	public List<Vacancy> listVacancies(boolean open, User user);
}
