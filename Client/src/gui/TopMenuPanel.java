package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private MenuPanel[] menuOptions;

	public TopMenuPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setPreferredSize(new Dimension(500, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		init();
	}
	
	private void init() {
		// menu options
		menuOptions = new MenuPanel[4];
		menuOptions[0] = new MenuPanel("Vacancies");
		menuOptions[0].setSelected(true);
		menuOptions[1] = new MenuPanel("My Candidate Pipeline");
		menuOptions[2] = new MenuPanel("Organisations");
		menuOptions[3] = new MenuPanel("Search Candidates");
		for(MenuPanel panel : menuOptions) {
			add(panel);
		}
	}
	
	/**
	 * A class that holds each menu option in the TopMenuPanel 
	 */
	private class MenuPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private static final int WIDTH = 200;
		private static final int HEIGHT = 135;
		
		private String name;
		
		// components
		private JLabel nameLbl;
		
		public MenuPanel(String name) {
			this.name = name;
			setBackground(Color.GRAY);
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
}
