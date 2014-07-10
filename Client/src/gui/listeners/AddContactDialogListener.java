package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Contact;

/**
 * Listener for events on the add contact dialog.
 * @author Charlie
 */
public class AddContactDialogListener extends ClientListener implements ActionListener {
	public AddContactDialogListener(ClientController controller) {
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
				Contact contact = controller.getView().getContactDialogContact(DialogType.ADD_CONTACT);

				if (contact != null) {
					// the contact is valid and can be added
					boolean contactAdded;
					try {
						contactAdded = controller.getModel().addContact(contact);

						if (contactAdded) {
							controller.getView().hideDialog(DialogType.ADD_CONTACT);
							controller.getView().showMessageDialog(MessageDialogType.CONTACT_ADDED);

							//TODO NEXT: if the organisation`s contacts are displayed then update them
							/*
							// check if the vacancies panel is displayed and then update if necessary
							PanelType shownPanel = controller.getView().getDisplayedPanel();
							if (shownPanel == PanelType.VACANCIES) {
								VacanciesPanelListener listener = controller.getVacanciesPanelListener();
								List<Vacancy> vacancies = controller.getModel().getVacancies(listener.getDisplayOpenVacancies(), listener.getSelectedUser());
								controller.getView().updateVacanciesPanel(vacancies);
							}
							*/
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.ADD_CONTACT_FAIL);
						}
					} catch (RemoteException e1) {
						controller.exitApplication();
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.ADD_CONTACT);
				break;
			}
		}
	}

}
