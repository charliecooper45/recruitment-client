package gui;

import gui.TopMenuPanel.MenuPanel;
import gui.dialogs.AddCandidateDialog;
import gui.dialogs.AddCandidateSkillDialog;
import gui.dialogs.AddContactDialog;
import gui.dialogs.AddEventDialog;
import gui.dialogs.AddLinkedInDialog;
import gui.dialogs.AddOrganisationDialog;
import gui.dialogs.AddSkillDialog;
import gui.dialogs.AddTaskDialog;
import gui.dialogs.AddUserDialog;
import gui.dialogs.AddVacancyDialog;
import gui.dialogs.EditUserDialog;
import gui.dialogs.RecruitmentDialog;
import gui.dialogs.RemoveCandidateDialog;
import gui.dialogs.RemoveCandidateSkillDialog;
import gui.dialogs.RemoveContactDialog;
import gui.dialogs.RemoveEventDialog;
import gui.dialogs.RemoveOrganisationDialog;
import gui.dialogs.RemoveUserDialog;
import gui.dialogs.RemoveVacancyDialog;
import gui.listeners.AddCandidateDialogListener;
import gui.listeners.AddCandidateSkillDialogListener;
import gui.listeners.AddContactDialogListener;
import gui.listeners.AddEventDialogListener;
import gui.listeners.AddLinkedInProfileListener;
import gui.listeners.AddOrganisationDialogListener;
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
import gui.listeners.RemoveCandidateSkillDialogListener;
import gui.listeners.RemoveContactDialogListener;
import gui.listeners.RemoveEventDialogListener;
import gui.listeners.RemoveOrganisationDialogListener;
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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Contact;
import database.beans.Event;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Report;
import database.beans.Search;
import database.beans.Skill;
import database.beans.Task;
import database.beans.User;
import database.beans.Vacancy;

