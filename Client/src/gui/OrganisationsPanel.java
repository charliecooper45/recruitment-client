package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OrganisationsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// alerts the GUI when an organisation needs to be displayed to the user
	private OrganisationDisplayedListener organisationDisplayedListener;
	
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
			private String[] columns = {"Organisation", "Phone Number", "Address", "Active Vacancies", "Main Contact", "User"};

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
				return 6;
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
		organisationsTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
					organisationDisplayedListener.organisationDisplayed();
				}
			}
		});
		tableScrll = new JScrollPane(organisationsTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * @param organisationDisplayedListener the organisationDisplayedListener to set
	 */
	public void setOrganisationDisplayedListener(OrganisationDisplayedListener organisationDisplayedListener) {
		this.organisationDisplayedListener = organisationDisplayedListener;
	}
}
