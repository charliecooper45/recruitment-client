package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Organisation;

/**
 * Listener for events on the organisation panel.
 * @author Charlie
 */
public class OrganisationPanelListener extends ClientListener implements ActionListener{

	public OrganisationPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			if (button.getText().equals("Add TOB")) {
				File file = controller.getView().showFileChooser("Select terms of business to add.");
				if(file != null) {
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(file);
						RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
						
						Organisation organisation = controller.getView().getDisplayedOrganisation();
						String oldFileName = organisation.getTermsOfBusiness();
						organisation.setTermsOfBusiness(file.getName());
						
						boolean profileAdded = controller.getModel().addOrganisationTob(organisation, remoteFileData, oldFileName);

						if (profileAdded) {
							String organisationTob = organisation.getTermsOfBusiness();
							RemoteInputStream remoteOrganisationTobData = controller.getModel().getOrganisationTob(organisationTob);
							InputStream fileData = RemoteInputStreamClient.wrap(remoteOrganisationTobData);
							Path tempFile = controller.storeTempFile(fileData, organisationTob);
							controller.getView().showOrganisationPanel(organisation, tempFile);
						}
					} catch (FileNotFoundException e) {
						// TODO handle this exception
						e.printStackTrace();
					} catch (IOException e) {
						// TODO handle this exception
						e.printStackTrace();
					}
				}
			} else if (button.getText().equals("Remove TOB")) {
				boolean confirm = controller.getView().showDialog(DialogType.ORGANISATION_REMOVE_TOB);
				if (confirm) {
					Organisation organisation = controller.getView().getDisplayedOrganisation();
					if(organisation.getTermsOfBusiness() != null) {
						if(controller.getModel().removeOrganisationTob(organisation)) {
							organisation.setTermsOfBusiness(null);
							controller.getView().showOrganisationPanel(organisation, null);
						}
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.ORGANISATION_NO_TOB);
					}
				}
			}
		}
	}
}
