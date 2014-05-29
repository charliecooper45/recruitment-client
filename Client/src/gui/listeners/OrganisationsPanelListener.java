package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

import controller.ClientController;
import database.beans.Organisation;

/**
 * Listener for events on the organisations panel.
 * @author Charlie
 */
public class OrganisationsPanelListener extends ClientListener implements ActionListener {
	private boolean activeSearch = false;
	
	public OrganisationsPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			List<Organisation> organisations = null;
			JButton button = (JButton) source;
			String text = button.getText();

			switch (text) {
			case "Search":
				activeSearch = true;
				List<Organisation> matchingOrganisations = new ArrayList<>();

				// do a search
				String searchTerm = controller.getView().getOrganisationSearchTerm();
				Pattern pattern = Pattern.compile("^" + searchTerm.toUpperCase() + ".*");

				organisations = controller.getModel().getOrganisations();

				for (Organisation organisation : organisations) {
					String organisationName = organisation.getOrganisationName().toUpperCase();
					Matcher matcher = pattern.matcher(organisationName);
					if (matcher.find()) {
						matchingOrganisations.add(organisation);
					}
				}

				controller.getView().updateOrganisationsPanel(matchingOrganisations);
				break;
			case "Show All":
				activeSearch = false;
				organisations = controller.getModel().getOrganisations();
				controller.getView().updateOrganisationsPanel(organisations);
				controller.getView().removeOrganisationSearchTerm();
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			// retrieve the selected organisaton so it`s values can be updated from the server before the user sees it
			Organisation selectedOrganisation = controller.getView().getSelectedOrganisation();
			Organisation updatedOrganisation = controller.getModel().getOrganisation(selectedOrganisation.getOrganisationId());
			Path tempFile = null;

			// get the organisation tob
			try {
				String termsOfBusiness = updatedOrganisation.getTermsOfBusiness();
				if (termsOfBusiness != null) {
					RemoteInputStream remoteFileData = controller.getModel().getOrganisationTob(termsOfBusiness);
					InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
					tempFile = controller.storeTempFile(fileData, termsOfBusiness);
				}
			} catch (IOException e1) {
				// TODO NEXT B: Possible display an error message here
				e1.printStackTrace();
			}

			controller.getView().showOrganisationPanel(updatedOrganisation, tempFile);
		}
	}
	
	public boolean hasActiveSearch() {
		return activeSearch;
	}
}
