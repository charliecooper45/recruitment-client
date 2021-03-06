package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.beans.Skill;

/**
 * Dialog that allows the user to add a skill.
 * @author Charlie
 */
public class AddSkillDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField skillNameTxtField;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public AddSkillDialog(JFrame frame) {
		super(frame, "Add Skill");
		setSize(400, 100);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Skill Name: "), gbc);
		
		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		skillNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(skillNameTxtField, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 12, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	public Skill getSelectedSkill() {
		if(skillNameTxtField.getText().trim().isEmpty()) {
			return null;
		} else {
			return new Skill(skillNameTxtField.getText(), MainWindow.USER_ID);
		}
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
