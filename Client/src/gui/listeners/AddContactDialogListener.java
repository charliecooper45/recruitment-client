package gui.listeners;

import gui.ConfirmDialogType;
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

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Contact;
import database.beans.Vacancy;

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
				InputStream inputStream;
				RemoteInputStreamServer profileData = null;
				Contact contact = controller.getView().getContactDialogContact(MenuDialogType.ADD_CONTACT);

				if (contact != null) {
					// the contact is valid and can be added
					boolean contactAdded = controller.getModel().addContact(contact);

					if (contactAdded) {
						controller.getView().hideMenuDialog(MenuDialogType.ADD_CONTACT);
						controller.getView().showConfirmDialog(ConfirmDialogType.CONTACT_ADDED);

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
				}
				/*
					try {
						if (vacancy.getProfile() != null) {
							File file = new File(vacancy.getProfile());
							inputStream = new FileInputStream(file);
							profileData = new SimpleRemoteInputStream(inputStream);
							vacancy.setProfile(file.getName());
						}
						//TODO NEXT: return a message to the user to say if the profile was added
						boolean profileAdded = controller.getModel().addVacancy(vacancy, profileData);

						if (profileAdded) {
							controller.getView().hideMenuDialog(MenuDialogType.ADD_VACANCY);
							controller.getView().showConfirmDialog(ConfirmDialogType.VACANCY_ADDED);
							// check if the vacancies panel is displayed and then update if necessary
							PanelType shownPanel = controller.getView().getDisplayedPanel();
							if (shownPanel == PanelType.VACANCIES) {
								VacanciesPanelListener listener = controller.getVacanciesPanelListener();
								List<Vacancy> vacancies = controller.getModel().getVacancies(listener.getDisplayOpenVacancies(), listener.getSelectedUser());
								controller.getView().updateVacanciesPanel(vacancies);
							}
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.ADD_VACANCY_FAIL);
						}
					} catch (FileNotFoundException e1) {
						// TODO NEXT B: handle exception
						e1.printStackTrace();
					}
						*/
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.ADD_CONTACT);
				break;
			}
		}
	}

}
