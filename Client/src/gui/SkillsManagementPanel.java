package gui;

import gui.listeners.SkillsManagementPanelListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.beans.Skill;

public class SkillsManagementPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// list of skills to be displayed
	private List<Skill> skills;
	
	// components - topPanel
	private JPanel topPanel;
	private JButton addSkillBtn;
	private JButton removeSkillBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable skillsTbl;
	private JScrollPane tableScrll;
	
	public SkillsManagementPanel() {
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		initTopPanel();
		initBottomPanel();
	}
	
	private void initTopPanel() {
		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		addSkillBtn = new JButton("Add Skill");
		removeSkillBtn = new JButton("Remove Skill");
		topPanel.add(addSkillBtn);
		topPanel.add(removeSkillBtn);

		add(topPanel, BorderLayout.NORTH);
	}
	
	private void initBottomPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 20, 10, 20);
		skillsTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Skill", "Usage", "Owner"};

			@Override
			public Object getValueAt(int row, int col) {
				Skill skill;

				if (skills != null) {
					skill = skills.get(row);
					switch (col) {
					case 0:
						return skill.getSkillName();
					case 1:
						return skill.getUsage();
					case 2:
						return skill.getUserId();
					}
				}
				return "";
			}

			@Override
			public int getRowCount() {
				if (skills != null) {
					return skills.size();
				} else {
					return 0;
				}
			}

			@Override
			public int getColumnCount() {
				return 3;
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
		skillsTbl.setRowHeight(30);
		skillsTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < skillsTbl.getColumnCount(); i++) {
			skillsTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		tableScrll = new JScrollPane(skillsTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}

	public void updateDisplayedSkills(List<Skill> skills) {
		this.skills = skills;
		DefaultTableModel model = (DefaultTableModel) skillsTbl.getModel();
		model.fireTableDataChanged();
	}

	public void setSkillsManagementPanelListener(SkillsManagementPanelListener listener) {
		addSkillBtn.addActionListener(listener);
		removeSkillBtn.addActionListener(listener);
	}
}
