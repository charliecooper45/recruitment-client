package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Skill;

public class SkillsManagementPanelListener extends ClientListener implements ActionListener {
	public SkillsManagementPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();

			if (text.equals("Add Skill")) {
				controller.getView().showDialog(DialogType.ADD_SKILL);
			} else if (text.equals("Remove Skill")) {
				Skill skill = controller.getView().getSkillPanelSkill();

				if (skill != null) {
					boolean remove = controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_SKILL);

					if (remove) {
						boolean removed = controller.getModel().removeSkill(skill);
						
						if(removed) {
							controller.getView().showMessageDialog(MessageDialogType.SKILL_REMOVED);
							List<Skill> skills = controller.getModel().getSkills();
							controller.getView().updateDisplayedSkills(skills);
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.REMOVE_SKILL_FAIL);
						}
					}
				}
			}
		}
	}
}
