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
	
	public String login(LoginAttempt attempt) {
		try {
			LOGIN_SERVER = (LoginInterface) Naming.lookup(SERVER_URL);
			SERVER = LOGIN_SERVER.login(attempt.getUserId(), attempt.getPassword());
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			return "Error: cannot connect to server.";
		} catch(SecurityException e) {
			return e.getMessage();
		}
		
		// test code
		if(SERVER != null) {
			System.out.println(SERVER.listCandidates());
		}
		
		return null;
	}
	
	@Override
	public List<Candidate> listCandidates() {
		return SERVER.listCandidates();
	}
}
