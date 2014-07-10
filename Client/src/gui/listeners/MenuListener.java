package gui.listeners;

import gui.DialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JMenuItem;

import controller.ClientController;
import database.beans.Candidate;
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
	public void actionPerformed(ActionEvent e) {
		List<Organisation> organisations = null;
		JMenuItem item = (JMenuItem) e.getSource();
		String action = item.getText();
		try {
			switch (action) {
			case "Add Vacancy":
				organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.ADD_VACANCY, organisations);
				controller.getView().showDialog(DialogType.ADD_VACANCY);
				break;
			case "Remove Vacancy":
				List<Vacancy> vacancies;
				vacancies = controller.getModel().getVacancies(false, null);
				controller.getView().setDisplayedVacanciesInDialog(DialogType.REMOVE_VACANCY, vacancies);
				controller.getView().showDialog(DialogType.REMOVE_VACANCY);
				break;
			case "Add Organisation":
				controller.getView().showDialog(DialogType.ADD_ORGANISATION);
				break;
			case "Remove Organisation":
				organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.REMOVE_ORGANISATION, organisations);
				controller.getView().showDialog(DialogType.REMOVE_ORGANISATION);
				break;
			case "Add Candidate":
				organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.ADD_CANDIDATE, organisations);
				controller.getView().showDialog(DialogType.ADD_CANDIDATE);
				break;
			case "Remove Candidate":
				List<Candidate> candidates;
				candidates = controller.getModel().getCandidates();
				controller.getView().setDisplayedCandidatesInDialog(DialogType.REMOVE_CANDIDATE, candidates);
				controller.getView().showDialog(DialogType.REMOVE_CANDIDATE);
				break;
			case "Add Contact":
				organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.ADD_CONTACT, organisations);
				controller.getView().showDialog(DialogType.ADD_CONTACT);
				break;
			case "Remove Contact":
				organisations = controller.getModel().getOrganisations();
				controller.getView().setDisplayedOrganisationsInDialog(DialogType.REMOVE_CONTACT, organisations);
				controller.getView().showDialog(DialogType.REMOVE_CONTACT);
				break;
			}
		} catch (RemoteException ex) {
			controller.exitApplication();
		}
	}
}
