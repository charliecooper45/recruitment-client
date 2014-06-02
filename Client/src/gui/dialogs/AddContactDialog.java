package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
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

import database.beans.Contact;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Dialog that allows the user to add a contact.
 * @author Charlie
 */
public class AddContactDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField contactFirstNameTxtField;
	private JTextField contactSurnameTxtField;
	private JTextField jobTitleTxtField;
	private JTextField phoneNoTxtField;
	private JTextField emailTxtField;
	private JTextField addressTxtField;
	private JComboBox<Organisation> orgsCmbBox;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JButton confirmButton;
	private JButton cancelButton;

	public AddContactDialog(JFrame frame) {
		super(frame, "Add Contact");
		
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
		panel.add(new JLabel("Organisation: "), gbc);
		Utils.setGBC(gbc, 1, 8, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Notes: "), gbc);
		
		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		contactFirstNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(contactFirstNameTxtField, gbc);
		contactSurnameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(contactSurnameTxtField, gbc);
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
		orgsCmbBox = new JComboBox<>();
		Utils.setGBC(gbc, 2, 7, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(orgsCmbBox, gbc);
		notesTxtArea = new JTextArea();
		notesTxtArea.setLineWrap(true);
		notesTxtArea.setWrapStyleWord(true);
		notesScrlPane = new JScrollPane(notesTxtArea);
		notesScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(gbc, 2, 8, 2, 2, GridBagConstraints.BOTH);
		panel.add(notesScrlPane, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 10, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	public Contact getContact() {
		StringBuilder errorMessage = new StringBuilder("");;
		
		// check that all fields are correct and returns null if they are not
		String contactFirstName = contactFirstNameTxtField.getText().trim();
		String contactSurname = contactSurnameTxtField.getText().trim();
		String jobTitle = jobTitleTxtField.getText().trim();
		String phoneNumber = phoneNoTxtField.getText().trim();
		String email = emailTxtField.getText().trim();
		String address = addressTxtField.getText().trim();
		Organisation organisation = (Organisation) orgsCmbBox.getSelectedItem();
		String notes = notesTxtArea.getText().trim();
		
		if(contactFirstName.isEmpty()) {
			errorMessage.append("Contact first name is empty.\n");
		}
		if(contactSurname.isEmpty()) {
			errorMessage.append("Contact surname is empty.\n");
		}
		
		if(errorMessage.toString().trim().equals("")) {
			return new Contact(-1, contactFirstName, contactSurname, jobTitle, phoneNumber, email, address, notes, organisation.getId(), MainWindow.USER_ID);
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot add contact", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		orgsCmbBox.removeAllItems();
		
		for(Organisation organisation : organisations) {
			orgsCmbBox.addItem(organisation);
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		contactFirstNameTxtField.setText("");
		contactSurnameTxtField.setText("");
		jobTitleTxtField.setText("");
		phoneNoTxtField.setText("");
		emailTxtField.setText("");
		addressTxtField.setText("");
		notesTxtArea.setText("");
		super.setVisible(b);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
