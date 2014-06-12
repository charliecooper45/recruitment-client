package gui.listeners;

import gui.ConfirmDialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Event;
import database.beans.Vacancy;

/**
 * Listener for events on the vacancy panel.
 * @author Charlie
 */
public class VacancyPanelListener extends ClientListener implements ActionListener, KeyListener {
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
				boolean confirm = controller.getView().showConfirmDialog(ConfirmDialogType.VACANCY_REMOVE_PROFILE);
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
				if (controller.getView().showConfirmDialog(ConfirmDialogType.VACANCY_CHANGE_STATUS_CLOSE)) {
					// close the vacancy
					controller.getModel().changeVacancyStatus(displayedVacancy);
				}
			} else if (status == false && comboBoxValue.equals("Open")) {
				if (controller.getView().showConfirmDialog(ConfirmDialogType.VACANCY_CHANGE_STATUS_OPEN)) {
					// open the vacancy
					controller.getModel().changeVacancyStatus(displayedVacancy);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Object source = event.getSource();
		
		if(source instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) source;
			
			int index = tabbedPane.getSelectedIndex();
			
			if(index == 1) {
				// update the shortlist from the server
				List<Event> shortlistEvents = controller.getModel().getShortlist(controller.getView().getDisplayedVacancy().getVacancyId());
				
				// update the view to display the shortlist
				controller.getView().updateDisplayedShortlist(shortlistEvents);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DELETE) {
			// the user wishes to remove the shortlist event
			Event shortlistEvent = controller.getView().getSelectedShortlistEvent();
			
			boolean delete = controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_FROM_SHORTLIST);
			
			if(delete) {
				// send a message to the server to delete the shortlist event
				boolean deleted = controller.getModel().removeCandidateFromShortlist(shortlistEvent.getCandidate().getId(), shortlistEvent.getVacancyId());
				
				if(deleted) {
					controller.getView().showMessageDialog(MessageDialogType.REMOVED_FROM_SHORTLIST);
					
					// update the view to display the updated shortlist
					List<Event> shortlistEvents = controller.getModel().getShortlist(controller.getView().getDisplayedVacancy().getVacancyId());
					controller.getView().updateDisplayedShortlist(shortlistEvents);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.REMOVE_FROM_SHORTLIST_FAIL);
				}
			}
		}
	}

	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
}
