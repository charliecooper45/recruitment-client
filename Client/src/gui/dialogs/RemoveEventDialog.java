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

import database.beans.Event;

/**
 * Dialog that allows the user to remove an event.
 * @author Charlie
 */
public class RemoveEventDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> eventCmbBox;
	private JButton confirmButton;
	private JButton cancelButton;

	public RemoveEventDialog(JFrame frame) {
		super(frame, "Remove Event");
		setSize(400, 200);
		init();
	}

	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;

		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the event to remove below:"), gbc);

		// components
		eventCmbBox = new JComboBox<Event>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(eventCmbBox, gbc);

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

	public Event getEvent() {
		return (Event) eventCmbBox.getSelectedItem();
	}
	
	@Override
	public void setDisplayedEvents(List<Event> events) {
		eventCmbBox.removeAllItems();
		
		for(Event event : events) {
			eventCmbBox.addItem(event);
		}
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
