package gui;

import gui.listeners.ChangePanelListener;
import interfaces.UserType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	// listener that alerts the MainWindow to changes in the displayed panel
	private ChangePanelListener changePanelListener;

	public TopMenuPanel(UserType userType) {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(500, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		init(userType);
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
	}

	public void setChangePanelListener(ChangePanelListener changePanelListener) {
		this.changePanelListener = changePanelListener;
	}

	/**
	 * A panel that holds each menu option in the TopMenuPanel 
	 */
	private class MenuPanel extends JPanel {
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
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (MenuPanel panel : TopMenuPanel.this.menuOptions) {
						panel.setSelected(false);
					}
					setSelected(true);
					changePanelListener.changePanel(panelType);
				}
			});
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
	}
}
