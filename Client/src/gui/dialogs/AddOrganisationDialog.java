package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
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
 * Dialog that allows the user to add a organisation.
 * @author Charlie
 */
public class AddOrganisationDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField nameTxtField;
	private JTextField phoneNoTxtField;
	private JTextField emailTxtField;
	private JTextField websiteTxtField;
	private JTextField addressTxtField;
	private JLabel tobFileLabel;
	private JButton browseTobButton;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JButton confirmButton;
	private JButton cancelButton;

	// holds the displayed File object
	private File displayedFile = null;
	
	public AddOrganisationDialog(JFrame frame) {
		super(frame, "Add Organisation");
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;

		// labels
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Name: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Phone Number: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Email Address: "), gbc);
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Website: "), gbc);
		Utils.setGBC(gbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Address: "), gbc);
		Utils.setGBC(gbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Terms of Business: "), gbc);
		Utils.setGBC(gbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Notes: "), gbc);
		
		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		nameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(nameTxtField, gbc);
		phoneNoTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(phoneNoTxtField, gbc);
		emailTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(emailTxtField, gbc);
		websiteTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(websiteTxtField, gbc);
		addressTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 5, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(addressTxtField, gbc);
		tobFileLabel = new JLabel("");
		tobFileLabel.setFont(tobFileLabel.getFont().deriveFont(Font.ITALIC));
		Utils.setGBC(gbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(tobFileLabel, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		browseTobButton = new JButton("..");
		Utils.setGBC(gbc, 3, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(browseTobButton, gbc);
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
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 9, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		add(panel);
	}
	
	public Organisation getOrganisation() {
		StringBuilder errorMessage = new StringBuilder("");
		
		// check that all fields are correct and returns null if they are not
		String name = nameTxtField.getText().trim();
		String phoneNumber = phoneNoTxtField.getText().trim();
		String email = emailTxtField.getText().trim();
		String website = websiteTxtField.getText().trim();
		String address = addressTxtField.getText().trim();
		File profile = displayedFile;
		String tobPath = null;
		if(profile != null) {
			tobPath = profile.getAbsolutePath();
		}
		String notes = notesTxtArea.getText().trim();
		
		if(name.isEmpty()) { 
			errorMessage.append("Organisation name is empty.\n");
		} 
		
		if(errorMessage.toString().trim().equals("")) {
			return new Organisation(-1, name, phoneNumber, email, website, address, tobPath, notes, MainWindow.USER_ID, -1);
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot add organisation", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	@Override
	public void setDisplayedFile(File file) {
		tobFileLabel.setText(file.getName());
		displayedFile = file;
	}
	
	@Override
	public void setVisible(boolean b) {
		nameTxtField.setText("");
		phoneNoTxtField.setText("");
		emailTxtField.setText("");
		websiteTxtField.setText("");
		addressTxtField.setText("");
		tobFileLabel.setText("");
		notesTxtArea.setText("");
		super.setVisible(b);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		browseTobButton.addActionListener(actionListener);
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
