package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ClientController;

/**
 * Listener for events on the task list panel.
 * @author Charlie
 */
public class TaskListPanelListener extends ClientListener implements ActionListener {
	public TaskListPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.err.println("Add task button pressed!");
	}
}
