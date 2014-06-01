package gui.listeners;

import gui.PanelType;
import gui.TopMenuPanel.MenuPanel;

import java.awt.event.MouseEvent;
import java.util.List;

import controller.ClientController;
import database.beans.Organisation;
import database.beans.User;
import database.beans.Vacancy;

public class TopMenuListener extends ClientListener {

	public TopMenuListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		
		if(source instanceof MenuPanel) {
			MenuPanel panel = (MenuPanel) source;
			
			// set the selected menu panel
			controller.getView().setSelectedTopMenuPanel(panel);
			
			// update and show the correct centre panel
			PanelType type = panel.getPanelType();
			
			switch (type) {
			case ADMIN:
				controller.getView().showAdminPanel();
				break;
			case ORGANISATIONS:
				List<Organisation> organisations = controller.getModel().getOrganisations();
				controller.getView().showOrganisationsPanel(organisations);
				break;
			case PIPELINE:
				controller.getView().showCandidatePipeline(); 
				break;
			case SEARCH:
				controller.getView().showSearchPanel();
				break;
			case VACANCIES:
				// update the vacancies panel and then show it
				List<Vacancy> vacancies = controller.getModel().getVacancies(true, null);
				List<User> users = controller.getModel().getUsers(null, true);
				controller.getView().showVacanciesPanel(vacancies, users);
				break;
			default:
				break;
			}
		}
	}
}
