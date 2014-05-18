package interfaces;

import java.rmi.Remote;


public interface LoginInterface extends Remote{

	/** 
	 * Method that lets clients login, returning an interface to the server.
	 * @param userId The id of the user.
  	 * @param password The password of the user.
	 * @return A reference to a proxy of the server object.
	 * @throws SecurityException If the client is not allowed to login. 
	 */
	public ServerInterface login(String userId, String password) throws java.rmi.RemoteException;
}
