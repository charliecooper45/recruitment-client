package model;

import interfaces.LoginInterface;
import interfaces.ServerInterface;
import interfaces.UserType;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.healthmarketscience.rmiio.RemoteInputStream;

import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Contact;
import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Report;
import database.beans.Search;
import database.beans.Skill;
import database.beans.Task;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Model part of MVC, responsible for interaction with the recruitment server via RMI. 
 * Uses the proxy pattern to protect access to the ServerInterface SERVER object.
 * @author Charlie
 */
public class ClientModel implements ServerInterface {
	private static final String SERVER_URL = "rmi://localhost/RecruitmentServer";
	private static LoginInterface LOGIN_SERVER;
	private static ServerInterface SERVER;

	public String login(LoginAttempt attempt) {
		UserType userType = null;
		try {
			LOGIN_SERVER = (LoginInterface) Naming.lookup(SERVER_URL);
			SERVER = LOGIN_SERVER.login(attempt.getUserId(), attempt.getPassword());

			// get the user type of the user
			userType = SERVER.getUserType(attempt.getUserId());
			return userType.toString();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			return "Error: cannot connect to server.";
		} catch (SecurityException e) {
			return e.getMessage();
		}
	}

	@Override
	public List<Candidate> getCandidates() throws RemoteException {
		return SERVER.getCandidates();
	}

	@Override
	public List<Vacancy> getVacancies(boolean open, User user) throws RemoteException {
		List<Vacancy> vacancies = SERVER.getVacancies(open, user);
		Collections.sort(vacancies);
		return vacancies;
	}

	@Override
	public List<User> getUsers(UserType userType, boolean status) throws RemoteException {
		List<User> users = SERVER.getUsers(userType, status);
		Collections.sort(users);
		return users;
	}

	@Override
	public UserType getUserType(String userId) throws RemoteException {
		throw new UnsupportedOperationException("Method is not implemented for the ClientModel class");
	}

	@Override
	public Vacancy getVacancy(int vacancyId) throws RemoteException {
		return SERVER.getVacancy(vacancyId);
	}

	@Override
	public RemoteInputStream getVacancyProfile(String fileName) throws RemoteException {
		return SERVER.getVacancyProfile(fileName);
	}

	@Override
	public boolean addVacancyProfile(Vacancy vacancy, RemoteInputStream profileData, String oldFileName) throws RemoteException {
		return SERVER.addVacancyProfile(vacancy, profileData, oldFileName);
	}

	@Override
	public boolean removeVacancyProfile(Vacancy vacancy) throws RemoteException {
		return SERVER.removeVacancyProfile(vacancy);
	}

	@Override
	public boolean changeVacancyStatus(Vacancy vacancy) throws RemoteException {
		return SERVER.changeVacancyStatus(vacancy);
	}

	@Override
	public List<Organisation> getOrganisations() throws RemoteException {
		List<Organisation> organisations = SERVER.getOrganisations();
		Collections.sort(organisations);
		return organisations;
	}

	@Override
	public List<Contact> getOrganisationsContacts(Organisation organisation) throws RemoteException  {
		return SERVER.getOrganisationsContacts(organisation);
	}

	@Override
	public boolean addVacancy(Vacancy vacancy, RemoteInputStream profileData) throws RemoteException  {
		return SERVER.addVacancy(vacancy, profileData);
	}

	public boolean removeVacancy(Vacancy vacancy) throws RemoteException  {
		return SERVER.removeVacancy(vacancy);
	}

	@Override
	public Organisation getOrganisation(int organisationId) throws RemoteException  {
		return SERVER.getOrganisation(organisationId);
	}

	@Override
	public RemoteInputStream getOrganisationTob(String fileName) throws RemoteException  {
		return SERVER.getOrganisationTob(fileName);
	}

	@Override
	public boolean addOrganisationTob(Organisation organisation, RemoteInputStream tobData, String oldFileName) throws RemoteException  {
		return SERVER.addOrganisationTob(organisation, tobData, oldFileName);
	}

	@Override
	public boolean removeOrganisationTob(Organisation organisation) throws RemoteException  {
		return SERVER.removeOrganisationTob(organisation);
	}

	@Override
	public boolean addOrganisation(Organisation organisation, RemoteInputStream tobData) throws RemoteException  {
		return SERVER.addOrganisation(organisation, tobData);
	}

	@Override
	public boolean removeOrganisation(Organisation organisation) throws RemoteException  {
		return SERVER.removeOrganisation(organisation);
	}

	@Override
	public boolean addCandidate(Candidate candidate, RemoteInputStream cvData) throws RemoteException {
		return SERVER.addCandidate(candidate, cvData);
	}

	@Override
	public boolean removeCandidate(Candidate candidate) throws RemoteException  {
		return SERVER.removeCandidate(candidate);
	}

	@Override
	public boolean addContact(Contact contact) throws RemoteException  {
		return SERVER.addContact(contact);
	}

	@Override
	public boolean removeContact(Contact contact) throws RemoteException  {
		return SERVER.removeContact(contact);
	}

	@Override
	public List<Skill> getSkills() throws RemoteException  {
		return SERVER.getSkills();
	}

	@Override
	public List<Candidate> searchCandidates(Search search) throws RemoteException  {
		return SERVER.searchCandidates(search);
	}

