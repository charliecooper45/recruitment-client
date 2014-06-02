package gui.listeners;

import gui.ConfirmDialogType;
import gui.ErrorDialogType;
import gui.MenuDialogType;
import gui.PanelType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Organisation;
import database.beans.Vacancy;

/**
 * Listener for events on the add organisation dialog.
 * @author Charlie
 */
public class AddOrganisationDialogListener extends ClientListener implements ActionListener{
	public AddOrganisationDialogListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			
			switch (text) {
			case "Confirm":
				InputStream inputStream;
				RemoteInputStreamServer tobData = null;
				Organisation organisation = controller.getView().getOrganisationDialogOrganisation(MenuDialogType.ADD_ORGANISATION);
				
				if(organisation != null) {
					// the organisation is valid and can be added
					try {
						if (organisation.getTermsOfBusiness() != null) {
							File file = new File(organisation.getTermsOfBusiness());
							inputStream = new FileInputStream(file);
							tobData = new SimpleRemoteInputStream(inputStream);
							organisation.setTermsOfBusiness(file.getName());
						}
						//TODO NEXT: return a message to the user to say if the tob wer added
						boolean tobAdded = controller.getModel().addOrganisation(organisation, tobData);
						
						if (tobAdded) {
							controller.getView().hideMenuDialog(MenuDialogType.ADD_ORGANISATION);
							controller.getView().showConfirmDialog(ConfirmDialogType.ORGANISATION_ADDED);
							
							// check if the organisations panel is displayed and then update if necessary
							PanelType shownPanel = controller.getView().getDisplayedPanel();
							if (shownPanel == PanelType.ORGANISATIONS) {
								OrganisationsPanelListener listener = controller.getOrganisationsPanelListener();
								
								if(!listener.hasActiveSearch()) {
									// there is no active search so updated the organisations panel
									List<Organisation> organisations = controller.getModel().getOrganisations();
									controller.getView().updateOrganisationsPanel(organisations);
								}
							}
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.ADD_ORGANISATION_FAIL);
						}
					} catch (FileNotFoundException e1) {
						// TODO NEXT B: handle exception
						e1.printStackTrace();
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.ADD_ORGANISATION);
				break;
			case "..":
				File file = controller.getView().showFileChooser("Select terms of business to add.");
				if (file != null) {
					// update the view to show the new file
					controller.getView().displayFileInDialog(MenuDialogType.ADD_ORGANISATION, file);
				}
				break;
			}
		}
	}
}
