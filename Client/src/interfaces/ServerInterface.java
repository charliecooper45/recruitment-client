package interfaces;

import java.util.List;

import database.beans.Candidate;

/**
 * The RMI interface used to send commands to the server.
 * @author Charlie
 */
public interface ServerInterface {
	public List<Candidate> listCandidates();
}
