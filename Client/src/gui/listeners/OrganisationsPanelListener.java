package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Organisation;

/**
 * Listener for events on the organisations panel.
 * @author Charlie
 */
public class OrganisationsPanelListener extends ClientListener implements ActionListener {
	public OrganisationsPanelListener(ClientController controller) {
		super(controller);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			System.err.println("Show an organisation here!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source instanceof JButton) {
			List<Organisation> organisations = null;
			JButton button = (JButton) source;
			String text = button.getText();
			
			switch (text) {
			case "Search":
				List<Organisation> matchingOrganisations = new ArrayList<>();
				
				// do a search
				String searchTerm = controller.getView().getOrganisationSearchTerm();
				Pattern pattern = Pattern.compile("^" + searchTerm.toUpperCase() + ".*");
				
				organisations = controller.getModel().getOrganisations();
				
				for(Organisation organisation : organisations) {
					String organisationName = organisation.getOrganisationName().toUpperCase();
					Matcher matcher = pattern.matcher(organisationName);
					if(matcher.find()) {
						matchingOrganisations.add(organisation);
					}
				}
				
				controller.getView().updateOrganisationsPanel(matchingOrganisations);
				break;
			case "Show All":
				System.err.println("here");
				organisations = controller.getModel().getOrganisations();
				controller.getView().updateOrganisationsPanel(organisations);
				controller.getView().removeOrganisationSearchTerm();
				break;
			}
		}
	}
}
