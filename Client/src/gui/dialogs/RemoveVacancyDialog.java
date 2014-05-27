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

import database.beans.Vacancy;

/**
 * Dialog that allows the user to remove a vacancy.
 * @author Charlie
 */
public class RemoveVacancyDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Vacancy> vacancyCmbBx;
	private JButton confirmButton;
	private JButton cancelButton;
	
	public RemoveVacancyDialog(JFrame frame) {
		super(frame, "Remove Vacancy");
		setSize(400, 200);
		init();
	}
	
	private void init() {
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		gbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		panel.add(new JLabel("Please select the vacancy to remove below:"), gbc);
		
		// components
		vacancyCmbBx = new JComboBox<Vacancy>();
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		panel.add(vacancyCmbBx, gbc);
		
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
	
	public Vacancy getVacancy() {
		return (Vacancy) vacancyCmbBx.getSelectedItem();
	}
	
	@Override
	public void setDisplayedVacancies(List<Vacancy> vacancies) {
		vacancyCmbBx.removeAllItems();
		
		for (Vacancy vacancy : vacancies) {
			vacancyCmbBx.addItem(vacancy);
		}
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
