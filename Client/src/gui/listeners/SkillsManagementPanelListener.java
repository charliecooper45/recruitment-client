package gui.listeners;

import gui.DialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;

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
			
			if(text.equals("Add Skill")) {
				controller.getView().showDialog(DialogType.ADD_SKILL);
			} else if(text.equals("Remove Skill")) {
				
			}
		}
	}
}
