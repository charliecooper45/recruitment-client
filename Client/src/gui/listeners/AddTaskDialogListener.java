package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

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

		try {
			if (source instanceof JButton) {
				JButton button = (JButton) source;
				String text = button.getText();

				switch (text) {
				case "Confirm":
					Task task = controller.getView().getTaskDialogTask();

					if (task != null) {
						boolean taskAdded = controller.getModel().addTask(task);

						if (taskAdded) {
							List<Task> tasks = controller.getModel().getTasks(MainWindow.USER_ID);
							controller.getView().updateDisplayedTasks(tasks);
							controller.getView().hideDialog(DialogType.ADD_TASK);
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.ADD_TASK_FAIL);
						}
					}
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.ADD_TASK);
					break;
				}
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}
}
