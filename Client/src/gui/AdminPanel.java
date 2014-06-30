package gui;

import gui.listeners.AdminPanelListener;
import gui.listeners.SkillsManagementPanelListener;
import gui.listeners.UserManagementPanelListener;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.beans.Skill;
import database.beans.User;

public class AdminPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components
	private JTabbedPane tabbedPane;
	private UserManagementPanel userManagementPanel;
	private SkillsManagementPanel skillsManagementPanel;
	
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
		skillsManagementPanel = new SkillsManagementPanel();
		tabbedPane.addTab("User management", userManagementPanel);
		tabbedPane.addTab("Skills management", skillsManagementPanel);
		tabbedPane.addTab("Activity report", new ReportPanel());
		
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(tabbedPane, gbc);
	}
	
	public void updateDisplayedUsers(List<User> users) {
		tabbedPane.setSelectedIndex(0);		
		userManagementPanel.updateDisplayedUsers(users);
	}
	
	public void updateDisplayedSkills(List<Skill> skills) {
		tabbedPane.setSelectedIndex(1);		
		skillsManagementPanel.updateDisplayedSkills(skills);
	}
	
	public User getSelectedUser() {
		return userManagementPanel.getSelectedUser();
	}
	
	public void setAdminPanelListener(AdminPanelListener listener, UserManagementPanelListener userListener, SkillsManagementPanelListener skillListener) {
		tabbedPane.addMouseListener(listener);
		userManagementPanel.setUserManagementPanelListener(userListener);
		skillsManagementPanel.setSkillsManagementPanelListener(skillListener);
	}
}
