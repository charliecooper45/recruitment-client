package model;

import interfaces.LoginInterface;
import interfaces.ServerInterface;
import interfaces.UserType;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import com.healthmarketscience.rmiio.RemoteInputStream;

import database.beans.Candidate;
import database.beans.Contact;
import database.beans.Organisation;
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
	public List<Candidate> getCandidates() {
		//TODO NEXT: Not currently used!
		try {
			return SERVER.getCandidates();
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			return null;
		}
	}

	@Override
	public List<Vacancy> getVacancies(boolean open, User user) {
		try {
			List<Vacancy> vacancies = SERVER.getVacancies(open, user);
			Collections.sort(vacancies);
			return vacancies;
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getUsers(UserType userType, boolean status) {
		try {
			List<User> users = SERVER.getUsers(userType, status);
			Collections.sort(users);
			return users;
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserType getUserType(String userId) throws RemoteException {
		throw new UnsupportedOperationException("Method is not implemented for the ClientModel class");
	}

	@Override
	public Vacancy getVacancy(int vacancyId) {
		try {
			return SERVER.getVacancy(vacancyId);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			e.printStackTrace();
			return null;
		}
		
		//TODO NEXT: if null is returned the vacancy has been deleted, handle this
	}

	@Override
	public RemoteInputStream getVacancyProfile(String fileName) {
		try {
			return SERVER.getVacancyProfile(fileName);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addVacancyProfile(Vacancy vacancy, RemoteInputStream profileData, String oldFileName) {
		try {
			return SERVER.addVacancyProfile(vacancy, profileData, oldFileName);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeVacancyProfile(Vacancy vacancy) {
		try {
			return SERVER.removeVacancyProfile(vacancy);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changeVacancyStatus(Vacancy vacancy) {
		try {
			return SERVER.changeVacancyStatus(vacancy);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Organisation> getOrganisations() {
		try {
			List<Organisation> organisations = SERVER.getOrganisations();
			Collections.sort(organisations);
			return organisations;
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Contact> getOrganisationsContacts(Organisation organisation) {
		try {
			return SERVER.getOrganisationsContacts(organisation);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addVacancy(Vacancy vacancy, RemoteInputStream profileData) {
		try {
			return SERVER.addVacancy(vacancy, profileData);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeVacancy(Vacancy vacancy) {
		try {
			return SERVER.removeVacancy(vacancy);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Organisation getOrganisation(int organisationId) {
		try {
			return SERVER.getOrganisation(organisationId);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RemoteInputStream getOrganisationTob(String fileName) {
		try {
			return SERVER.getOrganisationTob(fileName);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addOrganisationTob(Organisation organisation, RemoteInputStream tobData, String oldFileName) {
		try {
			return SERVER.addOrganisationTob(organisation, tobData, oldFileName);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeOrganisationTob(Organisation organisation) {
		try {
			return SERVER.removeOrganisationTob(organisation);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addOrganisation(Organisation organisation, RemoteInputStream tobData) {
		try {
			return SERVER.addOrganisation(organisation, tobData);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeOrganisation(Organisation organisation) {
		try {
			return SERVER.removeOrganisation(organisation);
		} catch (RemoteException e) {
			//TODO NEXT: Deal with this exception - possible propogate it?
			//TODO NEXT: if null is returned handle this
			e.printStackTrace();
		}
		return false;
	}
}
