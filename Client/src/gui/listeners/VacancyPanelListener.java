package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Vacancy;

/**
 * Listener for events on the vacancy panel.
 * @author Charlie
 */
public class VacancyPanelListener extends ClientListener implements ActionListener {

	public VacancyPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			if (button.getText().equals("Add profile")) {
				File file = controller.getView().showFileChooser("Select profile to add.");
				if (file != null) {
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(file);
						RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
						Vacancy vacancy = controller.getView().getDisplayedVacancy();
						String oldFileName = vacancy.getProfile();
						vacancy.setProfile(file.getName());
						boolean profileAdded = controller.getModel().addVacancyProfile(vacancy, remoteFileData, oldFileName);

						if (profileAdded) {
							String vacancyProfile = vacancy.getProfile();
							RemoteInputStream remoteVacancyProfileData = controller.getModel().getVacancyProfile(vacancyProfile);
							InputStream fileData = RemoteInputStreamClient.wrap(remoteVacancyProfileData);
							Path tempFile = controller.storeTempFile(fileData, vacancyProfile);
							controller.getView().showVacancyPanel(vacancy, tempFile);
						}
					} catch (FileNotFoundException e) {
						// TODO handle this exception
						e.printStackTrace();
					} catch (IOException e) {
						// TODO handle this exception
						e.printStackTrace();
					}
				}
			} else if (button.getText().equals("Remove profile")) {
				boolean confirm = controller.getView().showDialog(DialogType.VACANCY_REMOVE_PROFILE);
				if (confirm) {
					Vacancy vacancy = controller.getView().getDisplayedVacancy();
					if (vacancy.getProfile() != null) {
						if (controller.getModel().removeVacancyProfile(vacancy)) {
							vacancy.setProfile(null);
							controller.getView().showVacancyPanel(vacancy, null);
						}
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.VACANCY_NO_PROFILE);
					}
				}
			}
		} else if (source instanceof JComboBox<?>) {
			Vacancy displayedVacancy = controller.getView().getDisplayedVacancy();
			JComboBox<?> comboBox = (JComboBox<?>) source;
			boolean status = displayedVacancy.getStatus();

			String comboBoxValue = (String) comboBox.getSelectedItem();
			if (status == true && comboBoxValue.equals("Closed")) {
				if (controller.getView().showDialog(DialogType.VACANCY_CHANGE_STATUS_CLOSE)) {
					// close the vacancy
					controller.getModel().changeVacancyStatus(displayedVacancy);
				}
			} else if (status == false && comboBoxValue.equals("Open")) {
				if (controller.getView().showDialog(DialogType.VACANCY_CHANGE_STATUS_OPEN)) {
					// open the vacancy
					controller.getModel().changeVacancyStatus(displayedVacancy);
				}
			}
		}
	}
}
