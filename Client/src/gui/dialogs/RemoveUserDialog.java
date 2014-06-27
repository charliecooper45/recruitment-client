package gui.dialogs;

import gui.MainWindow;
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

import database.beans.User;

/**
 * Dialog that allows the user to remove a user.
 * @author Charlie
 */
public class RemoveUserDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<User> userCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveUserDialog(JFrame frame) {
		super(frame, "Remove User");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the user to remove below:"), gbc);
		
		// components
		userCmbBox = new JComboBox<User>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(userCmbBox, gbc);
		
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
	
	public User getUser() {
		return (User) userCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedUsers(List<User> users) {
		userCmbBox.removeAllItems();
		
		for(User user: users) {
			if(!user.getUserId().equals(MainWindow.USER_ID)) {
				userCmbBox.addItem(user);
			}
		}
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
