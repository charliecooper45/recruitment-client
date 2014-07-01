package interfaces;

import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.healthmarketscience.rmiio.RemoteInputStream;

import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Contact;
import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Search;
import database.beans.Skill;
import database.beans.Task;
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
	public boolean removeOrganisation(Organisation organisation) throws RemoteException;
	public boolean addCandidate(Candidate candidate, RemoteInputStream cvData) throws RemoteException;
	public boolean removeCandidate(Candidate candidate) throws RemoteException;
	public boolean addContact(Contact contact) throws RemoteException;
	public boolean removeContact(Contact contact) throws RemoteException;
	public List<Skill> getSkills() throws RemoteException;
	public List<Candidate> searchCandidates(Search search) throws RemoteException;
	public Candidate getCandidate(int candidateId) throws RemoteException;
	public RemoteInputStream getCandidateCV(String fileName) throws RemoteException;
	public boolean addLinkedInProfile(Candidate candidate, URL profileURL) throws RemoteException;
	public boolean removeLinkedInProfile(Candidate candidate) throws RemoteException;
	public boolean addCandidateCv(Candidate candidate, RemoteInputStream remoteFileData, String oldFileName) throws RemoteException;
	public boolean removeCandidateCv(Candidate candidate) throws RemoteException;
	public List<Event> getShortlist(int vacancyId) throws RemoteException;
	public boolean addCandidatesToShortlist(List<Candidate> candidates, Vacancy vacancy, String userId) throws RemoteException;
	public boolean removeCandidateFromShortlist(int candidateId, int vacancyId) throws RemoteException;
	public boolean updateCandidateDetails(Candidate candidate) throws RemoteException;
	public List<CandidateSkill> getCandidateSkills(int candidateId) throws RemoteException;
	public boolean addSkillToCandidate(Skill skill, Candidate candidate, String userId) throws RemoteException;
	public boolean removeSkillFromCandidate(Skill skill, Candidate candidate) throws RemoteException;
	public boolean updateVacancyDetails(Vacancy vacancy) throws RemoteException;
	public boolean updateOrganisationDetails(Organisation organisation) throws RemoteException;
	public List<Event> getCandidateEvents(int candidateId) throws RemoteException;
	public List<Vacancy> getOrganisationVacancies(int organisationId) throws RemoteException;
	public boolean addEvent(Event event) throws RemoteException;
	public boolean removeEvent(int eventId) throws RemoteException;
	public boolean saveCandidateNotes(int candidateId, String notes) throws RemoteException;
	public List<Event> getEvents(boolean shortlist, boolean cvSent, boolean interview, boolean placement, boolean user, String userId) throws RemoteException;
	public List<Task> getTasks(String userId) throws RemoteException;
	public boolean addTask(Task task) throws RemoteException;
	public boolean removeTask(Task task) throws RemoteException;
	public boolean addUser(User user) throws RemoteException;
	public boolean removeUser(User user) throws RemoteException;
	public User getUser(String userId) throws RemoteException;
	public boolean updateUserDetails(User user) throws RemoteException;
	public boolean addSkill(Skill skill) throws RemoteException;
	public boolean removeSkill(Skill skill) throws RemoteException;
}
