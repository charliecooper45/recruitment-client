package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays the top menu 
 * @author Charlie
 */
public class TopMenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints gbc;
	
	private JPanel menuPanelsPanel;
	private MenuPanel[] menuOptions;
	private EditorPanel editorPanel;
	
	// listener that alerts the MainWindow to changes in the displayed panel
	private ChangePanelListener changePanelListener;

	public TopMenuPanel() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(500, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		init();
	}
	
	private void init() {
		gbc = new GridBagConstraints();
		gbc.weightx = 2;
		gbc.weighty = 1;
		
		// menu options
		menuPanelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuOptions = new MenuPanel[4];
		menuOptions[0] = new MenuPanel("Vacancies", PanelTypes.VACANCIES);
		menuOptions[0].setSelected(true);
		menuOptions[1] = new MenuPanel("My Candidate Pipeline", PanelTypes.PIPELINE);
		menuOptions[2] = new MenuPanel("Organisations", PanelTypes.ORGANISATIONS);
		menuOptions[3] = new MenuPanel("Search Candidates", PanelTypes.SEARCH);
		for(MenuPanel panel : menuOptions) {
			menuPanelsPanel.add(panel);
		}
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(menuPanelsPanel, gbc);
		
		// editor panel
		gbc.weightx = 1;
		gbc.insets = new Insets(5, 0, 5, 0);
		editorPanel = new EditorPanel();
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(editorPanel, gbc);
	}
	
	public void setChangePanelListener(ChangePanelListener changePanelListener) {
		this.changePanelListener = changePanelListener;
	}
	
	/**
	 * A panel that holds each menu option in the TopMenuPanel 
	 */
	private class MenuPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private String name;
		private PanelTypes panelType;
		
		// components
		private JLabel nameLbl;
		
		public MenuPanel(String name, PanelTypes panelType) {
			this.panelType = panelType;
			this.name = name;
			setPreferredSize(new Dimension(150, 135));
			setBackground(Color.GRAY);
			setBorder(BorderFactory.createRaisedBevelBorder());
			init();
		}
		
		private void init() {
			nameLbl = new JLabel(name);
			add(nameLbl);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for(MenuPanel panel : TopMenuPanel.this.menuOptions) {
						panel.setSelected(false);
					}
					setSelected(true);
					changePanelListener.changePanel(panelType);
				}
			});
		}
		
		private void setSelected(boolean selected) {
			if(selected) {
				setBorder(BorderFactory.createLoweredBevelBorder());
				nameLbl.setForeground(Color.WHITE);
			} else {
				setBorder(BorderFactory.createRaisedBevelBorder());
				nameLbl.setForeground(Color.BLACK);
			}
		}
	}

	/**
	 * A panel that holds the actions the user can do on the database
	 */
	private class EditorPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private GridBagConstraints editorGbc;
		
		// components
		private JLabel[] labels;
		
		public EditorPanel() {
			init();
		}
		
		private void init() {
			setLayout(new GridBagLayout());
			editorGbc = new GridBagConstraints();
			editorGbc.weightx = 1;
			editorGbc.weighty = 1;
			editorGbc.anchor = GridBagConstraints.LINE_START;
			editorGbc.insets = new Insets(0, 10, 0, 10);
			
			labels = new JLabel[6];
			labels[0] = new JLabel("<html><a href=\"\">"+ "Add Candidate" +"</a></html>");
			labels[1] = new JLabel("<html><a href=\"\">"+ "Remove Candidate" +"</a></html>");
			labels[2]  = new JLabel("<html><a href=\"\">"+ "Add Vacancy" +"</a></html>");
			labels[3] = new JLabel("<html><a href=\"\">"+ "Remove Vacancy" +"</a></html>");
			labels[4] = new JLabel("<html><a href=\"\">"+ "Add Organisation" +"</a></html>");
			labels[5] = new JLabel("<html><a href=\"\">"+ "Remove Organisation" +"</a></html>");
			
			int row = 1;
			int column = 1;
			for(JLabel label : labels) {
				label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Utils.setGBC(editorGbc, column, row, 1, 1, GridBagConstraints.NONE);
				add(label, editorGbc);
				
				if(column == 2) {
					row++;
					column = 1;
				} else {
					column++;
				}
			}
		}
	}
}
