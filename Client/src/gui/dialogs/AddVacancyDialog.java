package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controller.ClientController;

import database.beans.Contact;
import database.beans.Organisation;
import database.beans.Vacancy;

public class AddVacancyDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	// components
	private JComboBox<String> vacancyStatusCmbBx;
	private JTextField vacancyNameTxtField;
	private JComboBox<Organisation> orgCmbBox;
	private JComboBox<Contact> contactCmbBox;
	private JLabel profileFileLabel;
	private JButton browseProfileButton;
	private JDateChooser dateChooser;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JButton confirmButton;
	private JButton cancelButton;

	// holds the displayed File object
	private File displayedFile = null;

	public AddVacancyDialog(JFrame frame) {
		super(frame, "Add Vacancy");
		init();
	}

	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;

		// add the labels
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Status: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Name: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Organisation: "), gbc);
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Contact: "), gbc);
		Utils.setGBC(gbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Profile: "), gbc);
		Utils.setGBC(gbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Date: "), gbc);
		Utils.setGBC(gbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Notes: "), gbc);

		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		vacancyStatusCmbBx = new JComboBox<String>();
		vacancyStatusCmbBx.addItem("Open");
		vacancyStatusCmbBx.addItem("Closed");
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(vacancyStatusCmbBx, gbc);
		vacancyNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(vacancyNameTxtField, gbc);
		orgCmbBox = new JComboBox<Organisation>();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(orgCmbBox, gbc);
		contactCmbBox = new JComboBox<Contact>();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(contactCmbBox, gbc);
		profileFileLabel = new JLabel("");
		profileFileLabel.setFont(profileFileLabel.getFont().deriveFont(Font.ITALIC));
		Utils.setGBC(gbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(profileFileLabel, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		browseProfileButton = new JButton("..");
		Utils.setGBC(gbc, 3, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(browseProfileButton, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		dateChooser = new JDateChooser(new Date());
		Utils.setGBC(gbc, 2, 6, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(dateChooser, gbc);
		notesTxtArea = new JTextArea();
		notesTxtArea.setLineWrap(true);
		notesTxtArea.setWrapStyleWord(true);
		notesScrlPane = new JScrollPane(notesTxtArea);
		notesScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(gbc, 2, 7, 2, 2, GridBagConstraints.BOTH);
		panel.add(notesScrlPane, gbc);

		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 9, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);

		add(panel);
	}

	public Vacancy getVacancy() {
		StringBuilder errorMessage = new StringBuilder("");;
		// check that all fields are correct and returns null if they are not
		String status = (String) vacancyStatusCmbBx.getSelectedItem();
		boolean statusBoolean = false;
		if(status == "Open") {
			statusBoolean = true;
		}
		String vacancyName = vacancyNameTxtField.getText().trim();
		Organisation organisation = (Organisation) orgCmbBox.getSelectedItem();
		Contact contact = (Contact) contactCmbBox.getSelectedItem();
		File profile = displayedFile;
		String profilePath = null;
		if(profile != null) {
			profilePath = profile.getAbsolutePath();
		}
		Date date = dateChooser.getDate();
		String notes = notesTxtArea.getText();
		
		if(vacancyName.isEmpty()) { 
			errorMessage.append("Vacancy name is empty.\n");
		} 
		if(contact.getId() == -1) {
			errorMessage.append("Contact is empty.\n");
		}
		if(date == null) {
			errorMessage.append("Date is empty.\n");
		}
		
		if(errorMessage.toString().trim().equals("")) {
			return new Vacancy(-1, statusBoolean, vacancyName, date, notes, profilePath, organisation.getId(), organisation.getOrganisationName(), 
					MainWindow.USER_ID, contact.getId(), contact.toString(), contact.getPhoneNumber());
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot add vacancy", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		orgCmbBox.removeAllItems();
		
		for (Organisation org : organisations) {
			orgCmbBox.addItem(org);
		}
	}

	@Override
	public void setDisplayedContacts(List<Contact> contacts) {
		contactCmbBox.removeAllItems();
		
		if (contacts.size() == 0) {
			contactCmbBox.setFont(contactCmbBox.getFont().deriveFont(Font.ITALIC));
			contactCmbBox.addItem(new Contact(-1, "Please create a contact for this", "organisation.", null, null, null, null, null, -1, null));
		} else {
			contactCmbBox.setFont(contactCmbBox.getFont().deriveFont(Font.PLAIN));
			for (Contact contact : contacts) {
				contactCmbBox.addItem(contact);
			}
		}
	}

	@Override
	public void setDisplayedFile(File file) {
		profileFileLabel.setText(file.getName());
		displayedFile = file;
	}

	@Override
	public void setVisible(boolean b) {
		// reset all fields
		vacancyNameTxtField.setText("");
		orgCmbBox.setSelectedIndex(0);
		profileFileLabel.setText("");
		dateChooser.setDate(new Date());
		notesTxtArea.setText("");
		super.setVisible(b);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		browseProfileButton.addActionListener(actionListener);
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
		orgCmbBox.addActionListener(actionListener);
	}
}
