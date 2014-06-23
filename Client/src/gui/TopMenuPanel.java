package gui;

import gui.TopMenuPanel.MenuPanel;
import gui.listeners.TopMenuListener;
import interfaces.UserType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays the top menu 
 * @author Charlie
 */
public class TopMenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	private MenuPanel[] menuOptions;
	private JPanel menuPanelsPanel;
	private JPanel adminPanel;

	private TopMenuListener topMenuListener;

	public TopMenuPanel() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(500, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	private void init(UserType userType) {
		gbc = new GridBagConstraints();
		gbc.weightx = 5;
		gbc.weighty = 1;

		// menu options
		menuPanelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuOptions = new MenuPanel[5];
		menuOptions[0] = new MenuPanel("Vacancies", PanelType.VACANCIES);
		menuOptions[0].setSelected(true);
		menuOptions[1] = new MenuPanel("My Candidate Pipeline", PanelType.PIPELINE);
		menuOptions[2] = new MenuPanel("Organisations", PanelType.ORGANISATIONS);
		menuOptions[3] = new MenuPanel("Search Candidates", PanelType.SEARCH);
		for (int i = 0; i < 4; i++) {
			menuPanelsPanel.add(menuOptions[i]);
		}
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(menuPanelsPanel, gbc);

		if (userType == UserType.ADMINISTRATOR) {
			// admin option
			adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			menuOptions[4] = new MenuPanel("Admin", PanelType.ADMIN);
			adminPanel.add(menuOptions[4]);
			Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.BOTH);
			add(adminPanel, gbc);
		}
		
		for(MenuPanel menuPanel : menuOptions) {
			if(menuPanel != null) 
				menuPanel.addMouseListener(topMenuListener);
		}
	}

	public void setUserType(UserType userType) {
		init(userType);
	}
	
	public void setSelectedPanel(MenuPanel panel) {
		for (MenuPanel menuPanel : TopMenuPanel.this.menuOptions) {
			menuPanel.setSelected(false);
		}
		
		panel.setSelected(true);
	}
	
	public void setTopMenuListener(TopMenuListener topMenuListener) {
		this.topMenuListener = topMenuListener;
	}

	/**
	 * A panel that holds each menu option in the TopMenuPanel 
	 */
	public class MenuPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private String name;
		private PanelType panelType;

		// components
		private JLabel nameLbl;

		public MenuPanel(String name, PanelType panelType) {
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
		}

		private void setSelected(boolean selected) {
			if (selected) {
				setBorder(BorderFactory.createLoweredBevelBorder());
				nameLbl.setForeground(Color.WHITE);
			} else {
				setBorder(BorderFactory.createRaisedBevelBorder());
				nameLbl.setForeground(Color.BLACK);
			}
		}

		public PanelType getPanelType() {
			return panelType;
		}
	}
}
