package gui.dialogs;

import gui.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.beans.Skill;

public class RemoveSkillDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Skill> skillCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveSkillDialog(JFrame frame) {
		super(frame, "Remove Skill");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the skill to remove below:"), gbc);
		
		// components
		skillCmbBox = new JComboBox<Skill>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(skillCmbBox, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	public Skill getSelectedSkill() {
		return (Skill) skillCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedSkills(List<Skill> skills) {
		skillCmbBox.removeAllItems();
		
		for(Skill skill : skills) {
			skillCmbBox.addItem(skill);
		}
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
