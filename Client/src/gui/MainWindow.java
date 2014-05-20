package gui;

import gui.listeners.CandidateDisplayedListener;
import gui.listeners.ChangePanelListener;
import gui.listeners.ClientViewListener;
import gui.listeners.VacancyDisplayedListener;
import gui.listeners.OrganisationDisplayedListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
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
	
	// sends messages to the ClientView class 
	private ClientViewListener clientViewListener;
	
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
		
		init();
	}

	private void init() {
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
		
		topMenuPanel = new TopMenuPanel();
		topMenuPanel.setChangePanelListener(new ChangePanelListener() {
			@Override
			public void changePanel(PanelTypes panelType) {
				changeDisplayedPanel(panelType);
			}
		});
		add(topMenuPanel, BorderLayout.NORTH);
		taskListPanel = new TaskListPanel();
		add(taskListPanel, BorderLayout.EAST);
		
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
		
		// add listeners to the centre panels
		VacanciesPanel vacanciesPanel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		vacanciesPanel.setVacancyDisplayedListener(new VacancyDisplayedListener() {
			@Override
			public void vacancyDisplayed() {
				changeDisplayedPanel(PanelTypes.VACANCY);
			}
		});
		CandidatePipelinePanel candidatePipelinePanel = (CandidatePipelinePanel) centrePanels.get(PanelTypes.PIPELINE);
		candidatePipelinePanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
		OrganisationsPanel organisationsPanel = (OrganisationsPanel) centrePanels.get(PanelTypes.ORGANISATIONS);
		organisationsPanel.setOrganisationDisplayedListener(new OrganisationDisplayedListener() {
			@Override
			public void organisationDisplayed() {
				changeDisplayedPanel(PanelTypes.ORGANISATION);
			}
		});
		SearchPanel searchPanel = (SearchPanel) centrePanels.get(PanelTypes.SEARCH);
		searchPanel.setCandidateDisplayedListener(new CandidateDisplayedListener() {
			@Override
			public void candidateDisplayed() {
				changeDisplayedPanel(PanelTypes.CANDIDATE);
			}
		});
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			changeDisplayedPanel(PanelTypes.VACANCIES);
		}
		super.setVisible(b);
	}
	
	private void changeDisplayedPanel(PanelTypes panelType) {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);
		if(!(centreComponent == null)) 
			remove(centreComponent);
		
		JPanel panel = centrePanels.get(panelType);
		
		add(panel);
		
		if(panelType == PanelTypes.VACANCIES) {
			// when the vacancies panel is displayed update the necessary fields from the server
			VacanciesPanel vPanel = (VacanciesPanel) panel;
			vPanel.updateDisplayedVacancies(clientViewListener.getVacancies(vPanel.getVacancyType(), vPanel.getUser()));
			vPanel.updateDisplayedUsers(clientViewListener.getUsers(null, true));
		}
		
		revalidate();
		repaint();
	}

	public void updateVacanciesPanel(List<Vacancy> vacancies) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		panel.updateDisplayedVacancies(vacancies);
	}
	
	public void setClientViewListener(ClientViewListener clientViewListener) {
		this.clientViewListener = clientViewListener;
	}
	
	public void setVacanciesListener(ActionListener listener) {
		VacanciesPanel panel = (VacanciesPanel) centrePanels.get(PanelTypes.VACANCIES);
		panel.setActionListener(listener);
	}
}
