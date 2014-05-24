package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.healthmarketscience.rmiio.RemoteInputStream;

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
	public List<User> getUsers(UserType userType, boolean status) throws RemoteException;
	public UserType getUserType(String userId) throws RemoteException;
	public Vacancy getVacancy(int vacancyId) throws RemoteException;
	public RemoteInputStream getVacancyProfile(String fileName) throws RemoteException;
	public boolean addVacancyProfile(Vacancy vacancy, RemoteInputStream profileData, String oldFileName) throws RemoteException;
	public boolean removeVacancyProfile(Vacancy vacancy) throws RemoteException;
	public boolean changeVacancyStatus(Vacancy vacancy) throws RemoteException;
}
