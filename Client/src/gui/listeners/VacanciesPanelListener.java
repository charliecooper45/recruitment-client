package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

import controller.ClientController;
import database.beans.Contact;
import database.beans.Organisation;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Listener for events on the vacancies panel.
 * @author Charlie
 */
public class VacanciesPanelListener extends ClientListener implements ActionListener {
	private boolean openVacancies = true;
	private User selectedUser = null;

	public VacanciesPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		try {
			if (source instanceof JRadioButton) {
				// deal with changes to the type of vacancies to be shown
				JRadioButton button = (JRadioButton) source;
				if (button.getText().equals("All Vacancies")) {
					openVacancies = false;
					List<Vacancy> vacancies;
					vacancies = controller.getModel().getVacancies(openVacancies, selectedUser);
					controller.getView().updateVacanciesPanel(vacancies);
				} else {
					openVacancies = true;
					List<Vacancy> vacancies;
					vacancies = controller.getModel().getVacancies(openVacancies, selectedUser);
					controller.getView().updateVacanciesPanel(vacancies);
				}
			} else if (source instanceof JComboBox<?>) {
				// change whose vacancies are shown 
				JComboBox<?> usersCombo = (JComboBox<?>) source;
				selectedUser = (User) usersCombo.getSelectedItem();

				if (selectedUser != null) {
					if (selectedUser.getUserId() == null) {
						// this means we need to get all user`s vacancies
						selectedUser = null;
					}
					List<Vacancy> vacancies;
					vacancies = controller.getModel().getVacancies(openVacancies, selectedUser);
					controller.getView().updateVacanciesPanel(vacancies);
				}
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			// retrieve the selected vacancy so it`s values can be updated from the server before the user sees it
			Vacancy selectedVacancy = controller.getView().getSelectedVacancy();
			Vacancy updatedVacancy;
			Path tempFile = null;

			// get the vacancy profile
			try {
				updatedVacancy = controller.getModel().getVacancy(selectedVacancy.getVacancyId());
				String vacancyProfile = updatedVacancy.getProfile();
				if (vacancyProfile != null) {
					RemoteInputStream remoteFileData = controller.getModel().getVacancyProfile(vacancyProfile);
					InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
					tempFile = controller.storeTempFile(fileData, vacancyProfile);
				}
				List<Contact> contacts = controller.getModel().getOrganisationsContacts(new Organisation(updatedVacancy.getOrganisationId(), updatedVacancy.getOrganisationName(), null, null, null, null, null, null, null, -1));
				controller.getView().showVacancyPanel(updatedVacancy, tempFile, contacts);
			} catch (RemoteException e2) {
				controller.exitApplication();
			} catch (IOException e1) {
				// TODO NEXT B: Possible display an error message here
				e1.printStackTrace();
			}
		}
	}

	public boolean getDisplayOpenVacancies() {
		return openVacancies;
	}

	public User getSelectedUser() {
		return selectedUser;
	}
}
