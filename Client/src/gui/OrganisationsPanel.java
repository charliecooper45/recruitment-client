package gui;

import gui.listeners.OrganisationsPanelListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.beans.Organisation;

public class OrganisationsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// list of organisations to be displayed
	private List<Organisation> organisations;

	// components - topPanel
	private JPanel topPanel;
	private JTextField searchTxt;
	private JButton searchBtn;
	private JButton showAllBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable organisationsTbl;
	private JScrollPane tableScrll;

	public OrganisationsPanel() {
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		initTopPanel();
		initMainPanel();
	}

	private void initTopPanel() {
		JPanel leftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Insets leftInsets = new Insets(30, 20, 0, 0);

		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		// left JPanel
		searchTxt = new JTextField(30);
		searchTxt.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		leftJPanel.add(searchTxt);
		searchBtn = new JButton("Search");
		leftJPanel.add(searchBtn);
		showAllBtn = new JButton("Show All");
		leftJPanel.add(showAllBtn);
		gbc.insets = leftInsets;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(leftJPanel, gbc);

		add(topPanel, BorderLayout.NORTH);
	}

	private void initMainPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(30, 20, 30, 20);
		organisationsTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Organisation", "Phone Number", "Address", "Active Vacancies", "User" };

			@Override
			public Object getValueAt(int row, int column) {
				Organisation organisation;

				if (organisations != null) {
					organisation = organisations.get(row);
					switch (column) {
					case 0:
						return organisation.getOrganisationName();
					case 1:
						return organisation.getPhoneNumber();
					case 2:
						return organisation.getAddress();
					case 3:
						return organisation.getNoOpenVacancies();
					case 4:
						return organisation.getUserId();
					}
				}
				return "test Data";
			}

			@Override
			public int getRowCount() {
				if (organisations != null) {
					return organisations.size();
				} else {
					return 0;
				}
			}

			@Override
			public int getColumnCount() {
				return 5;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		});
		organisationsTbl.setRowHeight(30);
		tableScrll = new JScrollPane(organisationsTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}

	public void setDefaultOptions() {
		searchTxt.setText("");
	}
	
	public void updateDisplayedOrganisations(List<Organisation> organisations) {
		this.organisations = organisations;
		DefaultTableModel model = (DefaultTableModel) organisationsTbl.getModel();
		model.fireTableDataChanged();
	}

	public String getSearchTerm() {
		String searchTermString = searchTxt.getText();
		
		if(searchTermString.trim().isEmpty()) {
			return null;
		} else {
			return searchTermString;
		}
	}
	
	public void removeSearchTerm() {
		searchTxt.setText("");
	}
	
	public Organisation getSelectedOrganisation() {
		return organisations.get(organisationsTbl.getSelectedRow());
	}
	
	public void setOrganisationsPanelListener(OrganisationsPanelListener organisationsPanelListener) {
		searchBtn.addActionListener(organisationsPanelListener);
		showAllBtn.addActionListener(organisationsPanelListener);
		organisationsTbl.addMouseListener(organisationsPanelListener);
	}
}
