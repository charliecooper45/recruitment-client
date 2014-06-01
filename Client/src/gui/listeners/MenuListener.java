package gui.listeners;

import gui.MenuDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;

import controller.ClientController;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Listener for events on the menu in the main window.
 * @author Charlie
 */
public class MenuListener extends ClientListener implements ActionListener {
	public MenuListener(ClientController controller) {
		super(controller);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		List<Organisation> organisations = null;
		JMenuItem item = (JMenuItem) event.getSource();
		String action = item.getText();
		switch (action) {
		case "Add Vacancy":
			organisations = controller.getModel().getOrganisations();
			controller.getView().setDisplayedOrganisationsInDialog(MenuDialogType.ADD_VACANCY, organisations);
			controller.getView().showMenuDialog(MenuDialogType.ADD_VACANCY);
			break;
		case "Remove Vacancy":
			List<Vacancy> vacancies = controller.getModel().getVacancies(false, null);
			controller.getView().setDisplayedVacanciesInDialog(MenuDialogType.REMOVE_VACANCY, vacancies);
			controller.getView().showMenuDialog(MenuDialogType.REMOVE_VACANCY);
			break;
		case "Add Organisation":
			controller.getView().showMenuDialog(MenuDialogType.ADD_ORGANISATION);
			break;
		case "Remove Organisation":
			organisations = controller.getModel().getOrganisations();
			controller.getView().setDisplayedOrganisationsInDialog(MenuDialogType.REMOVE_ORGANISATION, organisations);
			controller.getView().showMenuDialog(MenuDialogType.REMOVE_ORGANISATION);
			break;
		case "Add Candidate":
			controller.getView().showMenuDialog(MenuDialogType.ADD_CANDIDATE);
			break;
		}
	}
}
