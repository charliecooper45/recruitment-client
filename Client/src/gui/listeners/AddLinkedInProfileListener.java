package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;

/**
 * Listener for events on the add LinkedIn profile dialog.
 * @author Charlie
 */
public class AddLinkedInProfileListener extends ClientListener implements ActionListener {
	public AddLinkedInProfileListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			
			switch(text) {
			case "Confirm":
				// get the url for the LinkedIn profile
				String url = controller.getView().getLinkedInProfileDialogUrl(DialogType.CANDIDATE_ADD_LINKEDIN);
				
				try {
					URL checkedUrl = new URL(url);
					checkedUrl.toURI();
					
					// update the candidate`s profile with the LinkedIn profile data
					Candidate candidate = controller.getView().getCandidatePanelCandidate();
					boolean profileAdded = controller.getModel().addLinkedInProfile(candidate, checkedUrl);
					
					if(profileAdded) {
						controller.getView().updateCandidateLinkedInProfile(checkedUrl);
						controller.getView().hideDialog(DialogType.CANDIDATE_ADD_LINKEDIN);
						controller.getView().showMessageDialog(MessageDialogType.LINKEDIN_PROFILE_ADDED);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.ADD_LINKEDIN_FAIL);
					}
				} catch (MalformedURLException | URISyntaxException e1) {
					controller.getView().showErrorDialog(ErrorDialogType.ADD_LINKEDIN_FAIL);
				} catch (RemoteException e1) {
					controller.exitApplication();
				}
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.CANDIDATE_ADD_LINKEDIN);
				break;
			}
		}
	}
}
