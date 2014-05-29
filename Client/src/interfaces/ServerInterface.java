package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.healthmarketscience.rmiio.RemoteInputStream;

import database.beans.Candidate;
import database.beans.Contact;
import database.beans.Organisation;
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
	public List<Organisation> getOrganisations() throws RemoteException;
	public List<Contact> getOrganisationsContacts(Organisation organisation) throws RemoteException;
	public boolean addVacancy(Vacancy vacancy, RemoteInputStream profileData) throws RemoteException;
	public boolean removeVacancy(Vacancy vacancy) throws RemoteException;
	public Organisation getOrganisation(int organisationId) throws RemoteException;
	public RemoteInputStream getOrganisationTob(String fileName) throws RemoteException;
	public boolean addOrganisationTob(Organisation organisation, RemoteInputStream tobData, String oldFileName) throws RemoteException;
	public boolean removeOrganisationTob(Organisation organisation) throws RemoteException;
	public boolean addOrganisation(Organisation organisation, RemoteInputStream tobData) throws RemoteException;
}
