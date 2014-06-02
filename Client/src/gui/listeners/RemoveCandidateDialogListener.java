package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.ErrorDialogType;
import gui.MenuDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;

/**
 * Listens for events on the remove candidate dialog. 
 * @author Charlie
 */
public class RemoveCandidateDialogListener extends ClientListener implements ActionListener {

	public RemoveCandidateDialogListener(ClientController controller) {
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
				boolean confirmed = controller.getView().showDialog(DialogType.REMOVE_CANDIDATE);
				
				if(confirmed) {
					Candidate candidate = controller.getView().getCandidateDialogCandidate(MenuDialogType.REMOVE_CANDIDATE);
					boolean deleted = controller.getModel().removeCandidate(candidate);
					
					if(deleted) {
						controller.getView().hideMenuDialog(MenuDialogType.REMOVE_CANDIDATE);
						controller.getView().showConfirmDialog(ConfirmDialogType.REMOVE_CANDIDATE);
						
						// Check if the candidate pipeline panel is displayed and then update if necessary
						//TODO NEXT: implement this
						/*
						PanelType shownPanel = controller.getView().getDisplayedPanel();
						if (shownPanel == PanelType.CANDIDATE) {
							CandidatesPanelListener listener = controller.getCandidatePipelinePanelListener();
							List<Candidate> candidates = controller.getModel().getCandidates();
							controller.getView().updateCandidatesPanel(candidates);
						}
						*/
					}
					else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_CANDIDATE_FAIL);
					}
				} 
				break;
			case "Cancel ":
				controller.getView().hideMenuDialog(MenuDialogType.REMOVE_CANDIDATE);
				break;
			}
		}
	}

}