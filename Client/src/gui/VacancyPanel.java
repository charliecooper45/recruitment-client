package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class VacancyPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel vacancyNameLbl;
	private JLabel createdByLbl;
	private JComboBox<String> statusCmbBox;
	private JTextField dateTxtFld;
	private JTextField contactTxtFld;
	private JTextField phoneNoTxtFld;
	
	// components = leftBottomPanel
	private JPanel leftBottomPanel;
	
	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	
	public VacancyPanel() {
		init();
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		initLeftTopPanel();
		initLeftBottomPanel();
		initRightPanel();
	}
	
	private void initLeftTopPanel() {
		leftTopPanel = new JPanel(new GridBagLayout());
		leftTopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftTopPnlGbc = new GridBagConstraints();
		leftTopPnlGbc.weightx = 1;
		leftTopPnlGbc.weighty = 1;
		leftTopPnlGbc.anchor = GridBagConstraints.CENTER;
		
		vacancyNameLbl = new JLabel("Project Co-Ordinator @ Accenture");
		vacancyNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(vacancyNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("Created by: MC01");
		Utils.setGBC(leftTopPnlGbc, 1, 2, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);
		
		// labels
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Job Status:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Added:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact Phone Number:"), leftTopPnlGbc);
		
		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		statusCmbBox = new JComboBox<>();
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(statusCmbBox, leftTopPnlGbc);
		dateTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(dateTxtFld, leftTopPnlGbc);
		contactTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(contactTxtFld, leftTopPnlGbc);
		phoneNoTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(phoneNoTxtFld, leftTopPnlGbc);
		
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(leftTopPanel, gbc);
	}
	
	private void initLeftBottomPanel() {
		leftBottomPanel = new JPanel(new GridBagLayout());
		leftBottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftBottomPanelGbc = new GridBagConstraints();
		leftBottomPanelGbc.weightx = 1;
		leftBottomPanelGbc.weighty = 1;
		
		Utils.setGBC(leftBottomPanelGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftBottomPanel.add(new JLabel("Vacancy Options:"), leftBottomPanelGbc);

		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		add(leftBottomPanel, gbc);
	}
	
	private void initRightPanel() {
		gbc.weightx = 4;
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Role Profile", new JPanel());
		tabbedPane.addTab("Shortlist", new JPanel());
		tabbedPane.addTab("Progress Report", new JPanel());
		tabbedPane.addTab("Notes", new JPanel());
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);
		
		Utils.setGBC(gbc, 2, 1, 1, 2, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}
}
