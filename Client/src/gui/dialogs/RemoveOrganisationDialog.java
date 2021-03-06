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

import database.beans.Organisation;

/**
 * Dialog that allows the user to remove an organisation.
 * @author Charlie
 */
public class RemoveOrganisationDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<Organisation> organisationCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveOrganisationDialog(JFrame frame) {
		super(frame, "Remove Organisation");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the organisation to remove below:"), gbc);
		
		// components
		organisationCmbBox = new JComboBox<Organisation>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(organisationCmbBox, gbc);
		
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
	
	public Organisation getOrganisation() {
		return (Organisation) organisationCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		organisationCmbBox.removeAllItems();
		
		for (Organisation organisation : organisations) {
			organisationCmbBox.addItem(organisation);
		}
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
