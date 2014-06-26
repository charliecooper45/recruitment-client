package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.beans.Candidate;
import database.beans.Organisation;

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
	private JComboBox<Organisation> orgCmbBox;
	private JTextField phoneNoTxtField;
	private JTextField emailTxtField;
	private JTextField addressTxtField;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JTextField linkedInTxtField;
	private JLabel cvFileLabel;
	private JButton browseCVButton;
	private JButton confirmButton;
	private JButton cancelButton;
	
	// holds the displayed File object
	private File displayedFile = null;
	
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
		panel.add(new JLabel("Organisation: "), gbc);
		Utils.setGBC(gbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Phone Number: "), gbc);
		Utils.setGBC(gbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Email Address: "), gbc);
		Utils.setGBC(gbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Address: "), gbc);
		Utils.setGBC(gbc, 1, 8, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("LinkedIn profile: "), gbc);
		Utils.setGBC(gbc, 1, 9, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("CV: "), gbc);
		Utils.setGBC(gbc, 1, 10, 1, 1, GridBagConstraints.NONE);
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
		orgCmbBox = new JComboBox<Organisation>();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(orgCmbBox, gbc);
		phoneNoTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 5, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(phoneNoTxtField, gbc);
		emailTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 6, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(emailTxtField, gbc);
		addressTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 7, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(addressTxtField, gbc);
		linkedInTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 8, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(linkedInTxtField, gbc);
		cvFileLabel = new JLabel("");
		cvFileLabel.setFont(cvFileLabel.getFont().deriveFont(Font.ITALIC));
		Utils.setGBC(gbc, 2, 9, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(cvFileLabel, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		browseCVButton = new JButton("..");
		Utils.setGBC(gbc, 3, 9, 1, 1, GridBagConstraints.NONE);
		panel.add(browseCVButton, gbc);
		notesTxtArea = new JTextArea();
		notesTxtArea.setLineWrap(true);
		notesTxtArea.setWrapStyleWord(true);
		notesScrlPane = new JScrollPane(notesTxtArea);
		notesScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(gbc, 2, 10, 2, 2, GridBagConstraints.BOTH);
		panel.add(notesScrlPane, gbc);
		
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

	public Candidate getCandidate() {
		int organisationId = -1;
		String organisationName = null;
		StringBuilder errorMessage = new StringBuilder("");
		
		// checks that all fields are correct and returns null if they are not
		String firstName = candidateFirstNameTxtField.getText().trim();
		String surname = candidateSurnameTxtField.getText().trim();
		String jobTitle = jobTitleTxtField.getText();
		Organisation organisation = (Organisation) orgCmbBox.getSelectedItem();
		if(organisation.getId() != -1) {
			// the user has selected an organisation
			organisationId = organisation.getId();
			organisationName = organisation.getOrganisationName();
		}
		String phoneNo = phoneNoTxtField.getText();
		String email = emailTxtField.getText();
		String address = addressTxtField.getText();
		String notes = notesTxtArea.getText();
		String linkedInProfile = linkedInTxtField.getText();
		String cvPath = null;
		if(displayedFile != null) 
			cvPath = displayedFile.getAbsolutePath();
		
		if(firstName.isEmpty()) {
			errorMessage.append("First name is empty.\n");
		}
		if(surname.isEmpty()) {			
			errorMessage.append("Surname is empty.\n");
		}
		
		if(errorMessage.toString().trim().equals("")) {
			return new Candidate(-1, firstName, surname, jobTitle, organisationId, organisationName, phoneNo, email, address, notes, linkedInProfile, cvPath, MainWindow.USER_ID);
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot add candidate", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		orgCmbBox.removeAllItems();
		
		orgCmbBox.addItem(new Organisation(-1, null, null, null, null, null, null, null, null, -1));
		for (Organisation org : organisations) {
			orgCmbBox.addItem(org);
		}
	}
	
	@Override
	public void setDisplayedFile(File file) {
		cvFileLabel.setText(file.getName());
		displayedFile = file;
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
