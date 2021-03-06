package gui;

import gui.TopMenuPanel.MenuPanel;
import gui.listeners.AddCandidateDialogListener;
import gui.listeners.AddContactDialogListener;
import gui.listeners.AddEventDialogListener;
import gui.listeners.AddLinkedInProfileListener;
import gui.listeners.AddOrganisationDialogListener;
import gui.listeners.AddCandidateSkillDialogListener;
import gui.listeners.AddSkillDialogListener;
import gui.listeners.AddTaskDialogListener;
import gui.listeners.AddUserDialogListener;
import gui.listeners.AdminPanelListener;
import gui.listeners.CandidatePanelListener;
import gui.listeners.CandidatePipelinePanelListener;
import gui.listeners.EditUserDialogListener;
import gui.listeners.OrganisationPanelListener;
import gui.listeners.OrganisationsPanelListener;
import gui.listeners.RemoveCandidateDialogListener;
import gui.listeners.RemoveContactDialogListener;
import gui.listeners.RemoveEventDialogListener;
import gui.listeners.RemoveOrganisationDialogListener;
import gui.listeners.RemoveCandidateSkillDialogListener;
import gui.listeners.RemoveUserDialogListener;
import gui.listeners.RemoveVacancyDialogListener;
import gui.listeners.ReportPanelListener;
import gui.listeners.SearchPanelListener;
import gui.listeners.SkillsManagementPanelListener;
import gui.listeners.TaskListPanelListener;
import gui.listeners.TopMenuListener;
import gui.listeners.UserManagementPanelListener;
import gui.listeners.VacanciesPanelListener;
import gui.listeners.VacancyPanelListener;
import interfaces.UserType;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import model.ClientModel;
import model.LoginAttempt;
import controller.ClientController;
import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Contact;
import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Report;
import database.beans.ReportType;
import database.beans.Search;
import database.beans.Skill;
import database.beans.Task;
import database.beans.User;
import database.beans.Vacancy;

/**
 * View part of MVC, responsible for managing the GUI elements. Includes the main method that runs the client on the EDT.
 * @author Charlie
 */
public class ClientView {
	private LoginWindow loginWindow;
	private MainWindow mainWindow;

	public ClientView() {
		loginWindow = new LoginWindow();
		mainWindow = new MainWindow();
	}

	public void showGUI(ActionListener loginListener) {
		loginWindow.setLoginListener(loginListener);
		loginWindow.setVisible(true);
	}

	// LoginWindow and MainWindow methods
	public LoginAttempt getLoginAttempt() {
		return loginWindow.getLoginAttempt();
	}

	public void showLoginErrorMessage(String message) {
		loginWindow.showErrorMessage(message);
	}

	public void displayMainWindow(UserType userType, List<Vacancy> vacancies, List<User> users, List<Task> tasks) {
		mainWindow.setVisible(loginWindow.getUserId(), true, userType, vacancies, users, tasks);
		loginWindow.dispose();
	}

	// PanelType methods
	public PanelType getDisplayedPanel() {
		return mainWindow.getDisplayedPanel();
	}
	
