package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import database.beans.Candidate;
import database.beans.User;
import database.beans.Vacancy;

/**
 * The RMI interface used to send commands to the server.
 * @author Charlie
 */
public interface ServerInterface extends Remote{
	// methods callable by standard users and administrators
	public List<Candidate> getCandidates() throws RemoteException;
	public List<Vacancy> getVacancies(boolean open, User user) throws RemoteException;
	public List<User> getUsers(String userType, boolean status) throws RemoteException;
}
