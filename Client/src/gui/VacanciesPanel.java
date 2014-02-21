package gui;

import gui.listeners.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Displays the vacancies to the user
 * @author Charlie
 */
public class VacanciesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// alerts the GUI when a vacancy needs to be displayed to the user
	private VacancyDisplayedListener vacancyDisplayedListener;
	
	// components - topPanel
	private JPanel topPanel;
	private ButtonGroup group;
	private JRadioButton openVacanciesRdBtn;
	private JRadioButton allVacanciesRdBtn;
	private JComboBox<String> userCombo;
	
	// components - mainPanel
	private JPanel mainPanel;
	private JTable vacanciesTbl;
	private JScrollPane tableScrll;
	
	public VacanciesPanel() {
		setLayout(new BorderLayout());
		init();
	}
	
	private void init() {
		initTopPanel();
		initMainPanel();
	}
	
	private void initTopPanel() {
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Insets leftInsets = new Insets(30, 20, 0, 0);
		Insets rightInsets = new Insets(30, 0, 0, 20);
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		// left JPanel
		group = new ButtonGroup();
		openVacanciesRdBtn = new JRadioButton("Open Vacancies");
		group.add(openVacanciesRdBtn);
		leftPanel.add(openVacanciesRdBtn);
		allVacanciesRdBtn = new JRadioButton("All Vacancies");
		group.add(allVacanciesRdBtn);
		leftPanel.add(allVacanciesRdBtn);
		gbc.insets = leftInsets;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(leftPanel, gbc);
		
		// right JPanel
		rightPanel.add(new JLabel("User:"));
		userCombo = new JComboBox<String>();
		userCombo.addItem("All Users");
		userCombo.addItem("CC01 - Charlie Cooper");
		userCombo.addItem("MC01 - Martine Cooper");
		rightPanel.add(userCombo);
		gbc.insets = rightInsets;
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(rightPanel, gbc);
		
		add(topPanel, BorderLayout.NORTH);
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(30, 20, 30, 20);
		vacanciesTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Job Title", "Organisation", "Start Date", "User", "CVs Sent", "Interviews", "Closed"};

			@Override
			public Object getValueAt(int arg0, int arg1) {
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
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		vacanciesTbl.setRowHeight(30);
		
		vacanciesTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
					vacancyDisplayedListener.vacancyDisplayed();
				}
			}
		});
		
		tableScrll = new JScrollPane(vacanciesTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * @param vacancyDisplayedListener the vacancyDisplayedListener to set
	 */
	public void setVacancyDisplayedListener(VacancyDisplayedListener vacancyDisplayedListener) {
		this.vacancyDisplayedListener = vacancyDisplayedListener;
	}
}
