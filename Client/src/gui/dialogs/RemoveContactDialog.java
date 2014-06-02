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

import database.beans.Contact;
import database.beans.Organisation;

/**
 * Dialog that allows the user to remove a contact.
 * @author Charlie
 */
public class RemoveContactDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Organisation> orgCmbBox;
	private JComboBox<Contact> contactCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveContactDialog(JFrame frame) {
		super(frame, "Remove Contact");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the contact to remove below:"), gbc);
		
		// components
		orgCmbBox = new JComboBox<>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(orgCmbBox, gbc);
		contactCmbBox = new JComboBox<>();
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(contactCmbBox, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	public Contact getContact() {
		return (Contact) contactCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		orgCmbBox.removeAllItems();
		
		for (Organisation org : organisations) {
			orgCmbBox.addItem(org);
		}
	}

	@Override
	public void setDisplayedContacts(java.util.List<Contact> contacts) {
		contactCmbBox.removeAllItems();
		
		for (Contact contact : contacts) {
			contactCmbBox.addItem(contact);
		}
	};
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
		orgCmbBox.addActionListener(actionListener);
	}
}
