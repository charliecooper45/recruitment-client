package gui.listeners;

import gui.ErrorDialogType;
import gui.MessageDialogType;
import gui.PanelType;
import gui.DialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Contact;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Listener for events on the add vacancy dialog.
 * @author Charlie
 */
public class AddVacancyDialogListener extends ClientListener implements ActionListener {
	public AddVacancyDialogListener(ClientController controller) {
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
					InputStream inputStream;
					RemoteInputStreamServer profileData = null;
					Vacancy vacancy = controller.getView().getVacancyDialogVacancy(DialogType.ADD_VACANCY);
					if (vacancy != null) {
						// the vacancy is valid and can be added
						try {
							if (vacancy.getProfile() != null) {
								File file = new File(vacancy.getProfile());
								inputStream = new FileInputStream(file);
								profileData = new SimpleRemoteInputStream(inputStream);
								vacancy.setProfile(file.getName());
							}
							boolean profileAdded = controller.getModel().addVacancy(vacancy, profileData);

							if (profileAdded) {
								controller.getView().hideDialog(DialogType.ADD_VACANCY);
								controller.getView().showMessageDialog(MessageDialogType.VACANCY_ADDED);
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
					}
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.ADD_VACANCY);
					break;
				case "..":
					File file = controller.getView().showFileChooser("Select profile to add.");
					if (file != null) {
						// update the view to show the new file
						controller.getView().displayFileInDialog(DialogType.ADD_VACANCY, file);
					}
					break;
				}
			} else if (source instanceof JComboBox<?>) {
				JComboBox<?> organisationCmbBx = (JComboBox<?>) source;
				Organisation selectedOrg = (Organisation) organisationCmbBx.getSelectedItem();
				if (selectedOrg != null) {
					List<Contact> contacts = controller.getModel().getOrganisationsContacts(selectedOrg);
					controller.getView().setDisplayedContactsInDialog(DialogType.ADD_VACANCY, contacts);
				}
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}
}
