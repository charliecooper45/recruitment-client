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

import database.beans.Candidate;

/**
 * Dialog that allows the user to remove a candidate.
 * @author Charlie
 */
public class RemoveCandidateDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<Candidate> candidateCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveCandidateDialog(JFrame frame) {
		super(frame, "Remove Candidate");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the candidate to remove below:"), gbc);
		
		// components
		candidateCmbBox = new JComboBox<Candidate>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(candidateCmbBox, gbc);
		
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
	
	public Candidate getCandidate() {
		return (Candidate) candidateCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedCandidates(List<Candidate> candidates) {
		candidateCmbBox.removeAllItems();
		
		for(Candidate candidate : candidates) {
			candidateCmbBox.addItem(candidate);
		}
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
