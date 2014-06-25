package gui;

import gui.listeners.CandidatePipelinePanelListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.beans.Event;

/**
 * Displays the pipeline (current user or everyones) to the user
 * @author Charlie
 */
public class CandidatePipelinePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// list of events to be displayed
	private List<Event> events;

	// components - topPanel
	private JPanel topPanel;
	private JCheckBox[] chckBoxes;
	private JCheckBox shortlistedChckBox;
	private JCheckBox cvsChckBox;
	private JCheckBox interviewsChckBox;
	private JCheckBox placementsChckBox;
	private ButtonGroup group;
	private JRadioButton myPipelineRdBtn;
	private JRadioButton companyPipelineRdBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable pipelineTbl;
	private JScrollPane tableScrll;

	public CandidatePipelinePanel() {
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		initTopPanel();
		initMainPanel();
	}

	private void initTopPanel() {
		JPanel leftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rightJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Insets leftInsets = new Insets(30, 20, 0, 0);
		Insets rightInsets = new Insets(30, 0, 0, 20);

		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		// left JPanel
		shortlistedChckBox = new JCheckBox("Shortlisted Candidates");
		cvsChckBox = new JCheckBox("CVs Sent");
		interviewsChckBox = new JCheckBox("Interviews");
		placementsChckBox = new JCheckBox("Placements");
		chckBoxes = new JCheckBox[4];
		chckBoxes[0] = shortlistedChckBox;
		chckBoxes[1] = cvsChckBox;
		chckBoxes[2] = interviewsChckBox;
		chckBoxes[3] = placementsChckBox;
		for (JCheckBox box : chckBoxes) {
			leftJPanel.add(box);
		}
		gbc.insets = leftInsets;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(leftJPanel, gbc);

		// right JPanel
		group = new ButtonGroup();
		myPipelineRdBtn = new JRadioButton("My Pipeline");
		myPipelineRdBtn.setSelected(true);
		companyPipelineRdBtn = new JRadioButton("Company Pipeline");
		group.add(myPipelineRdBtn);
		rightJPanel.add(myPipelineRdBtn);
		group.add(companyPipelineRdBtn);
		rightJPanel.add(companyPipelineRdBtn);
		gbc.insets = rightInsets;
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(rightJPanel, gbc);

		add(topPanel, BorderLayout.NORTH);
	}

	private void initMainPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(30, 20, 30, 20);
		pipelineTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Candidate", "Event", "Job Title", "Organisation", "Date", "User" };

			@Override
			public Object getValueAt(int row, int column) {
				Event event;

				if (events != null) {
					event = events.get(row);
					switch (column) {
					case 0:
						return event.getCandidate();
					case 1:
						return event.getEventType();
					case 2:
						return event.getVacancyName();
					case 3:
						return event.getVacancyOrganisation();
					case 4:
						return event.getDate();
					case 5:
						return event.getUserId();
					}
				}
				return "";
			}

			@Override
			public int getRowCount() {
				if (events != null) {
					return events.size();
				} else {
					return 0;
				}
			}

			@Override
			public int getColumnCount() {
				return 6;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		pipelineTbl.setRowHeight(30);
		tableScrll = new JScrollPane(pipelineTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}

	public boolean[] getOptions() {
		boolean[] options = new boolean[5];
		options[0] = shortlistedChckBox.isSelected();
		options[1] = cvsChckBox.isSelected();
		options[2] = interviewsChckBox.isSelected();
		options[3] = placementsChckBox.isSelected();
		options[4] = myPipelineRdBtn.isSelected();

		return options;
	}

	public void updateDisplayedEvents(List<Event> events) {
		this.events = events;

		Collections.sort(events, new Comparator<Event>() {
			@Override
			public int compare(Event event1, Event event2) {
				int compare = event2.getDate().compareTo(event1.getDate());

				if (compare == 0) {
					if(event1.getTime() != null && event2.getTime() != null) {
						compare = event2.getTime().compareTo(event1.getTime());
					}
				}
				return compare;
			}
		});
		DefaultTableModel model = (DefaultTableModel) pipelineTbl.getModel();
		model.fireTableDataChanged();
	}

	public void setCandidatePipelinePanelListener(CandidatePipelinePanelListener candidatePipelineListener) {
		shortlistedChckBox.addActionListener(candidatePipelineListener);
		cvsChckBox.addActionListener(candidatePipelineListener);
		interviewsChckBox.addActionListener(candidatePipelineListener);
		placementsChckBox.addActionListener(candidatePipelineListener);
		myPipelineRdBtn.addActionListener(candidatePipelineListener);
		companyPipelineRdBtn.addActionListener(candidatePipelineListener);
	}
}
