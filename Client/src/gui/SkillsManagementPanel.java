package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SkillsManagementPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components - topPanel
	private JPanel topPanel;
	private JButton addSkillBtn;
	private JButton delSkillBtn;

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
		delSkillBtn = new JButton("Delete Skill");
		topPanel.add(addSkillBtn);
		topPanel.add(delSkillBtn);

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
				return "Test Data";
			}

			@Override
			public int getRowCount() {
				return 5;
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
}
