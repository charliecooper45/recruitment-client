package gui.dialogs;

import gui.MainWindow;
import gui.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;

import javafx.scene.text.Text;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lu.tudor.santec.jtimechooser.JTimeChooser;

import com.toedter.calendar.JDateChooser;

import database.beans.Task;

/**
 * Dialog that allows the user to add a task.
 * @author Charlie
 */
public class AddTaskDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	// components
	private JDateChooser dateChooser;
	private JTimeChooser timeChooser;
	private JTextField notesTxtField;
	private JButton confirmButton;
	private JButton cancelButton;

	public AddTaskDialog(JFrame frame) {
		super(frame, "Add Task");
		setSize(400, 200);
		init();
	}

	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;

		// labels
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Date: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Time: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		panel.add(new JLabel("Description: "), gbc);

		// components
		gbc.insets = new Insets(10, 0, 0, 20);
		gbc.weightx = 15;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		dateChooser = new JDateChooser();
		Utils.setGBC(gbc, 2, 1, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(dateChooser, gbc);
		timeChooser = new JTimeChooser(new Date());
		Utils.setGBC(gbc, 2, 2, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(timeChooser, gbc);
		notesTxtField = new JTextField();
		Utils.setGBC(gbc, 2, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		panel.add(notesTxtField, gbc);

		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 4, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);

		add(panel);
	}

	public Task getTask() {
		String errorMessage = null;

		Date date = dateChooser.getDate();
		if (date == null)
			errorMessage = "Date cannot be empty";
		Date time = timeChooser.getDateWithTime(new Date());
		String text = notesTxtField.getText();
		if (text.trim().isEmpty()) {
			if (errorMessage == null) {
				errorMessage = "Description cannot be empty";
			} else {
				errorMessage += "\nDescription cannot be empty";
			}
		}

		if (errorMessage != null) {
			JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			Task task = new Task(date, time, text, MainWindow.USER_ID);
			return task;
		}
	}

	@Override
	public void setVisible(boolean b) {
		// reset all fields
		dateChooser.setDate(new Date());
		timeChooser.setTime(new Date());
		notesTxtField.setText("");
		super.setVisible(b);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
