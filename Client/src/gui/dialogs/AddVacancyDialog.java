package gui.dialogs;

import gui.Utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class AddVacancyDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	// components
	private JComboBox<String> vacancyStatusCmbBx;
	private JTextField vacancyNameTxtField;
	private JTextField orgTxtField;
	private JTextField contactTxtField;
	private JLabel profileFileLabel;
	private JButton browseProfileButton;
	private JDateChooser dateChooser;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public AddVacancyDialog(JFrame frame) {
		super(frame, "Add Vacancy");
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// add the labels
		gbc.insets = new Insets(10, 0, 0, 10);
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
		gbc.weightx = 5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		vacancyStatusCmbBx = new JComboBox<String>();
		vacancyStatusCmbBx.addItem("Open");
		vacancyStatusCmbBx.addItem("Closed");
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(vacancyStatusCmbBx, gbc);
		vacancyNameTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(vacancyNameTxtField, gbc);
		orgTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(orgTxtField, gbc);
		contactTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 4, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(contactTxtField, gbc);
		profileFileLabel = new JLabel("MyFile.docx");
		profileFileLabel.setFont(profileFileLabel.getFont().deriveFont(Font.ITALIC));
		Utils.setGBC(gbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(profileFileLabel, gbc);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		browseProfileButton = new JButton("Browse...");
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

	@Override
	public void setButtonListener(ActionListener buttonListener) {
		browseProfileButton.addActionListener(buttonListener);
		confirmButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
	}
}