package gui;

import gui.listeners.SearchPanelListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.beans.Candidate;
import database.beans.Search;
import database.beans.Skill;
import database.beans.Vacancy;

/**
 * Panel that allows the user to search for candidates 
 * @author Charlie
 */
public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// components - leftPanel
	private JPanel leftPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private JTextField nameTxt;
	private JTextField jobTxt;
	private JComboBox<Skill> skillsCmbBx;
	private JButton skillsAddButton;
	private JButton skillsRemoveButton;
	private JTextArea skillsTxtArea;
	private JScrollPane skillsScrlPane;
	private JButton resetSearchButton;
	private JButton searchButton;

	// components - rightPanel
	private JPanel rightPanel;
	private JTable resultsTbl;
	private JScrollPane resultsTblScrlPane;
	private JComboBox<Vacancy> vacancyCmBx;
	private JButton shortlistBtn;

	// data for the results table
	private List<Candidate> candidates;
	private List<Boolean> selected;

	// holds the search 
	private Search search;

	public SearchPanel() {
		candidates = new ArrayList<>();
		selected = new ArrayList<>();
		search = new Search();
		init();
	}

	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		initLeftPanel();
		initRightPanel();
	}

	private void initLeftPanel() {
		leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftGbc = new GridBagConstraints();
		leftGbc.weightx = 1;
		leftGbc.weighty = 1;

		// topPanel
		topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints topPanelGbc = new GridBagConstraints();
		topPanelGbc.weightx = 1;
		topPanelGbc.weighty = 1;
		Utils.setGBC(topPanelGbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("Name:"), topPanelGbc);
		Utils.setGBC(topPanelGbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("Job Title:"), topPanelGbc);
		topPanelGbc.weightx = 5;
		topPanelGbc.weighty = 5;
		nameTxt = new JTextField(20);
		Utils.setGBC(topPanelGbc, 2, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(nameTxt, topPanelGbc);
		jobTxt = new JTextField(20);
		Utils.setGBC(topPanelGbc, 2, 2, 1, 1, GridBagConstraints.NONE);
		topPanel.add(jobTxt, topPanelGbc);
		Utils.setGBC(leftGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		leftPanel.add(topPanel, leftGbc);

		// middlePanel
		middlePanel = new JPanel(new GridBagLayout());
		GridBagConstraints middlePanelGbc = new GridBagConstraints();
		middlePanelGbc.weightx = 1;
		middlePanelGbc.weighty = 1;
		middlePanelGbc.anchor = GridBagConstraints.CENTER;
		
		Utils.setGBC(middlePanelGbc, 1, 1, 3, 1, GridBagConstraints.NONE);
		middlePanel.add(new JLabel("Skills Search:"), middlePanelGbc);
		
		Utils.setGBC(middlePanelGbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		skillsCmbBx = new JComboBox<>();
		middlePanel.add(skillsCmbBx, middlePanelGbc);
		
		Utils.setGBC(middlePanelGbc, 2, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		skillsAddButton = new JButton("Add");
		skillsAddButton.setName("AddSkillButton");
		middlePanel.add(skillsAddButton, middlePanelGbc);
		
		Utils.setGBC(middlePanelGbc, 3, 2, 1, 1, GridBagConstraints.NONE);
		skillsRemoveButton = new JButton("Remove");
		skillsRemoveButton.setName("RemoveSkillButton");
		middlePanel.add(skillsRemoveButton, middlePanelGbc);

		// add the scroll bars and text areas
		Insets insets = new Insets(0, 10, 0, 10);
		middlePanelGbc.insets = insets;
		middlePanelGbc.weighty = 4;
		skillsTxtArea = new JTextArea();
		skillsTxtArea.setLineWrap(true);
		skillsTxtArea.setFont(skillsTxtArea.getFont().deriveFont(Font.PLAIN, 15));
		skillsScrlPane = new JScrollPane(skillsTxtArea);
		skillsScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		skillsScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(middlePanelGbc, 1, 3, 3, 1, GridBagConstraints.BOTH);
		middlePanel.add(skillsScrlPane, middlePanelGbc);

		Utils.setGBC(leftGbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		leftPanel.add(middlePanel, leftGbc);

		// bottomPanel
		bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints bottomPanelGbc = new GridBagConstraints();
		bottomPanelGbc.weightx = 1;
		bottomPanelGbc.weighty = 1;
		Insets buttonInsets = new Insets(0, 10, 0, 10);
		bottomPanelGbc.insets = buttonInsets;
		searchButton = new JButton("Search");
		searchButton.setName("SearchButton");
		searchButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		Utils.setGBC(bottomPanelGbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		bottomPanel.add(searchButton, bottomPanelGbc);
		resetSearchButton = new JButton("Reset Search");
		resetSearchButton.setName("ResetSearchButton");
		resetSearchButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		Utils.setGBC(bottomPanelGbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		bottomPanel.add(resetSearchButton, bottomPanelGbc);

		Utils.setGBC(leftGbc, 1, 3, 1, 1, GridBagConstraints.BOTH);
		leftPanel.add(bottomPanel, leftGbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(leftPanel, gbc);
	}

	private void initRightPanel() {
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;
		rightPanelGbc.anchor = GridBagConstraints.LINE_START;
		JLabel resultsLabel = new JLabel("Results:");
		resultsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		rightPanel.add(resultsLabel, rightPanelGbc);
		resultsTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			//TODO NEXT: implement the candidate skills, these need to be shown to the user
			private String[] columns = { "", "Name", "Skills", "Job Title", "Phone Number", "Address", "User" };

			@Override
			public Object getValueAt(int row, int column) {
				switch (column) {
				case 0:
					return selected.get(row);
				case 1:
					return candidates.get(row).getFirstName() + " " + candidates.get(row).getSurname();
				case 2:
					return "";
				case 3:
					return candidates.get(row).getJobTitle();
				case 4:
					return candidates.get(row).getPhoneNumber();
				case 5:
					return candidates.get(row).getAddress();
				case 6:
					return candidates.get(row).getUserId();
				}
				return "";
			}

			@Override
			public void setValueAt(Object aValue, int row, int column) {
				if (column == 0)
					selected.set(row, (Boolean) aValue);
			}

			@Override
			public int getRowCount() {
				return candidates.size();
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
			public Class<?> getColumnClass(int col) {
				if (col == 0) {
					return Boolean.class;
				}
				return super.getColumnClass(col);
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 0;
			}
		});
		resultsTbl.setRowHeight(30);
		resultsTblScrlPane = new JScrollPane(resultsTbl);
		resultsTblScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resultsTblScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rightPanelGbc.weighty = 10;
		Utils.setGBC(rightPanelGbc, 1, 2, 3, 1, GridBagConstraints.BOTH);
		rightPanel.add(resultsTblScrlPane, rightPanelGbc);
		rightPanelGbc.anchor = GridBagConstraints.LINE_END;
		rightPanelGbc.weighty = 1;
		Utils.setGBC(rightPanelGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		rightPanel.add(new JLabel("Current Vacancy:"), rightPanelGbc);
		vacancyCmBx = new JComboBox<>();
		rightPanelGbc.anchor = GridBagConstraints.CENTER;
		rightPanelGbc.insets = new Insets(0, 10, 0, 10);
		Utils.setGBC(rightPanelGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		rightPanel.add(vacancyCmBx, rightPanelGbc);
		shortlistBtn = new JButton("   Add to shortlist   ");
		shortlistBtn.setName("AddShortlistButton");
		rightPanelGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(rightPanelGbc, 3, 3, 1, 1, GridBagConstraints.NONE);
		rightPanel.add(shortlistBtn, rightPanelGbc);

		gbc.insets = new Insets(30, 20, 30, 20);
		gbc.weightx = 5;
		gbc.weighty = 1;
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}

	public void setDefaultOptions() {
		candidates = new ArrayList<>();
		selected = new ArrayList<>();
		search = new Search();
		skillsTxtArea.setText("");
		nameTxt.setText("");
		jobTxt.setText("");
		
		DefaultTableModel model = (DefaultTableModel) resultsTbl.getModel();
		model.fireTableDataChanged();
	}

	public void updateDisplayedSkills(List<Skill> skills) {
		skillsCmbBx.removeAllItems();

		for (Skill skill : skills) {
			skillsCmbBx.addItem(skill);
		}
	}

	public void updateDisplayedVacancies(List<Vacancy> vacancies) {
		vacancyCmBx.removeAllItems();

		for (Vacancy vacancy : vacancies) {
			vacancyCmBx.addItem(vacancy);
		}
	}

	public void addSkillToSearch() {
		Skill skill = (Skill) skillsCmbBx.getSelectedItem();

		if (search.getSkills().size() < 5) {
			if (!search.getSkills().contains(skill)) {
				if (!search.getSkills().isEmpty())
					skillsTxtArea.append("\n");

				search.getSkills().add(skill);
				skillsTxtArea.append(skill.toString());
			}
		}
	}
	
	public void removeSkillFromSearch() {
		Skill skill = (Skill) skillsCmbBx.getSelectedItem();
		Set<Skill> skills = search.getSkills();
		
		if(skills.contains(skill)) {
			skills.remove(skill);
			
			skillsTxtArea.setText("");
			
			for(Skill aSkill : skills) {
				if(!skillsTxtArea.getText().trim().isEmpty()) {
					skillsTxtArea.append("\n");
				}
				skillsTxtArea.append(aSkill.toString());
			}
		}
	}

	public Search getSearch() {
		search.setName(nameTxt.getText());
		search.setJobTitle(jobTxt.getText());
		return search;
	}

	public void updateDisplayedCandidates(List<Candidate> candidates) {
		this.candidates = new ArrayList<>();
		this.selected = new ArrayList<>();

		for (Candidate candidate : candidates) {
			this.candidates.add(candidate);
			this.selected.add(false);
		}

		DefaultTableModel model = (DefaultTableModel) resultsTbl.getModel();
		model.fireTableDataChanged();
	}

	public Candidate getSelectedCandidate() {
		return candidates.get(resultsTbl.getSelectedRow());
	}
	
	public void setSearchPanelListener(SearchPanelListener searchPanelListener) {
		searchButton.addActionListener(searchPanelListener);
		skillsAddButton.addActionListener(searchPanelListener);
		skillsRemoveButton.addActionListener(searchPanelListener);
		resetSearchButton.addActionListener(searchPanelListener);
		shortlistBtn.addActionListener(searchPanelListener);
		resultsTbl.addMouseListener(searchPanelListener);
	}
}