	@Override
	public Candidate getCandidate(int candidateId) throws RemoteException  {
		return SERVER.getCandidate(candidateId);
	}

	@Override
	public RemoteInputStream getCandidateCV(String fileName) throws RemoteException {
		return SERVER.getCandidateCV(fileName);
	}

	@Override
	public boolean addLinkedInProfile(Candidate candidate, URL profileURL) throws RemoteException {
		return SERVER.addLinkedInProfile(candidate, profileURL);
	}

	@Override
	public boolean removeLinkedInProfile(Candidate candidate) throws RemoteException {
		return SERVER.removeLinkedInProfile(candidate);
	}

	@Override
	public boolean addCandidateCv(Candidate candidate, RemoteInputStream remoteFileData, String oldFileName) throws RemoteException {
		return SERVER.addCandidateCv(candidate, remoteFileData, oldFileName);
	}

	@Override
	public boolean removeCandidateCv(Candidate candidate) throws RemoteException  {
		return SERVER.removeCandidateCv(candidate);
	}

	@Override
	public List<Event> getShortlist(int vacancyId) throws RemoteException  {
		return SERVER.getShortlist(vacancyId);
	}

	@Override
	public boolean addCandidatesToShortlist(List<Candidate> candidates, Vacancy vacancy, String userId) throws RemoteException  {
		return SERVER.addCandidatesToShortlist(candidates, vacancy, userId);
	}

	@Override
	public boolean removeCandidateFromShortlist(int candidateId, int vacancyId) throws RemoteException {
		return SERVER.removeCandidateFromShortlist(candidateId, vacancyId);
	}

	@Override
	public boolean updateCandidateDetails(Candidate candidate) throws RemoteException  {
		return SERVER.updateCandidateDetails(candidate);
	}

	@Override
	public List<CandidateSkill> getCandidateSkills(int candidateId) throws RemoteException  {
		return SERVER.getCandidateSkills(candidateId);
	}

	@Override
	public boolean addSkillToCandidate(Skill skill, Candidate candidate, String userId) throws RemoteException  {
		return SERVER.addSkillToCandidate(skill, candidate, userId);
	}

	@Override
	public boolean removeSkillFromCandidate(Skill skill, Candidate candidate) throws RemoteException  {
		return SERVER.removeSkillFromCandidate(skill, candidate);
	}

	@Override
	public boolean updateVacancyDetails(Vacancy vacancy) throws RemoteException  {
		return SERVER.updateVacancyDetails(vacancy);
	}

	@Override
	public boolean updateOrganisationDetails(Organisation organisation) throws RemoteException  {
		return SERVER.updateOrganisationDetails(organisation);
	}

	@Override
	public List<Event> getCandidateEvents(int candidateId) throws RemoteException  {
		return SERVER.getCandidateEvents(candidateId);
	}

	@Override
	public List<Vacancy> getOrganisationVacancies(int organisationId) throws RemoteException  {
		return SERVER.getOrganisationVacancies(organisationId);
	}

	@Override
	public boolean addEvent(Event event) throws RemoteException  {
		return SERVER.addEvent(event);
	}

	@Override
	public boolean removeEvent(int eventId) throws RemoteException  {
		return SERVER.removeEvent(eventId);
	}

	@Override
	public boolean saveCandidateNotes(int candidateId, String notes) throws RemoteException  {
		return SERVER.saveCandidateNotes(candidateId, notes);
	}

	@Override
	public List<Event> getEvents(boolean shortlist, boolean cvSent, boolean interview, boolean placement, boolean user, String userId) throws RemoteException  {
		return SERVER.getEvents(shortlist, cvSent, interview, placement, user, userId);
	}

	@Override
	public List<Task> getTasks(String userId) throws RemoteException  {
		return SERVER.getTasks(userId);
	}

	@Override
	public boolean addTask(Task task) throws RemoteException  {
		return SERVER.addTask(task);
	}

	@Override
	public boolean removeTask(Task task) throws RemoteException  {
		return SERVER.removeTask(task);
	}

	@Override
	public boolean addUser(User user) throws RemoteException  {
		return SERVER.addUser(user);
	}

	@Override
	public boolean removeUser(User user) throws RemoteException {
		return SERVER.removeUser(user);
	}

	@Override
	public User getUser(String userId) throws RemoteException  {
		return SERVER.getUser(userId);
	}

	@Override
	public boolean updateUserDetails(User user) throws RemoteException  {
		return SERVER.updateUserDetails(user);
	}

	@Override
	public boolean addSkill(Skill skill) throws RemoteException  {
		return SERVER.addSkill(skill);
	}

	@Override
	public boolean removeSkill(Skill skill) throws RemoteException {
		return SERVER.removeSkill(skill);
	}

	@Override
	public Map<User, Map<EventType, Integer>> getUserReport(Report report) throws RemoteException {
		return SERVER.getUserReport(report);
	}

	@Override
	public Map<Vacancy, Map<EventType, Integer>> getVacancyReport(Report report) throws RemoteException {
		return SERVER.getVacancyReport(report);
	}

	@Override
	public Map<Organisation, Map<Boolean, Integer>> getOrganisationReport(Report report) throws RemoteException {
		return SERVER.getOrganisationReport(report);
	}
}
