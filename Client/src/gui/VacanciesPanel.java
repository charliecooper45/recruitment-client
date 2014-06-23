package gui;

import gui.listeners.VacanciesPanelListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.beans.User;
import database.beans.Vacancy;

/**
 * Displays the vacancies to the user
 * @author Charlie
 */
public class VacanciesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// list of vacancies to be displayed
	private List<Vacancy> vacancies;
	
	// components - topPanel
	private JPanel topPanel;
	private ButtonGroup group;
	private JRadioButton openVacanciesRdBtn;
	private JRadioButton allVacanciesRdBtn;
	private JComboBox<User> userCombo;
	
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
		openVacanciesRdBtn.setSelected(true);
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
		userCombo = new JComboBox<>();
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
			private String[] columns = {"Job Title", "Organisation", "Date Added", "User", "CVs Sent", "Interviews", "Status"};

			@Override
			public Object getValueAt(int row, int column) {
				Vacancy vacancy; 
				
				if(vacancies != null) {
					vacancy = vacancies.get(row);
					switch(column) {
					case 0:
						return vacancy.getName();
					case 1:
						return vacancy.getOrganisationName();
					case 2:
						return vacancy.getVacancyDate();
					case 3:
						return vacancy.getUserId();
					case 4:
						// TODO NEXT: Fill in the CV sent and interview fields for vacancy
						return 0;
					case 5:
						return 0;
					case 6:
						if(vacancy.getStatus() == true) {
							return "Open";
						} else { 
							return "Closed";
						}
					}
				}
				return "";
			}
			
			@Override
			public int getRowCount() {
				if(vacancies != null) {
					return vacancies.size();
				} else { 
					return 0;
				}
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
		tableScrll = new JScrollPane(vacanciesTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	public void setDefaultOptions() {
		openVacanciesRdBtn.setSelected(true);
	}
	
	public void updateDisplayedVacancies(List<Vacancy> vacancies) {
		this.vacancies = vacancies;
		DefaultTableModel model = (DefaultTableModel) vacanciesTbl.getModel();
		model.fireTableDataChanged();
	}

	public void updateDisplayedUsers(List<User> users) {
		userCombo.removeAllItems();
		
		// add the default all users option
		userCombo.addItem(new User(null, null, "All", "Users", null, null, false, null));
		
		// add the users stored on the database
		for(User user: users) {
			userCombo.addItem(user);
		}
	}
	
	public Vacancy getSelectedVacancy() {
		return vacancies.get(vacanciesTbl.getSelectedRow());
	}
	
	public void setVacanciesPanelListener(VacanciesPanelListener vacanciesPanelListener) {
		openVacanciesRdBtn.addActionListener(vacanciesPanelListener);
		allVacanciesRdBtn.addActionListener(vacanciesPanelListener);
		userCombo.addActionListener(vacanciesPanelListener);
		vacanciesTbl.addMouseListener(vacanciesPanelListener);
	}
}
