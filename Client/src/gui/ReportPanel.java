package gui;

import gui.listeners.ReportPanelListener;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import database.beans.EventType;
import database.beans.Report;
import database.beans.ReportType;
import database.beans.User;

public class ReportPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Map<User, Map<EventType, Integer>> userResults;
	private List<User> users = new ArrayList<>();
	
	private GridBagConstraints gbc;
	
	// components - topPanel
	private JPanel topPanel;
	private JComboBox<ReportType> reportTypeCmbBox;
	private JDateChooser fromDateChooser;
	private JDateChooser toDateChooser;
	private JButton getReportBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable userTbl;
	private JScrollPane userTblScrll;
	private JTable vacancyTbl;
	private JScrollPane vacancyTblScrll;
	private JTable orgTbl;
	private JScrollPane orgTblScrll;
	
	public ReportPanel() {
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		initTopPanel();
		initBottomPanel();
	}
	
	private void initTopPanel() {
		topPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		Insets defaultInsets = new Insets(20, 0, 20, 0);
		gbc.insets = defaultInsets;
		
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("Report Type:"), gbc);
		
		gbc.anchor = GridBagConstraints.LINE_START;
		reportTypeCmbBox = new JComboBox<>();
		reportTypeCmbBox.addItem(ReportType.CONSULTANT);
		reportTypeCmbBox.addItem(ReportType.VACANCY);
		reportTypeCmbBox.addItem(ReportType.ORGANISATION);
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(reportTypeCmbBox, gbc);
		
		fromDateChooser = new JDateChooser();
		Utils.setGBC(gbc, 4, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(fromDateChooser, gbc);
		
		toDateChooser = new JDateChooser();
		Utils.setGBC(gbc, 6, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(toDateChooser, gbc);
		
		gbc.anchor = GridBagConstraints.CENTER;
		Utils.setGBC(gbc, 3, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("From:"), gbc);
		
		Utils.setGBC(gbc, 5, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("To:"), gbc);
		
		getReportBtn = new JButton("Get Report");
		getReportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
				
				// change the panel that is shown according to the selected item
				ReportType selectedItem = (ReportType) reportTypeCmbBox.getSelectedItem();
				switch(selectedItem) {
				case CONSULTANT:
					mainPanel.removeAll();
					mainPanel.add(userTblScrll, gbc);
					break;
				case VACANCY:
					mainPanel.removeAll();
					mainPanel.add(vacancyTblScrll, gbc);
					break;
				case ORGANISATION:
					mainPanel.removeAll();
					mainPanel.add(orgTblScrll, gbc);
					break;
				}
				
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		Utils.setGBC(gbc, 7, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(getReportBtn, gbc);

		add(topPanel, BorderLayout.NORTH);
	}
	
	private void initBottomPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 20, 10, 20);
		
		// initialise the tables
		initConsultantTbl();
		initVacancyTbl();
		initOrgTbl();
		
		// by default show the CONSULTANT table
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(userTblScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initConsultantTbl() {
		userTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Consultant ID", "Consultant Name", "Shortlists", "CVs Sent", "Phone Interviews", "Interviews", "Placements"};

			@Override
			public Object getValueAt(int row, int col) {
				User user;
				
				if(userResults != null) {
					user = users.get(row);
					switch(col) {
					case 0:
						return user.getUserId();
					case 1:
						return user.getFirstName() + " " + user.getSurname();
					case 2: 
						return userResults.get(user).get(EventType.SHORTLIST);
					case 3: 
						return userResults.get(user).get(EventType.CV_SENT);
					case 4:
						return userResults.get(user).get(EventType.PHONE_INTERVIEW);
					case 5:
						int totalInterviews = 0;
						totalInterviews += userResults.get(user).get(EventType.INTERVIEW_1);
						totalInterviews += userResults.get(user).get(EventType.INTERVIEW_2);
						totalInterviews += userResults.get(user).get(EventType.INTERVIEW_3);
						totalInterviews += userResults.get(user).get(EventType.INTERVIEW_4);
						totalInterviews += userResults.get(user).get(EventType.FINAL_INTERVIEW);
						return totalInterviews;
					case 6:
						return userResults.get(user).get(EventType.PLACEMENT);
					}
				}
				return null;
			}

			@Override
			public int getRowCount() {
				return users.size();
			}

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		userTbl.setRowHeight(30);
		userTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < userTbl.getColumnCount(); i++) {
			userTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		userTblScrll = new JScrollPane(userTbl);
		userTblScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		userTblScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	private void initVacancyTbl() {
		vacancyTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Vacancy Name", "Organisation", "CVs Sent", "1st Interviews", "2nd Interviews", "3rd Interviews", "Final Interviews", "Status"};

			@Override
			public Object getValueAt(int row, int col) {
				return "Test Data";
			}

			@Override
			public int getRowCount() {
				return 5;
			}

			@Override
			public int getColumnCount() {
				return 8;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		vacancyTbl.setRowHeight(30);
		vacancyTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < vacancyTbl.getColumnCount(); i++) {
			vacancyTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		vacancyTblScrll = new JScrollPane(vacancyTbl);
		vacancyTblScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		vacancyTblScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	private void initOrgTbl() {
		orgTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Organisation Name", "Open Vacancies", "Closed Vacancies", "CVs Sent", "Interviews", "Placements"};

			@Override
			public Object getValueAt(int row, int col) {
				return "Test Data";
			}

			@Override
			public int getRowCount() {
				return 5;
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
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		orgTbl.setRowHeight(30);
		orgTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < orgTbl.getColumnCount(); i++) {
			orgTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		orgTblScrll = new JScrollPane(orgTbl);
		orgTblScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		orgTblScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public Report getReport() {
		StringBuilder errorMessage = new StringBuilder("");
		
		Date fromDate = fromDateChooser.getDate();
		Date toDate = toDateChooser.getDate();
		
		if(fromDate == null || toDate == null) {
			errorMessage.append("Date fields cannot be empty.\n");
		}
		
		if(errorMessage.toString().trim().equals("")) {
			return new Report((ReportType) reportTypeCmbBox.getSelectedItem(), fromDateChooser.getDate(), toDateChooser.getDate());
		} else {
			// display an error message to the user
			JOptionPane.showMessageDialog(this, errorMessage.toString(), "Cannot get report", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public void updateDisplayedReport(Map<User, Map<EventType, Integer>> results) {
		this.userResults = results;
		this.users = new ArrayList<>(results.keySet());
		Collections.sort(users);
		DefaultTableModel model = (DefaultTableModel) userTbl.getModel();
		model.fireTableDataChanged();
	}
	
	public void setReportPanelListener(ReportPanelListener listener) {
		getReportBtn.addActionListener(listener);
	}
}
