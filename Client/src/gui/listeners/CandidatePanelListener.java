package gui.listeners;

import gui.ConfirmDialogType;
import gui.DialogType;
import gui.MessageDialogType;
import gui.ErrorDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.ClientController;

/**
 * Listener for events on the candidate panel.
 * @author Charlie
 */
public class CandidatePanelListener extends ClientListener implements ActionListener {
	public CandidatePanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			if (button.getText().equals("Add LinkedIn   ")) {
				controller.getView().showDialog(DialogType.CANDIDATE_ADD_LINKEDIN);
			} else if(button.getText().equals("Remove LinkedIn")) {
				boolean remove = controller.getView().showConfirmDialog(ConfirmDialogType.CANDIDATE_REMOVE_LINKEDIN);
				
				if(remove) {
					boolean removed = controller.getModel().removeLinkedInProfile(controller.getView().getCandidatePanelCandidate());
					if(removed) {
						controller.getView().showMessageDialog(MessageDialogType.LINKEDIN_PROFILE_REMOVED);
						controller.getView().updateCandidateLinkedInProfile(null);
					} else {
						controller.getView().showErrorDialog(ErrorDialogType.REMOVE_LINKEDIN_FAIL);
					}
				}
			}
		}
	}
}
