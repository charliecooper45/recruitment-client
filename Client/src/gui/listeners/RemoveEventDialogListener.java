package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Event;

/**
 * Listener for events on the remove event dialog.
 * @author Charlie
 */
public class RemoveEventDialogListener extends ClientListener implements ActionListener {
	public RemoveEventDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText().trim();
			
			switch (text) {
			case "Confirm":
				Event oldEvent = controller.getView().getEventDialogEvent(DialogType.REMOVE_EVENT);
				boolean removed = controller.getModel().removeEvent(oldEvent.getEventId());
				
				if(removed) {
					controller.getView().showMessageDialog(MessageDialogType.EVENT_REMOVED);
					controller.getView().hideDialog(DialogType.REMOVE_EVENT);
					
					// update the events from the server
					Candidate candidate = controller.getView().getCandidatePanelCandidate();
					List<Event> events = controller.getModel().getCandidateEvents(candidate.getId());

					// update the view to display the events
					controller.getView().updateDisplayedCandidateEvents(events);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.REMOVE_EVENT_FAIL);
				}
				break;
			case "Cancel":
				controller.getView().hideDialog(DialogType.REMOVE_EVENT);
				break;
			}
		}
	}

}
