package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	private JComboBox<String> reqSkillsCmBx;
	private JButton reqAddButton;
	private JList<String> reqLst;
	private JScrollPane reqScrlPane;
	private JScrollPane prefScrlPane;
	private JComboBox<String> prefSkillsCmBx;
	private JButton prefAddButton;
	private JList<String> prefLst;
	private JButton searchButton;

	// components - rightPanel
	private JPanel rightPanel;
	private JTable resultsTbl;
	private JScrollPane resultsTblScrlPane;
	private JComboBox<String> vacancyCmBx;
	private JButton shortlistBtn;
	
	// data for the results table
	private Object[][] resultsArray = {{new Boolean(true), "Charlie"}};

	public SearchPanel() {
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
		middlePanel.add(new JLabel("Required:"), middlePanelGbc);
		reqSkillsCmBx = new JComboBox<String>();
		reqSkillsCmBx.addItem("Test Item");
		Utils.setGBC(middlePanelGbc, 2, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePanel.add(reqSkillsCmBx, middlePanelGbc);
		reqAddButton = new JButton("Add");
		Utils.setGBC(middlePanelGbc, 3, 2, 1, 1, GridBagConstraints.NONE);
		middlePanel.add(reqAddButton, middlePanelGbc);
		reqLst = new JList<>();
		reqScrlPane = new JScrollPane(reqLst);
		reqScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		reqScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(middlePanelGbc, 1, 3, 3, 1, GridBagConstraints.NONE);
		middlePanel.add(reqScrlPane, middlePanelGbc);
		Utils.setGBC(middlePanelGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		middlePanel.add(new JLabel("Preferred:"), middlePanelGbc);
		prefSkillsCmBx = new JComboBox<>();
		Utils.setGBC(middlePanelGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		middlePanel.add(prefSkillsCmBx, middlePanelGbc);
		prefAddButton = new JButton("Add");
		Utils.setGBC(middlePanelGbc, 3, 4, 1, 1, GridBagConstraints.NONE);
		middlePanel.add(prefAddButton, middlePanelGbc);
		prefLst = new JList<>();
		prefScrlPane = new JScrollPane(prefLst);
		prefScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		prefScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Utils.setGBC(middlePanelGbc, 1, 5, 3, 1, GridBagConstraints.NONE);
		middlePanel.add(prefScrlPane, middlePanelGbc);

		Utils.setGBC(leftGbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		leftPanel.add(middlePanel, leftGbc);

		// bottonPanel
		bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints bottomPanelGbc = new GridBagConstraints();
		bottomPanelGbc.weightx = 1;
		bottomPanelGbc.weighty = 1;
		Insets buttonInsets = new Insets(0, 10, 0, 10);
		bottomPanelGbc.insets = buttonInsets;
		searchButton = new JButton("Search");
		searchButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		Utils.setGBC(bottomPanelGbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		bottomPanel.add(searchButton, bottomPanelGbc);

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
			private String[] columns = { "", "Name", "Skills", "Job Title", "Company", "Phone Number", "Address", "User" };

			@Override
			public Object getValueAt(int row, int column) {
				if(column == 0) 
					return resultsArray[0][0];
				return null;
			}
			
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				if(column == 0)
					resultsArray[0][0] = aValue;
			}

			@Override
			public int getRowCount() {
				return 1;
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
		rightPanelGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(rightPanelGbc, 3, 3, 1, 1, GridBagConstraints.NONE);
		rightPanel.add(shortlistBtn, rightPanelGbc);

		gbc.insets = new Insets(30, 20, 30, 20);
		gbc.weightx = 5;
		gbc.weighty = 1;
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}
}
