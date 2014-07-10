package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MainWindow;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Skill;

/**
 * Listens for events on the add skill to candidate dialog. 
 * @author Charlie
 */
public class AddCandidateSkillDialogListener extends ClientListener implements ActionListener {

	public AddCandidateSkillDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();

			switch (text) {
			case "Confirm":
				Candidate candidate = controller.getView().getCandidatePanelCandidate();
				Skill skill = controller.getView().getSkillDialogSkill(DialogType.ADD_CANDIDATE_SKILL);
				boolean added = controller.getModel().addSkillToCandidate(skill, candidate, MainWindow.USER_ID);
				
				if(added) {
					controller.getView().showMessageDialog(MessageDialogType.CANDIDATE_SKILL_ADDED);
					
					// update the skills displayed for the candidate
					controller.getView().updateDisplayedCandidateSkills(controller.getModel().getCandidateSkills(candidate.getId()));
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.ADD_CANDIDATE_SKILL_FAIL);
				}
				controller.getView().hideDialog(DialogType.ADD_CANDIDATE_SKILL);
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.ADD_CANDIDATE_SKILL);
				break;
			}
		}
	}

}
