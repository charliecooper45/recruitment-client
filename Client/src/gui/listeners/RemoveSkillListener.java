package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Skill;

public class RemoveSkillListener extends ClientListener implements ActionListener {

	public RemoveSkillListener(ClientController controller) {
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
				Skill skill = controller.getView().getSkillDialogSkill(DialogType.REMOVE_SKILL);
				boolean added = controller.getModel().removeSkillFromCandidate(skill, candidate);
				
				if(added) {
					controller.getView().showMessageDialog(MessageDialogType.SKILL_REMOVED);
					
					// update the skills displayed for the candidate
					controller.getView().updateDisplayedCandidateSkills(controller.getModel().getCandidateSkills(candidate.getId()));
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.REMOVE_SKILL_FAILED);
				}
				controller.getView().hideDialog(DialogType.REMOVE_SKILL);
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.REMOVE_SKILL);
				break;
			}
		}
	}

}
