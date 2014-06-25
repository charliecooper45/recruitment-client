package gui;

import gui.listeners.AdminPanelListener;
import gui.listeners.UserManagementPanelListener;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.beans.User;

public class AdminPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components
	private JTabbedPane tabbedPane;
	private UserManagementPanel userManagementPanel;
	
	public AdminPanel() {
		init();
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		userManagementPanel = new UserManagementPanel();
		tabbedPane.addTab("User management", userManagementPanel);
		tabbedPane.addTab("Skills management", new SkillsManagementPanel());
		tabbedPane.addTab("Activity report", new ReportPanel());
		tabbedPane.addTab("Upload candidates", new JPanel());
		
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(tabbedPane, gbc);
	}
	
	public void updateDisplayedUsers(List<User> users) {
		tabbedPane.setSelectedIndex(0);		
		userManagementPanel.updateDisplayedUsers(users);
	}
	
	public void setAdminPanelListener(AdminPanelListener listener, UserManagementPanelListener userManagementPanelListener) {
		tabbedPane.addMouseListener(listener);
		
		userManagementPanel.setUserManagementPanelListener(userManagementPanelListener);
	}

}
