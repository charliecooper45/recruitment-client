package gui.listeners;

import gui.DialogType;

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
	public void actionPerformed(ActionEvent event) {
		controller.getView().showDialog(DialogType.ADD_TASK);
	}
}
