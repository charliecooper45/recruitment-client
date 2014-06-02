package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MenuDialogType;
import gui.PanelType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Contact;
import database.beans.Organisation;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Listens for events on the remove contact dialog. 
 * @author Charlie
 */
public class RemoveContactDialogListener extends ClientListener implements ActionListener {
	public RemoveContactDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			
			switch (text) {
			case "Confirm":
				boolean confirmed = controller.getView().showDialog(DialogType.REMOVE_CONTACT);
				
				if(confirmed) {
					Contact contact = controller.getView().getContactDialogContact(MenuDialogType.REMOVE_CONTACT);
					
					if(contact == null) {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_CONTACT_FAIL);
						return;
					}
					
					boolean deleted = controller.getModel().removeContact(contact);
					
					if(deleted) {
						controller.getView().hideMenuDialog(MenuDialogType.REMOVE_CONTACT);
						controller.getView().showConfirmDialog(ConfirmDialogType.CONTACT_REMOVED);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_CONTACT_FAIL);
					}
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
				}
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.REMOVE_CONTACT);
				break;
			}
		} else if(source instanceof JComboBox<?>) {
			JComboBox<?> organisationCmbBx = (JComboBox<?>) source;
			Organisation selectedOrg = (Organisation) organisationCmbBx.getSelectedItem();
			if (selectedOrg != null) {
				List<Contact> contacts = controller.getModel().getOrganisationsContacts(selectedOrg);
				controller.getView().setDisplayedContactsInDialog(MenuDialogType.REMOVE_CONTACT, contacts);
			}
		}
	}
}