//TODO NEXT: sort out resizing/minimum size
/**
 * Main window that displays once the user has logged in
 * @author Charlie
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private BorderLayout borderLayout;
	//TODO NEXT B: ensure this executor is shutdown
	private ExecutorService timeThreadExecutor;

	// JPanels that form the static elements of the GUI
	private TopMenuPanel topMenuPanel;
	private TaskListPanel taskListPanel;

	// the menu items displayed in the menu
	private JMenuItem[] menuItems;

	// JPanels that fill the centre of the GUI
	private Map<PanelType, JPanel> centrePanels;

	// Dialog windows 
	private Map<DialogType, RecruitmentDialog> dialogs;

	// Holds the userId of the user of the client
	public static String USER_ID;

	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1000, 500));
		// thread that updates the title bar of the JFrame to the current time and date
		timeThreadExecutor = Executors.newSingleThreadExecutor();
		timeThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
					Calendar date = new GregorianCalendar();
					SimpleDateFormat format = new SimpleDateFormat("EEEE dd'th' MMM YYYY - H:mm aa ");
					MainWindow.this.setTitle("Recruitment Software - " + format.format(date.getTime()));
				}
			}
		});

		// first panel displayed is the vacancies panel
		centrePanels = new EnumMap<>(PanelType.class);
		centrePanels.put(PanelType.VACANCIES, new VacanciesPanel());
		centrePanels.put(PanelType.PIPELINE, new CandidatePipelinePanel());
		centrePanels.put(PanelType.ORGANISATIONS, new OrganisationsPanel());
		centrePanels.put(PanelType.SEARCH, new SearchPanel());
		centrePanels.put(PanelType.ADMIN, new AdminPanel());
		centrePanels.put(PanelType.VACANCY, new VacancyPanel());
		centrePanels.put(PanelType.CANDIDATE, new CandidatePanel());
		centrePanels.put(PanelType.ORGANISATION, new OrganisationPanel());

		// create the menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Database Editor");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		String[] itemNames = { "Add Candidate", "Add Vacancy", "Add Organisation", "Add Contact", "Remove Candidate", "Remove Vacancy", "Remove Organisation", "Remove Contact" };
		menuItems = new JMenuItem[8];
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new JMenuItem(itemNames[i]);
			if (i < 4) {
				addMenu.add(menuItems[i]);
			} else {
				removeMenu.add(menuItems[i]);
			}
		}
		menu.add(addMenu);
		menu.add(removeMenu);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		// create the top menu panel
		topMenuPanel = new TopMenuPanel();

		// create the task list panel
		taskListPanel = new TaskListPanel();

		// create the dialogs
		dialogs = new EnumMap<>(DialogType.class);
		dialogs.put(DialogType.ADD_VACANCY, new AddVacancyDialog(this));
		dialogs.put(DialogType.REMOVE_VACANCY, new RemoveVacancyDialog(this));
		dialogs.put(DialogType.ADD_ORGANISATION, new AddOrganisationDialog(this));
		dialogs.put(DialogType.REMOVE_ORGANISATION, new RemoveOrganisationDialog(this));
		dialogs.put(DialogType.ADD_CANDIDATE, new AddCandidateDialog(this));
		dialogs.put(DialogType.REMOVE_CANDIDATE, new RemoveCandidateDialog(this));
		dialogs.put(DialogType.ADD_CONTACT, new AddContactDialog(this));
		dialogs.put(DialogType.REMOVE_CONTACT, new RemoveContactDialog(this));
		dialogs.put(DialogType.CANDIDATE_ADD_LINKEDIN, new AddLinkedInDialog(this));
		dialogs.put(DialogType.ADD_CANDIDATE_SKILL, new AddCandidateSkillDialog(this));
		dialogs.put(DialogType.REMOVE_CANDIDATE_SKILL, new RemoveCandidateSkillDialog(this));
		dialogs.put(DialogType.ADD_EVENT, new AddEventDialog(this));
		dialogs.put(DialogType.REMOVE_EVENT, new RemoveEventDialog(this));
		dialogs.put(DialogType.ADD_TASK, new AddTaskDialog(this));
		dialogs.put(DialogType.ADD_USER, new AddUserDialog(this));
		dialogs.put(DialogType.REMOVE_USER, new RemoveUserDialog(this));
		dialogs.put(DialogType.EDIT_USER, new EditUserDialog(this));
		dialogs.put(DialogType.ADD_SKILL, new AddSkillDialog(this));
	}

	private void init(UserType userType, List<Task> tasks) {
		borderLayout = new BorderLayout();
		setLayout(borderLayout);

		// initialize the TopMenuPanel according to the user type
		topMenuPanel.setUserType(userType);
		add(topMenuPanel, BorderLayout.NORTH);
		taskListPanel.updateTasks(tasks);
		add(taskListPanel, BorderLayout.EAST);
	}

	private void removeCentreComponent() {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);
		if (!(centreComponent == null))
			remove(centreComponent);
	}

	public void setVisible(String userId, boolean visible, UserType userType, List<Vacancy> vacancies, List<User> users, List<Task> tasks) {
		USER_ID = userId;
		init(userType, tasks);
		if (visible) {
			showVacanciesPanel(vacancies, users);
		}
		super.setVisible(visible);
	}

	// PanelType methods
	public PanelType getDisplayedPanel() {
		for (Map.Entry<PanelType, JPanel> e : centrePanels.entrySet()) {
			Object value = e.getValue();
			if (value == borderLayout.getLayoutComponent(BorderLayout.CENTER)) {
				return e.getKey();
			}
		}
		return null;
	}

	// VacanciesPanel methods
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		removeCentreComponent();

		// when the vacancies panel is displayed update the necessary fields from the server
		JPanel panel = centrePanels.get(PanelType.VACANCIES);
		VacanciesPanel vPanel = (VacanciesPanel) panel;
		vPanel.setDefaultOptions();
		vPanel.updateDisplayedUsers(users);
		vPanel.updateDisplayedVacancies(vacancies);

		add(panel);

		revalidate();
		repaint();
	}

	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelType.VACANCIES);
		panel.updateDisplayedVacancies(vacancies);
	}

	public Vacancy getSelectedVacancy() {
		VacanciesPanel vPanel = (VacanciesPanel) centrePanels.get(PanelType.VACANCIES);
		return vPanel.getSelectedVacancy();
	}

	// VacancyPanel methods
	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile, List<Contact> contacts) {
		removeCentreComponent();

		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.setDisplayedVacancy(updatedVacancy, tempFile, contacts);
		add(panel);

		revalidate();
		repaint();
	}

	public Vacancy getDisplayedVacancy() {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		return panel.getDisplayedVacancy();
	}

	public void updateDisplayedShortlist(List<Event> shortlistEvents) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.updateShortlist(shortlistEvents);
	}

	public Event getSelectedShortlistEvent() {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		return panel.getSelectedShortlistEvent();
	}

	public Vacancy getUpdatedVacancy() {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		return panel.getUpdatedVacancy();
	}

	public void updateDisplayedVacancy(Vacancy vacancy, List<Contact> contacts) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.updateDisplayedVacancy(vacancy, contacts);
	}

	// OrganisationsPanel methods
	public void showOrganisationsPanel(List<Organisation> organisations) {
		removeCentreComponent();

		// when the vacancies panel is displayed update the necessary fields from the server
		JPanel panel = centrePanels.get(PanelType.ORGANISATIONS);
		OrganisationsPanel oPanel = (OrganisationsPanel) panel;
		oPanel.setDefaultOptions();
		oPanel.updateDisplayedOrganisations(organisations);

		add(panel);

		revalidate();
		repaint();
	}

	public void updateOrganisationsPanel(List<Organisation> organisations) {
		JPanel panel = centrePanels.get(PanelType.ORGANISATIONS);
		OrganisationsPanel oPanel = (OrganisationsPanel) panel;
		oPanel.updateDisplayedOrganisations(organisations);
	}

	public String getOrganisationSearchTerm() {
		JPanel panel = centrePanels.get(PanelType.ORGANISATIONS);
		OrganisationsPanel oPanel = (OrganisationsPanel) panel;
		return oPanel.getSearchTerm();
	}

	public void removeOrganisationSearchTerm() {
		JPanel panel = centrePanels.get(PanelType.ORGANISATIONS);
		OrganisationsPanel oPanel = (OrganisationsPanel) panel;
		oPanel.removeSearchTerm();
	}

	public Organisation getSelectedOrganisation() {
		JPanel panel = centrePanels.get(PanelType.ORGANISATIONS);
		OrganisationsPanel oPanel = (OrganisationsPanel) panel;
		return oPanel.getSelectedOrganisation();
	}

	// OrganisationPanel methods
	public void showOrganisationPanel(Organisation updatedOrganisation, Path tempFile) {
		removeCentreComponent();

		OrganisationPanel panel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
		panel.setDisplayedOrganisation(updatedOrganisation, tempFile);
		add(panel);

		revalidate();
		repaint();
	}

	public Organisation getDisplayedOrganisation() {
		OrganisationPanel panel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
		return panel.getDisplayedOrganisation();
	}

	public Organisation getUpdatedOrganisation() {
		OrganisationPanel panel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
		return panel.getUpdatedOrganisation();
	}

	// SearchPanel methods
	public void showSearchPanel(List<Skill> skills, List<Vacancy> vacancies) {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);

		if (centreComponent != centrePanels.get(PanelType.SEARCH)) {
			removeCentreComponent();

			// when the search panel is displayed then update the necessary fields from the server
			SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
			panel.updateDisplayedSkills(skills);
			panel.updateDisplayedVacancies(vacancies);
			add(panel);

			revalidate();
			repaint();
		}
	}

	public void addSkillToSearch() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		panel.addSkillToSearch();
	}

	public void removeSkillFromSearch() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		panel.removeSkillFromSearch();
	}

	public Search getSearchPanelSearch() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		return panel.getSearch();
	}

	public void updateSearchPanel(List<Candidate> candidates) {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		panel.updateDisplayedCandidates(candidates);
	}

	public void resetSearchPanel() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		panel.setDefaultOptions();
	}

	public Candidate getSearchPanelCandidate() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		return panel.getSelectedCandidate();
	}

	public List<Candidate> getSelectedShortlistCandidates() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		return panel.getSelectedCandidates();
	}

	public Vacancy getShortlistVacancy() {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		return panel.getShortlistVacancy();
	}

	// CandidatePipelinePanel methods
	public void showCandidatePipeline() {
		removeCentreComponent();

		CandidatePipelinePanel panel = (CandidatePipelinePanel) centrePanels.get(PanelType.PIPELINE);
		add(panel);

		revalidate();
		repaint();
	}

	public boolean[] getCandidatePipelinePanelOptions() {
		CandidatePipelinePanel panel = (CandidatePipelinePanel) centrePanels.get(PanelType.PIPELINE);
		return panel.getOptions();
	}

	public void updateCandidatePipelinePanel(List<Event> events) {
		CandidatePipelinePanel panel = (CandidatePipelinePanel) centrePanels.get(PanelType.PIPELINE);
		panel.updateDisplayedEvents(events);
	}

	// CandidatePanel methods
	public void showCandidatePanel(Candidate updatedCandidate, Path tempFile, List<Organisation> organisations) {
		removeCentreComponent();

		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		panel.setDisplayedCandidate(updatedCandidate, tempFile, organisations);
		add(panel);

		revalidate();
		repaint();
	}

	public void updateCandidateLinkedInProfile(URL url) {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		panel.updateShownLinkedInProfile(url);
	}

	public Candidate getCandidatePanelCandidate() {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		return panel.getSelectedCandidate();
	}

	public String getCandidatePanelNotes() {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		return panel.getCandidateNotes();
	}

	public Candidate getUpdatedCandidate() {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		return panel.getUpdatedCandidate();
	}

	public void updateDisplayedCandidateSkills(List<CandidateSkill> candidateSkills) {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		panel.updateDisplayedCandidateSkills(candidateSkills);
	}

	public void updateDisplayedCandidateEvents(List<Event> events) {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		panel.updateDisplayedCandidateEvents(events);
	}

	// AdminPanel methods
	public void showAdminPanel(List<User> users) {
		removeCentreComponent();

		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		panel.updateDisplayedUsers(users);
		add(panel);

		revalidate();
		repaint();
	}

	public void updateDisplayedUsers(List<User> users) {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		panel.updateDisplayedUsers(users);
	}

	public User getAdminPanelUser() {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		return panel.getSelectedUser();
	}
	
	public void updateDisplayedSkills(List<Skill> skills) {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		panel.updateDisplayedSkills(skills);
	}
	
	public Skill getSkillPanelSkill() {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		return panel.getSelectedSkill();
	}
	
	public Report getReportPanelReport() {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		return panel.getReport();
	}
	
	public void updateDisplayedReport(Map<User, Map<EventType, Integer>> results) {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		panel.updateDisplayedReport(results);		
	}
	
	// TaskListPanel methods
	public void updateDisplayedTasks(List<Task> tasks) {
		TaskListPanel panel = taskListPanel;
		panel.updateTasks(tasks);
	}

	public Task getTaskListPanelTask() {
		return taskListPanel.getSelectedTask();
	}

	public void uncheckAllTaskListPanelTasks() {
		taskListPanel.uncheckAllTasks();
	}

	// Generic methods (dialogs, file choosers)
	public File showFileChooser(final String title) {
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".doc")) {
					return true;
				} else if (f.getName().endsWith(".docx")) {
					return true;
				} else if (f.getName().endsWith(".pdf")) {
					return true;
				} else if (f.getName().endsWith(".txt")) {
					return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Text/Office/PDF files";
			}
		});
		fc.setDialogTitle(title);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}

	public void setSelectedTopMenuPanel(MenuPanel panel) {
		topMenuPanel.setSelectedPanel(panel);
	}

	public boolean showConfirmDialog(ConfirmDialogType dialogType) {
		JPanel panel = null;
		VacancyPanel vacancyPanel = null;
		OrganisationPanel organisationPanel = null;
		int response;

		switch (dialogType) {
		case VACANCY_REMOVE_PROFILE:
			vacancyPanel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
			response = JOptionPane.showConfirmDialog(vacancyPanel, ConfirmDialogType.VACANCY_REMOVE_PROFILE.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0)
				return true;
			break;
		case REMOVE_VACANCY:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_VACANCY.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case ORGANISATION_REMOVE_TOB:
			organisationPanel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
			response = JOptionPane.showConfirmDialog(organisationPanel, ConfirmDialogType.ORGANISATION_REMOVE_TOB.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0)
				return true;
			break;
		case REMOVE_ORGANISATION:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_ORGANISATION.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case REMOVE_CANDIDATE:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_CANDIDATE.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case REMOVE_CONTACT:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_CONTACT.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case CANDIDATE_REMOVE_LINKEDIN:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.CANDIDATE_REMOVE_LINKEDIN.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case CANDIDATE_REMOVE_CV:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.CANDIDATE_REMOVE_CV.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case REMOVE_FROM_SHORTLIST:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_FROM_SHORTLIST.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case REMOVE_TASK:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_FROM_SHORTLIST.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
		case REMOVE_SKILL:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, ConfirmDialogType.REMOVE_SKILL.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
				return true;
			}
			break;
			
		}
		return false;
	}

	public void showErrorDialog(ErrorDialogType errorDialog) {
		JOptionPane.showMessageDialog(borderLayout.getLayoutComponent(BorderLayout.CENTER), errorDialog.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showMessageDialog(MessageDialogType confirmDialog) {
		JOptionPane.showMessageDialog(borderLayout.getLayoutComponent(BorderLayout.CENTER), confirmDialog.getMessage(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showDialog(DialogType dialog) {
		dialogs.get(dialog).setVisible(true);
	}

	public void hideDialog(DialogType dialog) {
		dialogs.get(dialog).setVisible(false);
	}

	public void setDisplayedOrganisationsInDialog(DialogType dialog, List<Organisation> organisations) {
		dialogs.get(dialog).setDisplayedOrganisations(organisations);
	}

	public void setDisplayedContactsInDialog(DialogType dialog, List<Contact> contacts) {
		dialogs.get(dialog).setDisplayedContacts(contacts);
	}

	public void setDisplayedVacanciesInDialog(DialogType dialog, List<Vacancy> vacancies) {
		dialogs.get(dialog).setDisplayedVacancies(vacancies);
	}

	public void setDisplayedCandidatesInDialog(DialogType dialog, List<Candidate> candidates) {
		dialogs.get(dialog).setDisplayedCandidates(candidates);
	}

	public void setDisplayedSkillsInDialog(DialogType dialog, List<Skill> skills) {
		dialogs.get(dialog).setDisplayedSkills(skills);
	}

	public void setDisplayedEventsInDialog(DialogType event, List<Event> events) {
		dialogs.get(event).setDisplayedEvents(events);
	}

	public void setDisplayedUsersInDialog(DialogType dialog, List<User> users) {
		dialogs.get(dialog).setDisplayedUsers(users);
	}

	public void setDisplayedUserInDialog(DialogType dialog, User user) {
		dialogs.get(dialog).setDisplayedUser(user);
	}
	
	public void displayFileInDialog(DialogType menuDialog, File file) {
		RecruitmentDialog dialog = dialogs.get(menuDialog);
		dialog.setDisplayedFile(file);
	}

	public Vacancy getVacancyDialogVacancy(DialogType dialog) {
		if (dialog == DialogType.ADD_VACANCY) {
			AddVacancyDialog vDialog = (AddVacancyDialog) dialogs.get(DialogType.ADD_VACANCY);
			return vDialog.getVacancy();
		} else if (dialog == DialogType.REMOVE_VACANCY) {
			RemoveVacancyDialog vDialog = (RemoveVacancyDialog) dialogs.get(DialogType.REMOVE_VACANCY);
			return vDialog.getVacancy();
		}
		return null;
	}

	public Organisation getOrganisationDialogOrganisation(DialogType dialog) {
		switch (dialog) {
		case ADD_ORGANISATION:
			AddOrganisationDialog addOrgDialog = (AddOrganisationDialog) dialogs.get(DialogType.ADD_ORGANISATION);
			return addOrgDialog.getOrganisation();
		case REMOVE_ORGANISATION:
			RemoveOrganisationDialog removeOrgDialog = (RemoveOrganisationDialog) dialogs.get(DialogType.REMOVE_ORGANISATION);
			return removeOrgDialog.getOrganisation();
		}
		return null;
	}

	public Candidate getCandidateDialogCandidate(DialogType dialog) {
		switch (dialog) {
		case ADD_CANDIDATE:
			AddCandidateDialog addCandidateDialog = (AddCandidateDialog) dialogs.get(DialogType.ADD_CANDIDATE);
			return addCandidateDialog.getCandidate();
		case REMOVE_CANDIDATE:
			RemoveCandidateDialog removeCandidateDialog = (RemoveCandidateDialog) dialogs.get(DialogType.REMOVE_CANDIDATE);
			return removeCandidateDialog.getCandidate();
		}
		return null;
	}

	public Contact getContactDialogContact(DialogType dialog) {
		switch (dialog) {
		case ADD_CONTACT:
			AddContactDialog addContactDialog = (AddContactDialog) dialogs.get(DialogType.ADD_CONTACT);
			return addContactDialog.getContact();
		case REMOVE_CONTACT:
			RemoveContactDialog removeContactDialog = (RemoveContactDialog) dialogs.get(DialogType.REMOVE_CONTACT);
			return removeContactDialog.getContact();
		}
		return null;
	}

	public String getLinkedInProfileDialogUrl(DialogType dialog) {
		switch (dialog) {
		case CANDIDATE_ADD_LINKEDIN:
			AddLinkedInDialog linkedInDialog = (AddLinkedInDialog) dialogs.get(DialogType.CANDIDATE_ADD_LINKEDIN);
			return linkedInDialog.getProfileUrl();
		}
		return "";
	}

	public Skill getSkillDialogSkill(DialogType dialog) {
		switch (dialog) {
		case ADD_CANDIDATE_SKILL:
			AddCandidateSkillDialog addCandidateSkillDialog = (AddCandidateSkillDialog) dialogs.get(DialogType.ADD_CANDIDATE_SKILL);
			return addCandidateSkillDialog.getSelectedSkill();
		case REMOVE_CANDIDATE_SKILL:
			RemoveCandidateSkillDialog removeCandidateSkillDialog = (RemoveCandidateSkillDialog) dialogs.get(DialogType.REMOVE_CANDIDATE_SKILL);
			return removeCandidateSkillDialog.getSelectedSkill();
		case ADD_SKILL:
			AddSkillDialog addSkillDialog = (AddSkillDialog) dialogs.get(DialogType.ADD_SKILL);
			return addSkillDialog.getSelectedSkill();
		}
		return null;
	}

	public Organisation getEventDialogOrganisation() {
		AddEventDialog dialog = (AddEventDialog) dialogs.get(DialogType.ADD_EVENT);
		return dialog.getDisplayedOrganisation();
	}

	public Event getEventDialogEvent(DialogType dialogType) {
		if (dialogType == DialogType.ADD_EVENT) {
			AddEventDialog dialog = (AddEventDialog) dialogs.get(DialogType.ADD_EVENT);
			return dialog.getEvent();
		} else if (dialogType == DialogType.REMOVE_EVENT) {
			RemoveEventDialog dialog = (RemoveEventDialog) dialogs.get(DialogType.REMOVE_EVENT);
			return dialog.getEvent();
		}
		return null;
	}

	public Task getTaskDialogTask() {
		AddTaskDialog dialog = (AddTaskDialog) dialogs.get(DialogType.ADD_TASK);
		return dialog.getTask();
	}

	public User getUserDialogUser(DialogType dialogType) {
		if (dialogType == DialogType.ADD_USER) {
			AddUserDialog dialog = (AddUserDialog) dialogs.get(DialogType.ADD_USER);
			return dialog.getUser();
		} else if (dialogType == DialogType.REMOVE_USER) {
			RemoveUserDialog dialog = (RemoveUserDialog) dialogs.get(DialogType.REMOVE_USER);
			return dialog.getUser();
		} else if (dialogType == DialogType.EDIT_USER) {
			EditUserDialog dialog = (EditUserDialog) dialogs.get(DialogType.EDIT_USER);
			return dialog.getUser();
		}
		return null;
	}

	// methods to set listeners
	public void setMenuListener(ActionListener actionListener) {
		for (JMenuItem menuItem : menuItems) {
			menuItem.addActionListener(actionListener);
		}
	}

	public void setTopMenuListener(TopMenuListener topMenuListener) {
		topMenuPanel.setTopMenuListener(topMenuListener);
	}

	public void setVacanciesPanelListeners(VacanciesPanelListener vacanciesPanelListener) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelType.VACANCIES);
		panel.setVacanciesPanelListener(vacanciesPanelListener);
	}

	public void setVacancyPanelListener(VacancyPanelListener listener) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.setVacancyPanelListener(listener);
	}

	public void setOrganisationsPanelListener(OrganisationsPanelListener organisationsPanelListener) {
		OrganisationsPanel panel = (OrganisationsPanel) centrePanels.get(PanelType.ORGANISATIONS);
		panel.setOrganisationsPanelListener(organisationsPanelListener);
	}

	public void setOrganisationPanelListener(OrganisationPanelListener organisationPanelListener) {
		OrganisationPanel panel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
		panel.setOrganisationPanelListener(organisationPanelListener);
	}

	public void setSearchPanelListener(SearchPanelListener searchPanelListener) {
		SearchPanel panel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		panel.setSearchPanelListener(searchPanelListener);
	}

	public void setCandidatePanelListener(CandidatePanelListener candidatePanelListener) {
		CandidatePanel panel = (CandidatePanel) centrePanels.get(PanelType.CANDIDATE);
		panel.setCandidatePanelListener(candidatePanelListener);
	}

	public void setCandidatePipelinePanelListener(CandidatePipelinePanelListener candidatePipelineListener) {
		CandidatePipelinePanel panel = (CandidatePipelinePanel) centrePanels.get(PanelType.PIPELINE);
		panel.setCandidatePipelinePanelListener(candidatePipelineListener);
	}

	public void setTaskListPanelListener(TaskListPanelListener taskListPanelListener) {
		TaskListPanel panel = taskListPanel;
		panel.setTaskListPanelListener(taskListPanelListener);
	}

	public void setAdminPanelListener(AdminPanelListener adminPanelListener, UserManagementPanelListener userListener, SkillsManagementPanelListener skillListener, ReportPanelListener reportListener) {
		AdminPanel panel = (AdminPanel) centrePanels.get(PanelType.ADMIN);
		panel.setAdminPanelListener(adminPanelListener, userListener, skillListener, reportListener);
	}

	public void setAddVacancyDialogListener(ActionListener actionListener) {
		dialogs.get(DialogType.ADD_VACANCY).setActionListener(actionListener);
	}

	public void setRemoveVacancyDialogListener(RemoveVacancyDialogListener removeVacancyDialogListener) {
		dialogs.get(DialogType.REMOVE_VACANCY).setActionListener(removeVacancyDialogListener);
	}

	public void setAddOrganisationDialogListener(AddOrganisationDialogListener addOrganisationDialogListener) {
		dialogs.get(DialogType.ADD_ORGANISATION).setActionListener(addOrganisationDialogListener);
	}

	public void setRemoveOrganisationDialogListener(RemoveOrganisationDialogListener removeOrganisationDialogListener) {
		dialogs.get(DialogType.REMOVE_ORGANISATION).setActionListener(removeOrganisationDialogListener);
	}

	public void setAddCandidateDialogListener(AddCandidateDialogListener addCandidateDialogListener) {
		dialogs.get(DialogType.ADD_CANDIDATE).setActionListener(addCandidateDialogListener);
	}

	public void setRemoveCandidateDialogListener(RemoveCandidateDialogListener removeCandidateDialogListener) {
		dialogs.get(DialogType.REMOVE_CANDIDATE).setActionListener(removeCandidateDialogListener);
	}

	public void setAddContactDialogListener(AddContactDialogListener addContactDialogListener) {
		dialogs.get(DialogType.ADD_CONTACT).setActionListener(addContactDialogListener);
	}

	public void setRemoveContactDialogListener(RemoveContactDialogListener removeContactDialogListener) {
		dialogs.get(DialogType.REMOVE_CONTACT).setActionListener(removeContactDialogListener);
	}

	public void setAddLinkedInProfileLister(AddLinkedInProfileListener addLinkedInProfileListener) {
		dialogs.get(DialogType.CANDIDATE_ADD_LINKEDIN).setActionListener(addLinkedInProfileListener);
	}

	public void setAddCandidateSkillDialogListener(AddCandidateSkillDialogListener addSkillListener) {
		dialogs.get(DialogType.ADD_CANDIDATE_SKILL).setActionListener(addSkillListener);
	}

	public void setRemoveCandidateSkillDialogListener(RemoveCandidateSkillDialogListener removeSkillListener) {
		dialogs.get(DialogType.REMOVE_CANDIDATE_SKILL).setActionListener(removeSkillListener);
	}

	public void setAddEventDialogListener(AddEventDialogListener eventDialogListener) {
		dialogs.get(DialogType.ADD_EVENT).setActionListener(eventDialogListener);
	}

	public void setRemoveEventDialogListener(RemoveEventDialogListener removeEventDialogListener) {
		dialogs.get(DialogType.REMOVE_EVENT).setActionListener(removeEventDialogListener);
	}

	public void setAddTaskDialogListener(AddTaskDialogListener addTaskDialogListener) {
		dialogs.get(DialogType.ADD_TASK).setActionListener(addTaskDialogListener);
	}

	public void setAddUserDialogListener(AddUserDialogListener addUserDialogListener) {
		dialogs.get(DialogType.ADD_USER).setActionListener(addUserDialogListener);
	}

	public void setRemoveUserDialogListener(RemoveUserDialogListener removeUserDialogListener) {
		dialogs.get(DialogType.REMOVE_USER).setActionListener(removeUserDialogListener);
	}

	public void setEditUserDialogListener(EditUserDialogListener editUserDialogListener) {
		dialogs.get(DialogType.EDIT_USER).setActionListener(editUserDialogListener);
	}

	public void setAddSkillDialogListener(AddSkillDialogListener addSkillDialogListener) {
		dialogs.get(DialogType.ADD_SKILL).setActionListener(addSkillDialogListener);
	}
}
