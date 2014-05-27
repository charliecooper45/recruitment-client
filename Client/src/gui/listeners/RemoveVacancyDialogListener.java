package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MenuDialogType;
import gui.PanelType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Vacancy;

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

			switch (text) {
			case "Confirm":
				boolean confirmed = controller.getView().showDialog(DialogType.REMOVE_VACANCY);
				if (confirmed) {
					Vacancy vacancy = controller.getView().getVacancyDialogVacancy(MenuDialogType.REMOVE_VACANCY);
					boolean deleted = controller.getModel().removeVacancy(vacancy);

					if (deleted) {
						controller.getView().hideMenuDialog(MenuDialogType.REMOVE_VACANCY);
						controller.getView().showConfirmDialog(ConfirmDialogType.VACANCY_REMOVED);
						// check if the vacancies panel is displayed and then update if necessary
						PanelType shownPanel = controller.getView().getDisplayedPanel();
						if (shownPanel == PanelType.VACANCIES) {
							VacanciesPanelListener listener = controller.getVacanciesPanelListener();
							List<Vacancy> vacancies = controller.getModel().getVacancies(listener.getDisplayOpenVacancies(), listener.getSelectedUser());
							controller.getView().updateVacanciesPanel(vacancies);
						}
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_VACANCY_FAIL);
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.REMOVE_VACANCY);
				break;
			}
		}
	}
}
