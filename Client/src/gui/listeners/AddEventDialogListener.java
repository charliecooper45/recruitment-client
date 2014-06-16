package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MainWindow;
import gui.MessageDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import controller.ClientController;
import database.beans.Event;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Listener for events on the add event dialog.
 * @author Charlie
 */
public class AddEventDialogListener extends ClientListener implements ActionListener {

	public AddEventDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source instanceof JComboBox<?>) {
			Organisation organisation = controller.getView().getEventDialogOrganisation();

			if (organisation != null) {
				List<Vacancy> vacancies = controller.getModel().getOrganisationVacancies(organisation.getId());
				controller.getView().setDisplayedVacanciesInDialog(DialogType.ADD_EVENT, vacancies);
			}
		} else if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText().trim();
			
			switch (text) {
			case "Confirm":
				Event newEvent = controller.getView().getEventDialogEvent(DialogType.ADD_EVENT);
				newEvent.setUserId(MainWindow.USER_ID);
				newEvent.setCandidate(controller.getView().getCandidatePanelCandidate());
				boolean added = controller.getModel().addEvent(newEvent);
				
				if(added) {
					controller.getView().showMessageDialog(MessageDialogType.EVENT_ADDED);
					controller.getView().hideDialog(DialogType.ADD_EVENT);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.ADD_EVENT_FAIL);
				}
				break;
			case "Cancel":
				controller.getView().hideDialog(DialogType.ADD_EVENT);
				break;
			}
		}
	}
}
