package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Search;

/**
 * Listener for events on the search panel.
 * @author Charlie
 */
public class SearchPanelListener extends ClientListener implements ActionListener {
	public SearchPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			
			String name = button.getName();
			switch (name) {
			case "AddSkillButton":
				controller.getView().addSkillToSearch(); 
				break;
			case "RemoveSkillButton":
				controller.getView().removeSkillFromSearch();
				break;
			case "SearchButton":
				Search search = controller.getView().getSearchPanelSearch();
				List<Candidate> candidates = controller.getModel().searchCandidates(search);
				
				if(candidates != null) 
					controller.getView().updateSearchPanel(candidates);
				break;
			case "ResetSearchButton":
				controller.getView().resetSearchPanel();
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			// retrieve the selected candidate so it`s values can be updated from the server
			Candidate selectedCandidate = controller.getView().getSelectedCandidate();
			Candidate updatedCandidate = controller.getModel().getCandidate(selectedCandidate.getId());
			Path tempFile = null;
			
			// get the candidate CV
			try {
				String candidateCV = updatedCandidate.getCV();
				if(candidateCV != null) {
					RemoteInputStream remoteFileData = controller.getModel().getCandidateCV(candidateCV);
					InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
					tempFile = controller.storeTempFile(fileData, candidateCV);
				}
			} catch(IOException e1) {
				// TODO NEXT B: Possible display an error message here
				e1.printStackTrace();
			}
			controller.getView().showCandidatePanel(updatedCandidate, tempFile);
		}
	}
}
