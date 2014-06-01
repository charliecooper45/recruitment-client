package gui.dialogs;

import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Dialog that allows the user to add a candidate.
 * @author Charlie
 */
public class AddCandidateDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField candidateFirstNameTxtField;
	private JTextField candidateSurnameTxtField;
	private JTextField jobTitleTxtField;
	private JTextField phoneNoTxtField;
	private JTextField emailTxtField;
	private JTextField addressTxtField;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	//TODO NEXT: implement the LinkedIn profile functionality - there is a library for this
	private JTextField linkedInTxtField;
	private JLabel cvFileLabel;
	private JButton browseCVButton;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public AddCandidateDialog(JFrame frame) {
		super(frame, "Add Candidate");
		setSize(400, 600);
		init();
	}

	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("First Name: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Surname: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Job Title: "), gbc);
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Phone Number: "), gbc);
		Utils.setGBC(gbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Email Address: "), gbc);
		Utils.setGBC(gbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Address: "), gbc);
		Utils.setGBC(gbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("LinkedIn profile: "), gbc);
		Utils.setGBC(gbc, 1, 8, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("CV: "), gbc);
		Utils.setGBC(gbc, 1, 9, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Notes: "), gbc);
		
		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		candidateFirstNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(candidateFirstNameTxtField, gbc);
		candidateSurnameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(candidateSurnameTxtField, gbc);
		jobTitleTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(jobTitleTxtField, gbc);
		phoneNoTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(phoneNoTxtField, gbc);
		emailTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 5, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(emailTxtField, gbc);
		addressTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 6, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(addressTxtField, gbc);
		linkedInTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 7, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(linkedInTxtField, gbc);
		cvFileLabel = new JLabel("");
		cvFileLabel.setFont(cvFileLabel.getFont().deriveFont(Font.ITALIC));
		Utils.setGBC(gbc, 2, 8, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(cvFileLabel, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		browseCVButton = new JButton("..");
		Utils.setGBC(gbc, 3, 8, 1, 1, GridBagConstraints.NONE);
		panel.add(browseCVButton, gbc);
		notesTxtArea = new JTextArea();
		notesTxtArea.setLineWrap(true);
		notesTxtArea.setWrapStyleWord(true);
		notesScrlPane = new JScrollPane(notesTxtArea);
		notesScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(gbc, 2, 9, 2, 2, GridBagConstraints.BOTH);
		panel.add(notesScrlPane, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 11, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	@Override
	public void setVisible(boolean b) {
		// reset all fields
		candidateFirstNameTxtField.setText("");
		candidateSurnameTxtField.setText("");
		jobTitleTxtField.setText("");
		phoneNoTxtField.setText("");
		emailTxtField.setText("");
		addressTxtField.setText("");
		notesTxtArea.setText("");
		linkedInTxtField.setText("");
		cvFileLabel.setText("");
		super.setVisible(b);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		browseCVButton.addActionListener(actionListener);
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}

}
