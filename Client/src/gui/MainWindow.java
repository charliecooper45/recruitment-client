package gui;

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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

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
	
	// JPanels that fill the centre of the GUI
	private Map<PanelTypes, JPanel> centrePanels;
	
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
		centrePanels = new EnumMap<>(PanelTypes.class);
		centrePanels.put(PanelTypes.VACANCIES, new VacanciesPanel());
		centrePanels.put(PanelTypes.PIPELINE, new CandidatePipelinePanel());
		centrePanels.put(PanelTypes.ORGANISATIONS, new OrganisationsPanel());
		centrePanels.put(PanelTypes.SEARCH, new SearchPanel());
		centrePanels.put(PanelTypes.ADMIN, new AdminPanel());
		centrePanels.put(PanelTypes.VACANCY, new VacancyPanel());
		centrePanels.put(PanelTypes.CANDIDATE, new CandidatePanel());
		centrePanels.put(PanelTypes.ORGANISATION, new OrganisationPanel());
	}

	private void init(UserType userType) {
		// create the menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Database Editor");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		String[] itemNames = {"Add Candidate", "Add Vacancy", "Add Organisation", "Remove Candidate", "Remove Vacancy", "Remove Organisation"};			
		JMenuItem[] menuItems = new JMenuItem[6];
		for(int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new JMenuItem(itemNames[i]);
			if(i < 3) {
				addMenu.add(menuItems[i]);
			} else {
				removeMenu.add(menuItems[i]);
			}
		}
		menu.add(addMenu);
		menu.add(removeMenu);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		topMenuPanel = new TopMenuPanel(userType);
		topMenuPanel.setChangePanelListener(new ChangePanelListener() {
			@Override
			public void changePanel(PanelTypes panelType) {
				// changeDisplayedPanel(panelType);
			}
		});
		add(topMenuPanel, BorderLayout.NORTH);
		taskListPanel = new TaskListPanel();
		add(taskListPanel, BorderLayout.EAST);
		
		// add listeners for switching GUI elements to the centre panels
		VacanciesPanel vacanciesPanel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		CandidatePipelinePanel candidatePipelinePanel = (CandidatePipelinePanel) centrePanels.get(PanelTypes.PIPELINE);
		candidatePipelinePanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				// changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
		OrganisationsPanel organisationsPanel = (OrganisationsPanel) centrePanels.get(PanelTypes.ORGANISATIONS);
		organisationsPanel.setOrganisationDisplayedListener(new OrganisationDisplayedListener() {
			@Override
			public void organisationDisplayed() {
				// changeDisplayedPanel(PanelTypes.ORGANISATION);
			}
		});
		SearchPanel searchPanel = (SearchPanel) centrePanels.get(PanelTypes.SEARCH);
		searchPanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				// changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
	}
	
	private void removeCentreComponent() {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);
		if(!(centreComponent == null)) 
			remove(centreComponent);
	}
	
	public void setVisible(boolean b, UserType userType, List<Vacancy> vacancies, List<User> users) {
		init(userType);
		if(b) {
		 	showVacanciesPanel(vacancies, users);
		}
		this.setVisible(b);
	}
	
	public void showVacanciesPanel(List<Vacancy> vacancies, List<User> users) {
		removeCentreComponent();
		
		// when the vacancies panel is displayed update the necessary fields from the server
		JPanel panel = centrePanels.get(PanelTypes.VACANCIES);
		VacanciesPanel vPanel = (VacanciesPanel) panel;
		vPanel.updateDisplayedUsers(users);
		vPanel.updateDisplayedVacancies(vacancies);
		add(panel);
		
		revalidate();
		repaint();
	}

	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		panel.updateDisplayedVacancies(vacancies);
	}

	public void showVacancyPanel(Vacancy updatedVacancy, Path tempFile) {
		removeCentreComponent();
		
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		panel.setDisplayedVacancy(updatedVacancy, tempFile);
		add(panel);
		
		revalidate();
		repaint();
	}
	
	public File showFileChooser(DialogTypes messageType) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		return panel.showFileChooser(messageType);
	}
	
	public boolean showDialog(DialogTypes dialogType) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		return panel.showDialog(dialogType);
	}
	
	public void showErrorDialog(ErrorMessages errorMessage) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		panel.showErrorDialog(errorMessage);
	}
	
	public Vacancy getSelectedVacancy() {
		VacanciesPanel vPanel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		return vPanel.getSelectedVacancy();
	}

	public Vacancy getDisplayedVacancy() {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		return panel.getDisplayedVacancy();
	}
	
	public void setVacanciesPanelListeners(ActionListener actionListener, MouseListener mouseListener) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		panel.setVacanciesPanelListeners(actionListener, mouseListener);
	}

	public void setVacancyPanelListener(ActionListener actionListener) {
		VacancyPanel panel = (VacancyPanel) centrePanels.get(PanelTypes.VACANCY);
		panel.setVacancyPanelListener(actionListener);
	}
}
