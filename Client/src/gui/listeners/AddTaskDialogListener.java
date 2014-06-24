package gui.listeners;

import gui.DialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Task;

/**
 * Listener for events on the add task dialog.
 * @author Charlie
 */
public class AddTaskDialogListener extends ClientListener implements ActionListener {
	public AddTaskDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			
			switch(text) {
			case "Confirm":
				Task task = controller.getView().getTaskDialogTask();
				
				if(task != null) {
					//TODO NEXT: send a message to the server here to add the task to the list of tasks then update the lists shown
					controller.getView().hideDialog(DialogType.ADD_TASK);
				}
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.ADD_TASK);
				break;
			}
		}
	}
}
