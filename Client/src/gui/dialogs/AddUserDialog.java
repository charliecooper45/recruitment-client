package gui.dialogs;

import gui.Utils;
import interfaces.UserType;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.beans.User;

/**
 * Dialog that allows the user to add a user.
 * @author Charlie
 */
public class AddUserDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField userIdTxtField;
	private JPasswordField passwordTxtField;
	private JPasswordField confirmPasswordTxtField;
	private JTextField firstNameTxtField;
	private JTextField surnameTxtField;
	private JTextField emailTxtField;
	private JTextField phoneNoTxtField;
	private JComboBox<String> statusCmbBox;
	private JComboBox<UserType> accountTypeCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public AddUserDialog(JFrame frame) {
		super(frame, "Add User");
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
		panel.add(new JLabel("User ID: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Password: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Confirm Password: "), gbc);
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("First Name: "), gbc);
		Utils.setGBC(gbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Surname: "), gbc);
		Utils.setGBC(gbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Email Address: "), gbc);
		Utils.setGBC(gbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Phone Number: "), gbc);
		Utils.setGBC(gbc, 1, 8, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Status: "), gbc);
		Utils.setGBC(gbc, 1, 9, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Account Type: "), gbc);
		
		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		userIdTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(userIdTxtField, gbc);
		passwordTxtField = new JPasswordField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(passwordTxtField, gbc);
		confirmPasswordTxtField = new JPasswordField();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(confirmPasswordTxtField, gbc);
		firstNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(firstNameTxtField, gbc);
		surnameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 5, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(surnameTxtField, gbc);
		emailTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 6, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(emailTxtField, gbc);
		phoneNoTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 7, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(phoneNoTxtField, gbc);
		statusCmbBox = new JComboBox<String>();
		statusCmbBox.addItem("Active");
		statusCmbBox.addItem("Closed");
		Utils.setGBC(gbc, 2, 8, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(statusCmbBox, gbc);
		accountTypeCmbBox = new JComboBox<UserType>();
		accountTypeCmbBox.addItem(UserType.STANDARD);
		accountTypeCmbBox.addItem(UserType.ADMINISTRATOR);
		Utils.setGBC(gbc, 2, 9, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(accountTypeCmbBox, gbc);
		
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
	
	public User getUser() {
		StringBuilder errorMessage = new StringBuilder("");
		
		// checks that all fields are correct and returns null if they are not
		String userId = userIdTxtField.getText();
		String password = new String(passwordTxtField.getPassword());
		String confirmPassword = new String(confirmPasswordTxtField.getPassword());
		String firstName = firstNameTxtField.getText();
		String surname = surnameTxtField.getText();
		String email = emailTxtField.getText();
		String phoneNo = phoneNoTxtField.getText();
		String status = (String) statusCmbBox.getSelectedItem();
		UserType userType = (UserType) accountTypeCmbBox.getSelectedItem();
		
		if(userId.trim().isEmpty()) {
			errorMessage.append("UserId cannot be empty.\n");
		}
		if(password.trim().isEmpty()) {
			errorMessage.append("Password cannot be empty.\n");
		}
		if(!password.equals(confirmPassword)) {
			errorMessage.append("Passwords do not match.\n");
		}
		
		if(errorMessage.toString().trim().equals("")) {
			boolean accountStatus = false;
			
			if(status.equals("Active")) {
				accountStatus = true;
			}
			return new User(userId, password, firstName, surname, email, phoneNo, accountStatus, userType);
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot add user", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		// reset all fields
		userIdTxtField.setText("");
		passwordTxtField.setText("");
		confirmPasswordTxtField.setText("");
		phoneNoTxtField.setText("");
		emailTxtField.setText("");
		firstNameTxtField.setText("");
		surnameTxtField.setText("");
		statusCmbBox.setSelectedIndex(0);
		accountTypeCmbBox.setSelectedIndex(0);
		super.setVisible(b);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
