package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;
import gui.PanelType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Organisation;
import database.beans.User;
import database.beans.Vacancy;

/**
 * Listens for events on the remove organisation dialog. 
 * @author Charlie
 */
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

			try {
				switch (text) {
				case "Confirm":
					boolean confirmed = controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_ORGANISATION);

					if (confirmed) {
						Organisation organisation = controller.getView().getOrganisationDialogOrganisation(DialogType.REMOVE_ORGANISATION);
						boolean deleted = controller.getModel().removeOrganisation(organisation);

						if (deleted) {
							controller.getView().hideDialog(DialogType.REMOVE_ORGANISATION);
							controller.getView().showMessageDialog(MessageDialogType.ORGANISATION_REMOVED);

							// check if the organisations or vacancies panel is displayed and then update if necessary
							//TODO NEXT: update the contacts panel
							//TODO NEXT: update the vacancies panel
							PanelType shownPanel = controller.getView().getDisplayedPanel();
							if (shownPanel == PanelType.ORGANISATIONS) {
								List<Organisation> organisations = controller.getModel().getOrganisations();
								controller.getView().updateOrganisationsPanel(organisations);
							} else if (shownPanel == PanelType.VACANCIES) {
								User user = controller.getVacanciesPanelListener().getSelectedUser();
								boolean open = controller.getVacanciesPanelListener().getDisplayOpenVacancies();
								List<Vacancy> vacancies;
								vacancies = controller.getModel().getVacancies(open, user);
								controller.getView().updateVacanciesPanel(vacancies);
							}
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.REMOVE_ORGANISATION_FAIL);
						}
					}
					break;
				case "Cancel ":
					controller.getView().hideDialog(DialogType.REMOVE_ORGANISATION);
					break;
				}
			} catch (RemoteException e1) {
				controller.exitApplication();
			}
		}
	}
}
