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
	public List<Candidate> listCandidates() throws RemoteException;
	public List<Vacancy> listVacancies(boolean open, User user) throws RemoteException;
}
