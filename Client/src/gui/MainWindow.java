package gui;

import gui.dialogs.AddVacancyDialog;
import gui.dialogs.RecruitmentDialog;
import gui.listeners.CandidateDisplayedListener;
import gui.listeners.ChangePanelListener;
import gui.listeners.OrganisationDisplayedListener;
import interfaces.UserType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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

		// create the dialogs
		dialogs = new EnumMap<>(MenuDialogType.class);
		dialogs.put(MenuDialogType.ADD_VACANCY, new AddVacancyDialog(this));
	}

	private void init(UserType userType) {
		borderLayout = new BorderLayout();
		setLayout(borderLayout);

		topMenuPanel = new TopMenuPanel(userType);
		topMenuPanel.setChangePanelListener(new ChangePanelListener() {
			@Override
			public void changePanel(PanelType panelType) {
				// changeDisplayedPanel(panelType);
			}
		});
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
		OrganisationsPanel organisationsPanel = (OrganisationsPanel) centrePanels.get(PanelType.ORGANISATIONS);
		organisationsPanel.setOrganisationDisplayedListener(new OrganisationDisplayedListener() {
			@Override
			public void organisationDisplayed() {
				// changeDisplayedPanel(PanelTypes.ORGANISATION);
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

	public void setVisible(boolean b, UserType userType, List<Vacancy> vacancies, List<User> users) {
		init(userType);
		if (b) {
			showVacanciesPanel(vacancies, users);
		}
		this.setVisible(b);
	}

	// VacanciesPanel methods
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		removeCentreComponent();

		// when the vacancies panel is displayed update the necessary fields from the server
		JPanel panel = centrePanels.get(PanelType.VACANCIES);
		VacanciesPanel vPanel = (VacanciesPanel) panel;
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

	public boolean showDialog(DialogType dialogType) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		return panel.showDialog(dialogType);
	}

	public void showErrorDialog(ErrorDialogType errorMessage) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.showErrorDialog(errorMessage);
	}

	public void showMenuDialog(MenuDialogType menuDialog) {
		switch (menuDialog) {
		case ADD_VACANCY:
			dialogs.get(MenuDialogType.ADD_VACANCY).setVisible(true);
			break;
		}
	}

	public void hideMenuDialog(MenuDialogType menuDialog) {
		switch (menuDialog) {
		case ADD_VACANCY:
			//TODO NEXT: remove data from all the fields when setvisible is called with the argument false
			dialogs.get(MenuDialogType.ADD_VACANCY).setVisible(false);
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
	
	public void displayFileInDialog(MenuDialogType menuDialogType, File file) {
		RecruitmentDialog dialog = dialogs.get(menuDialogType);
		dialog.setDisplayedFile(file);
	}
	
	public Vacancy getVacancyDialogVacancy() {
		AddVacancyDialog dialog = (AddVacancyDialog) dialogs.get(MenuDialogType.ADD_VACANCY);
		return dialog.getVacancy();
	}

	// methods to set listeners
	public void setMenuListener(ActionListener actionListener) {
		for (JMenuItem menuItem : menuItems) {
			menuItem.addActionListener(actionListener);
		}
	}
	
	public void setVacanciesPanelListeners(ActionListener actionListener, MouseListener mouseListener) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelType.VACANCIES);
		panel.setVacanciesPanelListeners(actionListener, mouseListener);
	}

	public void setVacancyPanelListener(ActionListener actionListener) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelType.VACANCY);
		panel.setVacancyPanelListener(actionListener);
	}

	public void setAddVacancyDialogListener(ActionListener actionListener) {
		dialogs.get(MenuDialogType.ADD_VACANCY).setActionListener(actionListener);
	}
}
