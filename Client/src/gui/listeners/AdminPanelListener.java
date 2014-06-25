package gui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

import controller.ClientController;

/**
 * Listener for events on the admin panel.
 * @author Charlie
 */
public class AdminPanelListener extends ClientListener {
	public AdminPanelListener(ClientController controller) {
		super(controller);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		Object source = event.getSource();

		if (source instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) source;

			int index = tabbedPane.getSelectedIndex();
			
		}
	}
}
