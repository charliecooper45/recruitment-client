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
import database.beans.Organisation;

public class RemoveOrganisationDialogListener extends ClientListener implements ActionListener {
	public RemoveOrganisationDialogListener(ClientController controller) {
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
				boolean confirmed = controller.getView().showDialog(DialogType.REMOVE_ORGANISATION);
				
				if(confirmed) {
					Organisation organisation = controller.getView().getOrganisationDialogVacancy(MenuDialogType.REMOVE_ORGANISATION);
					boolean deleted = controller.getModel().removeOrganisation(organisation);
					
					if (deleted) {
						controller.getView().hideMenuDialog(MenuDialogType.REMOVE_ORGANISATION);
						controller.getView().showConfirmDialog(ConfirmDialogType.ORGANISATION_REMOVED);
						
						// check if the organisations panel is displayed and then update if necessary
						PanelType shownPanel = controller.getView().getDisplayedPanel();
						if (shownPanel == PanelType.ORGANISATIONS) {
							OrganisationsPanelListener listener = controller.getOrganisationsPanelListener();
							List<Organisation> organisations = controller.getModel().getOrganisations();
							controller.getView().updateOrganisationsPanel(organisations);
						}
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_ORGANISATION_FAIL);
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.REMOVE_ORGANISATION);
				break;
			}
		}
	}
}
