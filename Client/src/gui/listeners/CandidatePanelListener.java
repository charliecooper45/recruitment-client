package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Organisation;

/**
 * Listener for events on the candidate panel.
 * @author Charlie
 */
public class CandidatePanelListener extends ClientListener implements ActionListener {
	public CandidatePanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			
			if (button.getText().trim().equals("Add LinkedIn")) {
				controller.getView().showDialog(DialogType.CANDIDATE_ADD_LINKEDIN);
			} else if (button.getText().trim().equals("Remove LinkedIn")) {
				boolean remove = controller.getView().showConfirmDialog(ConfirmDialogType.CANDIDATE_REMOVE_LINKEDIN);

				if (remove) {
					boolean removed = controller.getModel().removeLinkedInProfile(controller.getView().getCandidatePanelCandidate());
					if (removed) {
						controller.getView().showMessageDialog(MessageDialogType.LINKEDIN_PROFILE_REMOVED);
						controller.getView().updateCandidateLinkedInProfile(null);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_LINKEDIN_FAIL);
					}
				}
			} else if (button.getText().trim().equals("Add CV")) {
				File file = controller.getView().showFileChooser("Select CV to add.");
				if (file != null) {
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(file);
						RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);

						Candidate candidate = controller.getView().getCandidatePanelCandidate();
						String oldFileName = candidate.getCV();
						candidate.setCV(file.getName());

						boolean cvAdded = controller.getModel().addCandidateCv(candidate, remoteFileData, oldFileName);

						if (cvAdded) {
							String candidateCv = candidate.getCV();
							RemoteInputStream remoteCvData = controller.getModel().getCandidateCV(candidateCv);
							InputStream fileData = RemoteInputStreamClient.wrap(remoteCvData);
							Path tempFile = controller.storeTempFile(fileData, candidateCv);
							List<Organisation> organisations = controller.getModel().getOrganisations();
							controller.getView().showCandidatePanel(candidate, tempFile, organisations);
						}
					} catch (FileNotFoundException e) {
						// TODO handle this exception
						e.printStackTrace();
					} catch (IOException e) {
						// TODO handle this exception
						e.printStackTrace();
					}
				}
			} else if (button.getText().trim().equals("Remove CV")) {
				boolean confirm = controller.getView().showConfirmDialog(ConfirmDialogType.CANDIDATE_REMOVE_CV);

				if (confirm) {
					Candidate candidate = controller.getView().getCandidatePanelCandidate();

					if (candidate.getCV() != null) {
						if (controller.getModel().removeCandidateCv(candidate)) {
							candidate.setCV(null);
							List<Organisation> organisations = controller.getModel().getOrganisations();
							controller.getView().showCandidatePanel(candidate, null, organisations);
						}
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.CANDIDATE_NO_CV);
					}
				}
			} else if (button.getText().trim().equals("Save candidate data")) {
				Candidate updatedCandidate = controller.getView().getUpdatedCandidate();
				
				// send a message with the update candidate information to the server
				boolean updated = controller.getModel().updateCandidateDetails(updatedCandidate);
				
				if(updated) {
					controller.getView().showMessageDialog(MessageDialogType.CANDIDATE_UPDATED);
					controller.getView().updateDisplayedCandidate(updatedCandidate);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.CANDIDATE_UPDATE_FAIL);
				}
			}
		}
	}
}
