package controller;

import gui.ClientView;
import gui.DialogTypes;
import gui.ErrorMessages;
import interfaces.UserType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import model.ClientModel;
import model.LoginAttempt;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import database.beans.User;
import database.beans.Vacancy;

/**
 * Controller part of MVC, responsible for interaction between the view and the model.
 * @author Charlie
 */
public class ClientController {
	private final ClientView view;
	private final ClientModel model;

	public ClientController(ClientView view, ClientModel model) {
		this.view = view;
		this.model = model;
		this.view.setController(this);

		// shows the GUI to the user and sets the login listener for the login screen
		view.showGUI(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LoginAttempt attempt = ClientController.this.view.getLoginAttempt();
				String message = ClientController.this.model.login(attempt);

				List<Vacancy> vacancies = ClientController.this.model.getVacancies(true, null);
				List<User> users = ClientController.this.model.getUsers(null, true);

				if (message.equals(UserType.ADMINISTRATOR.toString())) {
					// the user is an administrator
					ClientController.this.view.displayMainWindow(UserType.ADMINISTRATOR, vacancies, users);
				} else if (message.equals(UserType.STANDARD.toString())) {
					// the user is a standard user
					ClientController.this.view.displayMainWindow(UserType.STANDARD, vacancies, users);
				} else {
					ClientController.this.view.showLoginErrorMessage(message);
				}
			}
		});

		// sets the listeners for the GUI
		view.setVacanciesPanelListeners(new ActionListener() {
			private boolean openVacancies = true;
			private User selectedUser = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();

				if (source instanceof JRadioButton) {
					// deal with changes to the type of vacancies to be shown
					JRadioButton button = (JRadioButton) source;
					if (button.getText().equals("All Vacancies")) {
						openVacancies = false;
						List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					} else {
						openVacancies = true;
						List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
						ClientController.this.view.updateVacanciesPanel(vacancies);
					}
				} else if (source instanceof JComboBox<?>) {
					// change whose vacancies are shown 
					JComboBox<?> usersCombo = (JComboBox<?>) source;
					selectedUser = (User) usersCombo.getSelectedItem();

					if (selectedUser.getUserId() == null) {
						// this means we need to get all user`s vacancies
						selectedUser = null;
					}

					List<Vacancy> vacancies = ClientController.this.model.getVacancies(openVacancies, selectedUser);
					ClientController.this.view.updateVacanciesPanel(vacancies);
				}
			}
		}, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					// retrieve the selected vacancy so it`s values can be updated from the server before the user sees it
					Vacancy selectedVacancy = ClientController.this.view.getSelectedVacancy();
					Vacancy updatedVacancy = ClientController.this.model.getVacancy(selectedVacancy.getVacancyId());
					Path tempFile = null;

					// get the vacancy profile
					try {
						String vacancyProfile = selectedVacancy.getProfile();
						if (vacancyProfile != null) {
							RemoteInputStream remoteFileData = ClientController.this.model.getVacancyProfile(vacancyProfile);
							InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
							tempFile = storeFile(fileData, vacancyProfile);
						}
						//TODO NEXT: output this file to the GUI
					} catch (IOException e1) {
						// TODO NEXT B: Possible display an error message here
						e1.printStackTrace();
					}
					ClientController.this.view.showVacancyPanel(updatedVacancy, tempFile);
				}
			}
		});
		view.setVacancyPanelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Object source = event.getSource();
				if (source instanceof JButton) {
					JButton button = (JButton) source;
					if (button.getText().equals("Add profile")) {
						File file = ClientController.this.view.showFileChooser(DialogTypes.VACANCYADDPROFILE);
						if (file != null) {
							InputStream inputStream = null;
							try {
								inputStream = new FileInputStream(file);
								RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
								Vacancy vacancy = ClientController.this.view.getDisplayedVacancy();
								String oldFileName = vacancy.getProfile();
								vacancy.setProfile(file.getName());
								boolean profileAdded = ClientController.this.model.addVacancyProfile(vacancy, remoteFileData, oldFileName);

								if (profileAdded) {
									String vacancyProfile = vacancy.getProfile();
									RemoteInputStream remoteVacacnyProfileData = ClientController.this.model.getVacancyProfile(vacancyProfile);
									InputStream fileData = RemoteInputStreamClient.wrap(remoteVacacnyProfileData);
									Path tempFile = storeFile(fileData, vacancyProfile);
									ClientController.this.view.showVacancyPanel(vacancy, tempFile);
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
						boolean confirm = ClientController.this.view.showDialog(DialogTypes.VACANCYREMOVEPROFILE);
						if (confirm) {
							Vacancy vacancy = ClientController.this.view.getDisplayedVacancy();
							if (vacancy.getProfile() != null) {
								if (ClientController.this.model.removeVacancyProfile(vacancy)) {
									vacancy.setProfile(null);
									ClientController.this.view.showVacancyPanel(vacancy, null);
								}
							} else {
								ClientController.this.view.showErrorDialog(ErrorMessages.VACANCYNOPROFILE);
							}
						}
					}
				}
			}
		});
	}

	private Path storeFile(InputStream inStream, String name) {
		// write the file to a temp file
		try {
			String suffix = name.substring(name.lastIndexOf("."));
			Path tempFile = Files.createTempFile(null, suffix);
			tempFile.toFile().deleteOnExit();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
			FileOutputStream outputStream;

			outputStream = new FileOutputStream(tempFile.toFile());

			int size = 0;
			byte[] byteBuff = new byte[1024];
			while ((size = bufferedInputStream.read(byteBuff)) != -1) {
				outputStream.write(byteBuff, 0, size);
			}

			outputStream.close();
			bufferedInputStream.close();

			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
