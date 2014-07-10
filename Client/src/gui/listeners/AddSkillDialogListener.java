package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Skill;

/**
 * Listener for events on the add skil dialog.
 * @author Charlie
 */
public class AddSkillDialogListener extends ClientListener implements ActionListener {

	public AddSkillDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		try {
			if (source instanceof JButton) {
				JButton button = (JButton) source;
				String text = button.getText();
				boolean added = false;

				switch (text) {
				case "Confirm":
					Skill skill = controller.getView().getSkillDialogSkill(DialogType.ADD_SKILL);

					if (skill != null) {
						added = controller.getModel().addSkill(skill);
					}

					if (skill == null || added == false) {
						controller.getView().showErrorDialog(ErrorDialogType.ADD_SKILL_FAIL);
					} else {
						controller.getView().showMessageDialog(MessageDialogType.SKILL_ADDED);
						List<Skill> skills = controller.getModel().getSkills();
						controller.getView().updateDisplayedSkills(skills);
						controller.getView().hideDialog(DialogType.ADD_SKILL);
					}
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.ADD_SKILL);
					break;
				}
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}
}
