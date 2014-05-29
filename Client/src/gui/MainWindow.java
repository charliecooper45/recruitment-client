package gui;

import gui.TopMenuPanel.MenuPanel;
import gui.dialogs.AddOrganisationDialog;
import gui.dialogs.AddVacancyDialog;
import gui.dialogs.RecruitmentDialog;
import gui.dialogs.RemoveVacancyDialog;
import gui.listeners.AddOrganisationDialogListener;
import gui.listeners.CandidateDisplayedListener;
import gui.listeners.OrganisationPanelListener;
import gui.listeners.OrganisationsPanelListener;
import gui.listeners.RemoveVacancyDialogListener;
import gui.listeners.TopMenuListener;
import gui.listeners.VacanciesPanelListener;
import interfaces.UserType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
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

import database.beans.Contact;
import database.beans.Organisation;
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
	private JPanel taskListPanel;

	// the menu items displayed in the menu
	private JMenuItem[] menuItems;

	// JPanels that fill the centre of the GUI
	private Map<PanelType, JPanel> centrePanels;

	// Dialog windows 
	private Map<MenuDialogType, RecruitmentDialog> dialogs;

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

		// create the dialogs
		dialogs = new EnumMap<>(MenuDialogType.class);
		dialogs.put(MenuDialogType.ADD_VACANCY, new AddVacancyDialog(this));
		dialogs.put(MenuDialogType.REMOVE_VACANCY, new RemoveVacancyDialog(this));
		dialogs.put(MenuDialogType.ADD_ORGANISATION, new AddOrganisationDialog(this));
	}

	private void init(UserType userType) {
		borderLayout = new BorderLayout();
		setLayout(borderLayout);

		// initialize the TopMenuPanel according to the user type
		topMenuPanel.setUserType(userType);
		add(topMenuPanel, BorderLayout.NORTH);
		taskListPanel = new TaskListPanel();
		add(taskListPanel, BorderLayout.EAST);

		// add listeners for switching GUI elements to the centre panels
		VacanciesPanel vacanciesPanel = (VacanciesPanel) centrePanels.get(PanelType.VACANCIES);
		CandidatePipelinePanel candidatePipelinePanel = (CandidatePipelinePanel) centrePanels.get(PanelType.PIPELINE);
		candidatePipelinePanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				// changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
		SearchPanel searchPanel = (SearchPanel) centrePanels.get(PanelType.SEARCH);
		searchPanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				// changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
	}

	private void removeCentreComponent() {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);
		if (!(centreComponent == null))
			remove(centreComponent);
	}

	public void setVisible(String userId, boolean visible, UserType userType, List<Vacancy> vacancies, List<User> users) {
		USER_ID = userId;
		init(userType);
		if (visible) {
			showVacanciesPanel(vacancies, users);
		}
		super.setVisible(visible);
	}
	
	// PanelType methods
	public PanelType getDisplayedPanel() {
		for (Map.Entry<PanelType, JPanel> e : centrePanels.entrySet()) {
		    Object value = e.getValue();
		    if(value == borderLayout.getLayoutComponent(BorderLayout.CENTER)) {
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
	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile) {
		removeCentreComponent();

		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.setDisplayedVacancy(updatedVacancy, tempFile);
		add(panel);

		revalidate();
		repaint();
	}

	public Vacancy getDisplayedVacancy() {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		return panel.getDisplayedVacancy();
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
	
	// OrganisationPanel method
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
	
	public boolean showDialog(DialogType dialogType) {
		JPanel panel = null;
		VacancyPanel vacancyPanel = null;
		OrganisationPanel organisationPanel = null;
		int response;
		
		switch (dialogType) {
		case VACANCY_REMOVE_PROFILE:
			vacancyPanel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
			response = JOptionPane.showConfirmDialog(vacancyPanel, DialogType.VACANCY_REMOVE_PROFILE.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if(response == 0)
				return true;
			break;
		case VACANCY_ADD_PROFILE:
			break;
		case VACANCY_CHANGE_STATUS_OPEN:
			vacancyPanel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
			response = JOptionPane.showConfirmDialog(vacancyPanel, DialogType.VACANCY_CHANGE_STATUS_OPEN.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if(response == 0) {
				vacancyPanel.setVacancyStatus(true);
				return true;
			}
			break;
		case VACANCY_CHANGE_STATUS_CLOSE:
			vacancyPanel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
			response = JOptionPane.showConfirmDialog(vacancyPanel, DialogType.VACANCY_CHANGE_STATUS_CLOSE.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if(response == 0) {
				vacancyPanel.setVacancyStatus(false);
				return true;
			}
			break;
		case REMOVE_VACANCY:
			panel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);
			response = JOptionPane.showConfirmDialog(panel, DialogType.REMOVE_VACANCY.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if(response == 0) {
				return true;
			}
			break;
		case ORGANISATION_REMOVE_TOB:
			organisationPanel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
			response = JOptionPane.showConfirmDialog(organisationPanel, DialogType.ORGANISATION_REMOVE_TOB.getMessage(), "Confirm.", JOptionPane.YES_NO_OPTION);
			if(response == 0)
				return true;
			break;
		}
		return false;
	}

	public void showErrorDialog(ErrorDialogType errorDialog) {
		JOptionPane.showMessageDialog(borderLayout.getLayoutComponent(BorderLayout.CENTER), errorDialog.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showConfirmDialog(ConfirmDialogType confirmDialog) {
		JOptionPane.showMessageDialog(borderLayout.getLayoutComponent(BorderLayout.CENTER), confirmDialog.getMessage(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showMenuDialog(MenuDialogType menuDialog) {
		switch (menuDialog) {
		case ADD_VACANCY:
			dialogs.get(MenuDialogType.ADD_VACANCY).setVisible(true);
			break;
		case REMOVE_VACANCY:
			dialogs.get(MenuDialogType.REMOVE_VACANCY).setVisible(true);
			break;
		case ADD_ORGANISATION:
			dialogs.get(MenuDialogType.ADD_ORGANISATION).setVisible(true);
			break;
		}
	}

	public void hideMenuDialog(MenuDialogType menuDialog) {
		switch (menuDialog) {
		case ADD_VACANCY:
			//TODO NEXT: remove data from all the fields when setvisible is called with the argument false
			dialogs.get(MenuDialogType.ADD_VACANCY).setVisible(false);
			break;
		case REMOVE_VACANCY:
			dialogs.get(MenuDialogType.REMOVE_VACANCY).setVisible(false);
			break;
		case ADD_ORGANISATION:
			dialogs.get(MenuDialogType.ADD_ORGANISATION).setVisible(false);
			break;
		}
	}

	public void setDisplayedOrganisationsInDialog(MenuDialogType menuDialog, List<Organisation> organisations) {
		switch (menuDialog) {
		case ADD_VACANCY:
			dialogs.get(MenuDialogType.ADD_VACANCY).setDisplayedOrganisations(organisations);
			break;
		}
	}

	public void setDisplayedContactsInDialog(MenuDialogType menuDialog, List<Contact> contacts) {
		switch (menuDialog) {
		case ADD_VACANCY:
			dialogs.get(MenuDialogType.ADD_VACANCY).setDisplayedContacts(contacts);
			break;
		}
	}
	
	public void setDisplayedVacanciesInDialog(MenuDialogType menuDialog, List<Vacancy> vacancies) {
		switch (menuDialog) {
		case REMOVE_VACANCY:
			dialogs.get(MenuDialogType.REMOVE_VACANCY).setDisplayedVacancies(vacancies);
			break;
		}
			
	}
	
	public void displayFileInDialog(MenuDialogType menuDialogType, File file) {
		RecruitmentDialog dialog = dialogs.get(menuDialogType);
		dialog.setDisplayedFile(file);
	}
	
	public Vacancy getVacancyDialogVacancy(MenuDialogType menuDialog) {
		if(menuDialog == MenuDialogType.ADD_VACANCY) {
			AddVacancyDialog dialog = (AddVacancyDialog) dialogs.get(MenuDialogType.ADD_VACANCY);
			return dialog.getVacancy();
		} else if (menuDialog == MenuDialogType.REMOVE_VACANCY) {
			RemoveVacancyDialog dialog = (RemoveVacancyDialog) dialogs.get(MenuDialogType.REMOVE_VACANCY);
			return dialog.getVacancy();
		}
		return null;
	}

	public Organisation getOrganisationDialogVacancy(MenuDialogType menuDialog) {
		AddOrganisationDialog dialog = (AddOrganisationDialog) dialogs.get(MenuDialogType.ADD_ORGANISATION);
		return dialog.getOrganisation();
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
		panel.setVacanciesPanelListeners(vacanciesPanelListener);
	}

	public void setVacancyPanelListener(ActionListener actionListener) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.setVacancyPanelListener(actionListener);
	}

	public void setOrganisationsPanelListener(OrganisationsPanelListener organisationsPanelListener) {
		OrganisationsPanel panel = (OrganisationsPanel) centrePanels.get(PanelType.ORGANISATIONS);
		panel.setOrganisationsPanelListener(organisationsPanelListener);
	}
	
	public void setOrganisationPanelListener(OrganisationPanelListener organisationPanelListener) {
		OrganisationPanel panel = (OrganisationPanel) centrePanels.get(PanelType.ORGANISATION);
		panel.setOrganisationPanelListener(organisationPanelListener);
	}
	
	public void setAddVacancyDialogListener(ActionListener actionListener) {
		dialogs.get(MenuDialogType.ADD_VACANCY).setActionListener(actionListener);
	}

	public void setRemoveVacancyDialogListener(RemoveVacancyDialogListener removeVacancyDialogListener) {
		dialogs.get(MenuDialogType.REMOVE_VACANCY).setActionListener(removeVacancyDialogListener);
	}

	public void setAddOrganisationDialogListener(AddOrganisationDialogListener addOrganisationDialogListener) {
		dialogs.get(MenuDialogType.ADD_ORGANISATION).setActionListener(addOrganisationDialogListener);
	}

}
