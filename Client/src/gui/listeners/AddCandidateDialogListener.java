package gui.listeners;

import gui.MenuDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;

/**
 * Listener for events on the add candidate dialog.
 * @author Charlie
 */
public class AddCandidateDialogListener extends ClientListener implements ActionListener {
	public AddCandidateDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();

			switch (text) {
			case "Confirm":
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.ADD_CANDIDATE);
				break;
			}
		}
	}
}
