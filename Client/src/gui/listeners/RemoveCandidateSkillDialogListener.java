package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Skill;

public class RemoveCandidateSkillDialogListener extends ClientListener implements ActionListener {

	public RemoveCandidateSkillDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();

			try {
				switch (text) {
				case "Confirm":
					Candidate candidate = controller.getView().getCandidatePanelCandidate();
					Skill skill = controller.getView().getSkillDialogSkill(DialogType.REMOVE_CANDIDATE_SKILL);
					boolean added = controller.getModel().removeSkillFromCandidate(skill, candidate);

					if (added) {
						controller.getView().showMessageDialog(MessageDialogType.CANDIDATE_SKILL_REMOVED);

						// update the skills displayed for the candidate
						controller.getView().updateDisplayedCandidateSkills(controller.getModel().getCandidateSkills(candidate.getId()));
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.CANDIDATE_REMOVE_SKILL_FAIL);
					}
					controller.getView().hideDialog(DialogType.REMOVE_CANDIDATE_SKILL);
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.REMOVE_CANDIDATE_SKILL);
					break;
				}
			} catch (RemoteException ex) {
				controller.exitApplication();
			}
		}
	}
}
