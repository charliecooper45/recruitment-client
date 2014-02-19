package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private PanelTypes displayedPanel;
	private Map<PanelTypes, JPanel> centrePanels;
	
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(850, 600));
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
		centrePanels = new HashMap<>();
		centrePanels.put(PanelTypes.VACANCIES, new VacanciesPanel());
		centrePanels.put(PanelTypes.PIPELINE, new CandidatePipelinePanel());
		centrePanels.put(PanelTypes.ORGANISATIONS, new OrganisationsPanel());
		centrePanels.put(PanelTypes.SEARCH, new SearchPanel());
		changeDisplayedPanel(PanelTypes.VACANCIES);
	}
	
	private void changeDisplayedPanel(PanelTypes panel) {
		Component centreComponent = borderLayout.getLayoutComponent(BorderLayout.CENTER);
		if(!(centreComponent == null)) 
			remove(centreComponent);
		
		displayedPanel = panel;
		add(centrePanels.get(panel));
		
		revalidate();
		repaint();
	}
}
