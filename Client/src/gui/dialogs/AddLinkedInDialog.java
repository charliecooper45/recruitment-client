package gui.dialogs;

import gui.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog that allows the user to add a LinkedIn profile to a candidate.
 * @author Charlie
 */
public class AddLinkedInDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JTextField urlTxtField;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public AddLinkedInDialog(JFrame frame) {
		super(frame, "Add LinkedIn profile");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("LinkedIn profile URL:"), gbc);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.LINE_START;
		urlTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(urlTxtField, gbc);
		
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 2, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);
		
		add(panel);
	}
	
	public String getProfileUrl() {
		return urlTxtField.getText();
	}
	
	@Override
	public void setVisible(boolean b) {
		// reset all fields
		urlTxtField.setText("");
		super.setVisible(b);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
