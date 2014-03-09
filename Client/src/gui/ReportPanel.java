package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class ReportPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components - topPanel
	private JPanel topPanel;
	private JComboBox<String> reportTypeCmbBox;
	private JDateChooser fromDateChooser;
	private JDateChooser toDateChooser;
	private JButton getReportBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable consultantTbl;
	private JScrollPane consultantTblScrll;
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
		reportTypeCmbBox.addItem("CONSULTANT");
		reportTypeCmbBox.addItem("VACANCY");
		reportTypeCmbBox.addItem("ORGANISATION");
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
				String selectedItem = (String) reportTypeCmbBox.getSelectedItem();
				switch(selectedItem) {
				case "CANDIDATE":
					mainPanel.removeAll();
					mainPanel.add(consultantTblScrll, gbc);
					break;
				case "VACANCY":
					mainPanel.removeAll();
					mainPanel.add(vacancyTblScrll, gbc);
					break;
				case "ORGANISATION":
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
		mainPanel.add(consultantTblScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initConsultantTbl() {
		consultantTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Consultant ID", "Consultant Name", "CVs Sent", "1st Interviews", "2nd Interviews", "3rd Interviews", "Final Interviews",
					"Placements", "Candidates Created", "Vacancies Created", "Organisations Created"};

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
				return 11;
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
		consultantTbl.setRowHeight(30);
		consultantTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < consultantTbl.getColumnCount(); i++) {
			consultantTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		consultantTblScrll = new JScrollPane(consultantTbl);
		consultantTblScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		consultantTblScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	private void initVacancyTbl() {
		vacancyTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Vacancy Name", "Organisation", "CVs Sent", "1st Interviews", "2nd Interviews", "3rd Interviews", "Final Interviews"};

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
				return 7;
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
}
