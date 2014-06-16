package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Skill;

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
			} else if (button.getText().trim().equals("Add Event")) {
				List<Organisation> organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.ADD_EVENT, organisations);
				controller.getView().showDialog(DialogType.ADD_EVENT);
				
			} else if (button.getText().trim().equals("Remove Event")) {
				Candidate candidate = controller.getView().getCandidatePanelCandidate();
				List<Event> candidateEvents = controller.getModel().getCandidateEvents(candidate.getId());
				controller.getView().setDisplayedEventsInDialog(DialogType.REMOVE_EVENT, candidateEvents);
				controller.getView().showDialog(DialogType.REMOVE_EVENT);
			} else if (button.getText().trim().equals("Save candidate data")) {
				Candidate updatedCandidate = controller.getView().getUpdatedCandidate();
				
				// send a message with the update candidate information to the server
				boolean updated = controller.getModel().updateCandidateDetails(updatedCandidate);
				
				if(updated) {
					controller.getView().showMessageDialog(MessageDialogType.CANDIDATE_UPDATED);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.CANDIDATE_UPDATE_FAIL);
				}
			} else if (button.getText().trim().equals("Add Skill")) {
				List<Skill> skills = controller.getModel().getSkills();
				controller.getView().setDisplayedSkillsInDialog(DialogType.ADD_SKILL, skills);
				controller.getView().showDialog(DialogType.ADD_SKILL);
			} else if (button.getText().trim().equals("Remove Skill")) {
				Candidate candidate = controller.getView().getCandidatePanelCandidate();
				List<CandidateSkill> candidateSkills = controller.getModel().getCandidateSkills(candidate.getId());
				List<Skill> skills = new ArrayList<>();
				
				for(CandidateSkill candidateSkill : candidateSkills) {
					skills.add(new Skill(candidateSkill.getSkillName(), null));
				}
				controller.getView().setDisplayedSkillsInDialog(DialogType.REMOVE_SKILL, skills);
				controller.getView().showDialog(DialogType.REMOVE_SKILL);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Object source = event.getSource();

		if (source instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) source;

			int index = tabbedPane.getSelectedIndex();

			if (index == 2) {
				Candidate candidate = controller.getView().getCandidatePanelCandidate();
				// update the key skills from the server
				List<CandidateSkill> candidateSkill = controller.getModel().getCandidateSkills(candidate.getId());

				// update the view to display the skills
				controller.getView().updateDisplayedCandidateSkills(candidateSkill);
			} else if (index == 3) {
				Candidate candidate = controller.getView().getCandidatePanelCandidate();
				// update the events from the server
				List<Event> events = controller.getModel().getCandidateEvents(candidate.getId());

				// update the view to display the events
				controller.getView().updateDisplayedCandidateEvents(events);
			}
		}
	}
}
