package gui.listeners;

import gui.ErrorDialogType;
import gui.MessageDialogType;
import gui.PanelType;
import gui.DialogType;
import gui.ConfirmDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Vacancy;

/**
 * Listens for events on the remove vacancy dialog. 
 * @author Charlie
 */
public class RemoveVacancyDialogListener extends ClientListener implements ActionListener {
	public RemoveVacancyDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();

			try {
				switch (text) {
				case "Confirm":
					boolean confirmed = controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_VACANCY);
					if (confirmed) {
						Vacancy vacancy = controller.getView().getVacancyDialogVacancy(DialogType.REMOVE_VACANCY);
						boolean deleted = controller.getModel().removeVacancy(vacancy);

						if (deleted) {
							controller.getView().hideDialog(DialogType.REMOVE_VACANCY);
							controller.getView().showMessageDialog(MessageDialogType.VACANCY_REMOVED);
							// check if the vacancies panel is displayed and then update if necessary
							PanelType shownPanel = controller.getView().getDisplayedPanel();
							if (shownPanel == PanelType.VACANCIES) {
								VacanciesPanelListener listener = controller.getVacanciesPanelListener();
								List<Vacancy> vacancies;
								vacancies = controller.getModel().getVacancies(listener.getDisplayOpenVacancies(), listener.getSelectedUser());
								controller.getView().updateVacanciesPanel(vacancies);
							}
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.REMOVE_VACANCY_FAIL);
						}
					}
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.REMOVE_VACANCY);
					break;
				}
			} catch (RemoteException e1) {
				controller.exitApplication();
			}
		}
	}
}
