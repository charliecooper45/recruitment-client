package gui.listeners;

import gui.MenuDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JMenuItem;

import controller.ClientController;
import database.beans.Organisation;

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
		JMenuItem item = (JMenuItem) event.getSource();
		String action = item.getText();
		switch (action) {
		case "Add Vacancy":
			// get the up to date organisations list from the server
			List<Organisation> organisations = controller.getModel().getOrganisations();
			Collections.sort(organisations);
			controller.getView().setDisplayedOrganisationsInDialog(MenuDialogType.ADD_VACANCY, organisations);
			controller.getView().showMenuDialog(MenuDialogType.ADD_VACANCY);
			break;
		case "Remove Vacancy":
			System.err.println("Remove vacancy");
			break;
		}
	}
}
