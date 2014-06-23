package gui.dialogs;

import gui.Utils;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lu.tudor.santec.jtimechooser.JTimeChooser;

import com.toedter.calendar.JDateChooser;

import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Dialog that allows the user to add an event.
 * @author Charlie
 */
public class AddEventDialog extends RecruitmentDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<EventType> eventTypes;
	private JButton confirmButton;
	private JButton cancelButton;

	// panels that are displayed when the user selects an event type
	private JPanel middlePnl;
	private JComboBox<Organisation> organisationCmbBx;
	private JComboBox<Vacancy> vacanciesCmbBx;
	private JDateChooser dateChooser;
	private JTimeChooser timeChooser;

	public AddEventDialog(JFrame frame) {
		super(frame, "Add Event");
		setLayout(new BorderLayout());
		setSize(400, 400);
		initTopPnl();
		initMiddlePnl();
		initBottomPnl();
	}

	private void initTopPnl() {
		JPanel topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints topGbc = new GridBagConstraints();
		topGbc.weightx = 1;
		topGbc.weighty = 1;
		topGbc.insets = new Insets(10, 10, 10, 10);

		Utils.setGBC(topGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		topPanel.add(new JLabel("Please select the event type to add to this candidate:"), topGbc);

		eventTypes = new JComboBox<EventType>();
		for (EventType type : EventType.values()) {
			eventTypes.addItem(type);
		}
		Utils.setGBC(topGbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		topPanel.add(eventTypes, topGbc);

		add(topPanel, BorderLayout.NORTH);
	}

	private void initMiddlePnl() {
		
		// initialize the panels
		initShortlistPnl();

	}
	
	private void initShortlistPnl() {
		middlePnl = new JPanel();
		middlePnl.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		// labels
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 10);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		middlePnl.add(new JLabel("Organisation: "), gbc);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		middlePnl.add(new JLabel("Vacancy: "), gbc);
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		middlePnl.add(new JLabel("Date: "), gbc);
		Utils.setGBC(gbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		middlePnl.add(new JLabel("Time: "), gbc);

		// combo boxes
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 5;
		gbc.insets = new Insets(0, 0, 0, 40);
		organisationCmbBx = new JComboBox<Organisation>();
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePnl.add(organisationCmbBx, gbc);
		vacanciesCmbBx = new JComboBox<Vacancy>();
		Utils.setGBC(gbc, 2, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePnl.add(vacanciesCmbBx, gbc);
		dateChooser = new JDateChooser(new Date());
		Utils.setGBC(gbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePnl.add(dateChooser, gbc);
		timeChooser = new JTimeChooser(new Date());
		Utils.setGBC(gbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePnl.add(timeChooser, gbc);
		
		add(middlePnl, BorderLayout.CENTER);
	}

	private void initBottomPnl() {
		// buttons
		JPanel buttonsPanel = new JPanel();
		confirmButton = new JButton("Confirm");
		buttonsPanel.add(confirmButton);
		cancelButton = new JButton("Cancel ");
		buttonsPanel.add(cancelButton);
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 1, 12, 3, 1, GridBagConstraints.HORIZONTAL);
		panel.add(buttonsPanel, gbc);

		add(panel, BorderLayout.SOUTH);
	}

	public Organisation getDisplayedOrganisation() {
		return (Organisation) organisationCmbBx.getSelectedItem();
	}
	
	public Vacancy getDisplayedVacancy() {
		return (Vacancy) vacanciesCmbBx.getSelectedItem();
	}

	@Override
	public void setDisplayedOrganisations(List<Organisation> organisations) {
		organisationCmbBx.removeAllItems();

		for (Organisation org : organisations) {
			organisationCmbBx.addItem(org);
		}
	}

	@Override
	public void setDisplayedVacancies(List<Vacancy> vacancies) {
		vacanciesCmbBx.removeAllItems();
		
		if (vacancies != null) {
			for (Vacancy vacancy : vacancies) {
				vacanciesCmbBx.addItem(vacancy);
			}
		}
	}

	public Event getEvent() {
		EventType type = (EventType) eventTypes.getSelectedItem();
		Vacancy vacancy = (Vacancy) vacanciesCmbBx.getSelectedItem();
		Date date = dateChooser.getDate();
		Time time = new Time(timeChooser.getDateWithTime(date).getTime());
		Event event = new Event(type, null, date, time, null, new Vacancy(vacancy.getVacancyId(), vacancy.getName()));
		return event;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			eventTypes.setSelectedIndex(0);
			timeChooser.setTime(new Date());
			dateChooser.setDate(new Date());
		}
		super.setVisible(visible);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		organisationCmbBx.addActionListener(actionListener);
		confirmButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);
	}
}
