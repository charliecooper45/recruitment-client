package gui;

import gui.listeners.AdminPanelListener;
import gui.listeners.ReportPanelListener;
import gui.listeners.SkillsManagementPanelListener;
import gui.listeners.UserManagementPanelListener;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Report;
import database.beans.ReportType;
import database.beans.Skill;
import database.beans.User;
import database.beans.Vacancy;

public class AdminPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;
	
	// components
	private JTabbedPane tabbedPane;
	private UserManagementPanel userManagementPanel;
	private SkillsManagementPanel skillsManagementPanel;
	private ReportPanel reportPanel;
	
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
		reportPanel = new ReportPanel();
		tabbedPane.addTab("User management", userManagementPanel);
		tabbedPane.addTab("Skills management", skillsManagementPanel);
		tabbedPane.addTab("Activity report", reportPanel);
		
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
	
	public void updateDisplayedUserReport(Map<User, Map<EventType, Integer>> results) {
		tabbedPane.setSelectedIndex(2);
		reportPanel.updateUserReport(results);
	}
	
	public void updateDisplayedVacancyReport(Map<Vacancy, Map<EventType, Integer>> results) {
		tabbedPane.setSelectedIndex(2);
		reportPanel.updateVacancyReport(results);
	}
	
	public void updateDisplayedOrganisationReport(Map<Organisation, Map<Boolean, Integer>> results) {
		tabbedPane.setSelectedIndex(2);
		reportPanel.updateOrganisationReport(results);
	}
	
	public User getSelectedUser() {
		return userManagementPanel.getSelectedUser();
	}
	
	public Skill getSelectedSkill() {
		return skillsManagementPanel.getSelectedSkill();
	}
	
	public Report getReport() {
		return reportPanel.getReport();
	}
	
	public void changeDisplayedTable(ReportType reportType) {
		reportPanel.changeDisplayedTable(reportType);
	}
	
	public void setAdminPanelListener(AdminPanelListener listener, UserManagementPanelListener userListener, SkillsManagementPanelListener skillListener, ReportPanelListener reportListener) {
		tabbedPane.addMouseListener(listener);
		userManagementPanel.setUserManagementPanelListener(userListener);
		skillsManagementPanel.setSkillsManagementPanelListener(skillListener);
		reportPanel.setReportPanelListener(reportListener);
	}
}