	// VacanciesPanel methods
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		mainWindow.showVacanciesPanel(vacancies, users);
	}
	
	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		mainWindow.updateVacanciesPanel(vacancies);
	}

	public Vacancy getSelectedVacancy() {
		return mainWindow.getSelectedVacancy();
	}
	
	// VacancyPanel methods
	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile, List<Contact> contacts) {
		mainWindow.showVacancyPanel(updatedVacancy, tempFile, contacts);
	}

	public Vacancy getDisplayedVacancy() {
		return mainWindow.getDisplayedVacancy();
	}
	
	public void updateDisplayedShortlist(List<Event> shortlistEvents) {
		mainWindow.updateDisplayedShortlist(shortlistEvents);
	}
	
	public Event getSelectedShortlistEvent() {
		return mainWindow.getSelectedShortlistEvent();
	}
	
	public Vacancy getUpdatedVacancy() {
		return mainWindow.getUpdatedVacancy();
	}
	
	public void updateDisplayedVacancy(Vacancy vacancy, List<Contact> contacts) {
		mainWindow.updateDisplayedVacancy(vacancy, contacts);
	}
	
	// OrganisationsPanel methods
	public void showOrganisationsPanel(List<Organisation> organisations) {
		mainWindow.showOrganisationsPanel(organisations);
	}
	
	public void updateOrganisationsPanel(List<Organisation> organisations) {
		mainWindow.updateOrganisationsPanel(organisations);
	}
	
	public String getOrganisationSearchTerm() {
		return mainWindow.getOrganisationSearchTerm();
	}
	
	public void removeOrganisationSearchTerm() {
		mainWindow.removeOrganisationSearchTerm();
	}
	
	public Organisation getSelectedOrganisation() {
		return mainWindow.getSelectedOrganisation();
	}
	
	// OrganisationPanel methods
	public void showOrganisationPanel(Organisation updatedOrganisation, Path tempFile) {
		mainWindow.showOrganisationPanel(updatedOrganisation, tempFile);
	}
	
	public Organisation getDisplayedOrganisation() {
		return mainWindow.getDisplayedOrganisation();
	}
	
	public Organisation getUpdatedOrganisation() {
		return mainWindow.getUpdatedOrganisation();
	}
	
	// SearchPanel methods
	public void showSearchPanel(List<Skill> skills, List<Vacancy> vacancies) {
		mainWindow.showSearchPanel(skills, vacancies);
	}
	
	public void addSkillToSearch() {
		mainWindow.addSkillToSearch();
	}
	
	public void removeSkillFromSearch() {
		mainWindow.removeSkillFromSearch();
	}
	
	public Search getSearchPanelSearch() {
		return mainWindow.getSearchPanelSearch();
	}
	
	public void updateSearchPanel(List<Candidate> candidates) {
		mainWindow.updateSearchPanel(candidates);
	}
	
	public void resetSearchPanel() {
		mainWindow.resetSearchPanel();
	}
	
	public Candidate getSearchPanelCandidate() {
		return mainWindow.getSearchPanelCandidate();
	}
	
	public List<Candidate> getSelectedShortlistCandidates() {
		return mainWindow.getSelectedShortlistCandidates();
	}
	
	public Vacancy getShortlistVacancy() {
		return mainWindow.getShortlistVacancy();
	}
	
	// CandidatePipelinePanel methods
	public void showCandidatePipeline() {
		mainWindow.showCandidatePipeline();
	}
	
	public boolean[] getCandidatePipelinePanelOptions() {
		return mainWindow.getCandidatePipelinePanelOptions();
	}
	
	public void updateCandidatePipelinePanel(List<Event> events) {
		mainWindow.updateCandidatePipelinePanel(events);
	}
	
	// CandidatePanel methods
	public void showCandidatePanel(Candidate updatedCandidate, Path tempFile, List<Organisation> organisations) {
		mainWindow.showCandidatePanel(updatedCandidate, tempFile, organisations);
	}
	
	public void updateCandidateLinkedInProfile(URL url) {
		mainWindow.updateCandidateLinkedInProfile(url);
	}
	
	public Candidate getCandidatePanelCandidate() {
		return mainWindow.getCandidatePanelCandidate();
	}
	
	public String getCandidatePanelNotes() {
		return mainWindow.getCandidatePanelNotes();
	}
	
	public Candidate getUpdatedCandidate() {
		return mainWindow.getUpdatedCandidate();
	}
	
	public void updateDisplayedCandidateSkills(List<CandidateSkill> candidateSkills) {
		mainWindow.updateDisplayedCandidateSkills(candidateSkills);
	}
	
	public void updateDisplayedCandidateEvents(List<Event> events) {
		mainWindow.updateDisplayedCandidateEvents(events);
	}
	
	// AdminPanel methods
	public void showAdminPanel(List<User> users) {
		mainWindow.showAdminPanel(users);
	}
	
	public void updateDisplayedUsers(List<User> users) {
		mainWindow.updateDisplayedUsers(users);
	}
	
	public User getAdminPanelUser() {
		return mainWindow.getAdminPanelUser();
	}
	
	public void updateDisplayedSkills(List<Skill> skills) {
		mainWindow.updateDisplayedSkills(skills);
	}
	
	public Skill getSkillPanelSkill() {
		return mainWindow.getSkillPanelSkill();
	}
	
	public Report getReportPanelReport() {
		return mainWindow.getReportPanelReport();
	}
	
	public void changeDisplayedReportTable(ReportType reportType) {
		mainWindow.changeDisplayedReportTable(reportType);
	}
	
	public void updateDisplayedUserReport(Map<User, Map<EventType, Integer>> results) {
		mainWindow.updateDisplayedUserReport(results);
	}
	
	public void updateDisplayedVacancyReport(Map<Vacancy, Map<EventType, Integer>> results) {
		mainWindow.updateDisplayedVacancyReport(results);
	}
	
	public void updateDisplayedOrganisationReport(Map<Organisation, Map<Boolean, Integer>> results) {
		mainWindow.updateDisplayedOrganisationReport(results);
	}
	
	// TaskListPanel methods
	public void updateDisplayedTasks(List<Task> tasks) {
		mainWindow.updateDisplayedTasks(tasks);
	}
	
	public Task getTaskListPanelTask() {
		return mainWindow.getTaskListPanelTask();
	}
	
	public void uncheckAllTaskListPanelTasks() {
		mainWindow.uncheckAllTaskListPanelTasks();
	}
	
	// Generic methods (dialogs, file choosers)
	public File showFileChooser(String title) {
		return mainWindow.showFileChooser(title);
	}

	public void setSelectedTopMenuPanel(MenuPanel panel) {
		mainWindow.setSelectedTopMenuPanel(panel);
	}
	
	public boolean showConfirmDialog(ConfirmDialogType dialogType) {
		return mainWindow.showConfirmDialog(dialogType);
	}

	public void showErrorDialog(ErrorDialogType errorDialog) {
		mainWindow.showErrorDialog(errorDialog);
	}

	public void showMessageDialog(MessageDialogType confirmDialog) {
		mainWindow.showMessageDialog(confirmDialog);		
	}
	
	public void showDialog(DialogType dialog) {
		mainWindow.showDialog(dialog);
	}

	public void hideDialog(DialogType dialog) {
		mainWindow.hideDialog(dialog);
	}

	public void setDisplayedOrganisationsInDialog(DialogType dialog, List<Organisation> organisations) {
		mainWindow.setDisplayedOrganisationsInDialog(dialog, organisations);
	}
	
	public void setDisplayedContactsInDialog(DialogType dialog, List<Contact> contacts) {
		mainWindow.setDisplayedContactsInDialog(dialog, contacts);
	}

	public void setDisplayedVacanciesInDialog(DialogType dialog, List<Vacancy> vacancies) {
		mainWindow.setDisplayedVacanciesInDialog(dialog, vacancies);
	}
	
	public void setDisplayedCandidatesInDialog(DialogType dialog, List<Candidate> candidates) {
		mainWindow.setDisplayedCandidatesInDialog(dialog, candidates);
	}
	
	public void setDisplayedSkillsInDialog(DialogType dialog, List<Skill> skills) {
		mainWindow.setDisplayedSkillsInDialog(dialog, skills);
	}
	
	public void setDisplayedEventsInDialog(DialogType dialog, List<Event> event) {
		mainWindow.setDisplayedEventsInDialog(dialog, event);
	}
	
	public void setDisplayedUsersInDialog(DialogType dialog, List<User> users) {
		mainWindow.setDisplayedUsersInDialog(dialog, users);
	}

	public void setDisplayedUserInDialog(DialogType dialog, User user) {
		mainWindow.setDisplayedUserInDialog(dialog, user);
	}
	
	public void displayFileInDialog(DialogType menuDialogType, File file) {
		mainWindow.displayFileInDialog(menuDialogType, file);
	}

	public Vacancy getVacancyDialogVacancy(DialogType dialog) {
		return mainWindow.getVacancyDialogVacancy(dialog);
	}
	
	public Organisation getOrganisationDialogOrganisation(DialogType dialog) {
		return mainWindow.getOrganisationDialogOrganisation(dialog);
	}
	
	public Candidate getCandidateDialogCandidate(DialogType dialog) {
		return mainWindow.getCandidateDialogCandidate(dialog);
	}
	
	public Contact getContactDialogContact(DialogType dialog) {
		return mainWindow.getContactDialogContact(dialog);
	}
	
	public String getLinkedInProfileDialogUrl(DialogType dialog) {
		return mainWindow.getLinkedInProfileDialogUrl(dialog);
	}
	
	public Skill getSkillDialogSkill(DialogType dialog) {
		return mainWindow.getSkillDialogSkill(dialog);
	}
	
	public Organisation getEventDialogOrganisation() {
		return mainWindow.getEventDialogOrganisation();
	}
	
	public Event getEventDialogEvent(DialogType dialogType) {
		return mainWindow.getEventDialogEvent(dialogType);
	}
	
	public Task getTaskDialogTask() {
		return mainWindow.getTaskDialogTask();
	}
	
	public User getUserDialogUser(DialogType dialogType) {
		return mainWindow.getUserDialogUser(dialogType);
	}
	
	// methods to set listeners 
	public void setMenuListener(ActionListener actionListener) {
		mainWindow.setMenuListener(actionListener);
	}
	
	public void setTopMenuListener(TopMenuListener topMenuListener) {
		mainWindow.setTopMenuListener(topMenuListener);
	}
	
	public void setVacanciesPanelListeners(VacanciesPanelListener vacanciesPanelListener) {
		mainWindow.setVacanciesPanelListeners(vacanciesPanelListener);
	}

	public void setVacancyPanelListener(VacancyPanelListener listener) {
		mainWindow.setVacancyPanelListener(listener);
	}

	public void setOrganisationsPanelListener(OrganisationsPanelListener organisationsPanelListener) {
		mainWindow.setOrganisationsPanelListener(organisationsPanelListener);
	}

	public void setOrganisationPanelListener(OrganisationPanelListener organisationPanelListener) {
		mainWindow.setOrganisationPanelListener(organisationPanelListener);
	}
	
	public void setSearchPanelListener(SearchPanelListener searchPanelListener) {
		mainWindow.setSearchPanelListener(searchPanelListener);
	}
	
	public void setCandidatePanelListener(CandidatePanelListener candidatePanelListener) {
		mainWindow.setCandidatePanelListener(candidatePanelListener);
	}
	
	public void setCandidatePipelinePanelListener(CandidatePipelinePanelListener candidatePipelineListener) {
		mainWindow.setCandidatePipelinePanelListener(candidatePipelineListener);
	}
	
	public void setTaskListPanelListener(TaskListPanelListener taskListPanelListener) {
		mainWindow.setTaskListPanelListener(taskListPanelListener);
	}
	
	public void setAdminPanelListener(AdminPanelListener adminPanelListener, UserManagementPanelListener userListener, SkillsManagementPanelListener skillListener, ReportPanelListener reportListener) {
		mainWindow.setAdminPanelListener(adminPanelListener, userListener, skillListener, reportListener);
	}
	
	public void setAddVacancyDialogListener(ActionListener actionListener) {
		mainWindow.setAddVacancyDialogListener(actionListener);
	}
	
	public void setRemoveVacancyDialogListener(RemoveVacancyDialogListener removeVacancyDialogListener) {
		mainWindow.setRemoveVacancyDialogListener(removeVacancyDialogListener);
	}
	
	public void setAddOrganisationDialogListener(AddOrganisationDialogListener addOrganisationDialogListener) {
		mainWindow.setAddOrganisationDialogListener(addOrganisationDialogListener);
	}

	public void setRemoveOrganisationDialogListener(RemoveOrganisationDialogListener removeOrganisationDialogListener) {
		mainWindow.setRemoveOrganisationDialogListener(removeOrganisationDialogListener);
	}
	
	public void setAddCandidateDialogListener(AddCandidateDialogListener addCandidateDialogListener) {
		mainWindow.setAddCandidateDialogListener(addCandidateDialogListener);
	}
	
	public void setRemoveCandidateDialogListener(RemoveCandidateDialogListener removeCandidateDialogListener) {
		mainWindow.setRemoveCandidateDialogListener(removeCandidateDialogListener);
	}
	
	public void setAddContactDialogListener(AddContactDialogListener addContactDialogListener) {
		mainWindow.setAddContactDialogListener(addContactDialogListener);
	}
	
	public void setRemoveContactDialogListener(RemoveContactDialogListener removeContactDialogListener) {
		mainWindow.setRemoveContactDialogListener(removeContactDialogListener);
	}
	
	public void setAddLinkedInProfileListener(AddLinkedInProfileListener addLinkedInProfileListener) {
		mainWindow.setAddLinkedInProfileLister(addLinkedInProfileListener);
	}
	
	public void setAddCandidateSkillDialogListener(AddCandidateSkillDialogListener addSkillListener) {
		mainWindow.setAddCandidateSkillDialogListener(addSkillListener);
	}
	
	public void setRemoveCandidateSkillDialogListener(RemoveCandidateSkillDialogListener removeSkillListener) {
		mainWindow.setRemoveCandidateSkillDialogListener(removeSkillListener);
	}
	
	public void setAddEventDialogListener(AddEventDialogListener eventDialogListener) {
		mainWindow.setAddEventDialogListener(eventDialogListener);
	}
	
	public void setRemoveEventDialogListener(RemoveEventDialogListener removeEventDialogListener) {
		mainWindow.setRemoveEventDialogListener(removeEventDialogListener);
	}
	
	public void setAddTaskDialogListener(AddTaskDialogListener addTaskDialogListener) {
		mainWindow.setAddTaskDialogListener(addTaskDialogListener);
	}
	
	public void setAddUserDialogListener(AddUserDialogListener addUserDialogListener) {
		mainWindow.setAddUserDialogListener(addUserDialogListener);
	}
	
	public void setRemoveUserDialogListener(RemoveUserDialogListener removeUserDialogListener) {
		mainWindow.setRemoveUserDialogListener(removeUserDialogListener);
	}
	
	public void setEditUserDialogListener(EditUserDialogListener editUserDialogListener) {
		mainWindow.setEditUserDialogListener(editUserDialogListener);
	}
	
	public void setAddSkillDialogListener(AddSkillDialogListener addSkillDialogListener) {
		mainWindow.setAddSkillDialogListener(addSkillDialogListener);
	}
	
	// main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ClientModel model = new ClientModel();
				ClientView view = new ClientView();
				@SuppressWarnings("unused")
				ClientController controller = new ClientController(view, model);
			}
		});
	}
}
