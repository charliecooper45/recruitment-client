package gui.listeners;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import controller.ClientController;
import database.beans.Event;

/**
 * Listener for events on the candidate pipeline panel.
 * @author Charlie
 */
public class CandidatePipelinePanelListener extends ClientListener implements ActionListener {
	public CandidatePipelinePanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// one of the options was selected so get the list of options
		boolean[] options = controller.getView().getCandidatePipelinePanelOptions();
		
		// retrieve the appropriate events off the server 
		List<Event> events = controller.getModel().getEvents(options[0], options[1], options[2], options[3], options[4], MainWindow.USER_ID);
		
		controller.getView().updateCandidatePipelinePanel(events);
	}
}
