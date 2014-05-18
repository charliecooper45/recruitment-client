package model;

import interfaces.LoginInterface;
import interfaces.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import database.beans.Candidate;

/**
 * Model part of MVC, responsible for interaction with the recruitment server via RMI.
 * @author Charlie
 */
public class ClientModel implements ServerInterface {
	private static final String SERVER_URL = "rmi://localhost/RecruitmentServer";
	private static LoginInterface LOGIN_SERVER;
	private static ServerInterface SERVER;
	
	public String login(String userId, String password) {
		try {
			LOGIN_SERVER = (LoginInterface) Naming.lookup(SERVER_URL);
			SERVER = LOGIN_SERVER.login(userId, password);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			return "Unable to connect to the server, please contact your administrator";
		} catch(SecurityException e) {
			return e.getMessage();
		}
		
		return null;
	}
	
	@Override
	public List<Candidate> listCandidates() {
		return SERVER.listCandidates();
	}
}
