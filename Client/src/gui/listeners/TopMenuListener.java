package gui.listeners;

import gui.PanelType;
import gui.TopMenuPanel.MenuPanel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controller.ClientController;
import database.beans.Organisation;
import database.beans.Skill;
import database.beans.User;
import database.beans.Vacancy;

public class TopMenuListener extends ClientListener {

	public TopMenuListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		List<Vacancy> vacancies = null;
		Object source = e.getSource();
		
		if(source instanceof MenuPanel) {
			List<User> users = null;
			MenuPanel panel = (MenuPanel) source;
			
			// set the selected menu panel
			controller.getView().setSelectedTopMenuPanel(panel);
			
			// update and show the correct centre panel
			PanelType type = panel.getPanelType();
			
			switch (type) {
			case ADMIN:
				users = controller.getModel().getUsers(null, false);
				controller.getView().showAdminPanel(users);
				break;
			case ORGANISATIONS:
				List<Organisation> organisations = controller.getModel().getOrganisations();
				controller.getView().showOrganisationsPanel(organisations);
				break;
			case PIPELINE:
				controller.getView().showCandidatePipeline(); 
				break;
			case SEARCH:
				List<Skill> skills = controller.getModel().getSkills();
				vacancies = controller.getModel().getVacancies(true, null);
				controller.getView().showSearchPanel(skills, vacancies);
				break;
			case VACANCIES:
				// update the vacancies panel and then show it
				vacancies = controller.getModel().getVacancies(true, null);
				users = controller.getModel().getUsers(null, true);
				controller.getView().showVacanciesPanel(vacancies, users);
				break;
			default:
				break;
			}
		}
	}
}
