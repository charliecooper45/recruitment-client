package gui.listeners;

import gui.ConfirmDialogType;
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
 * Listener for events on the task list panel.
 * @author Charlie
 */
public class TaskListPanelListener extends ClientListener implements ActionListener {
	public TaskListPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		try {
			if (source instanceof JButton) {
				controller.getView().showDialog(DialogType.ADD_TASK);
			} else {
				boolean confirmed = controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_TASK);

				if (confirmed) {
					Task task = controller.getView().getTaskListPanelTask();

					boolean removed = controller.getModel().removeTask(task);

					if (removed) {
						List<Task> tasks = controller.getModel().getTasks(MainWindow.USER_ID);
						controller.getView().updateDisplayedTasks(tasks);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_TASK_FAIL);
					}
				} else {
					controller.getView().uncheckAllTaskListPanelTasks();
				}
			}
		} catch (RemoteException e1) {
			controller.exitApplication();
		}
	}
}
