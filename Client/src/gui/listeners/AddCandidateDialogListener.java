package gui.listeners;

import gui.DialogType;
import gui.ErrorDialogType;
import gui.MessageDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;

import javax.swing.JButton;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.ClientController;
import database.beans.Candidate;

/**
 * Listener for events on the add candidate dialog.
 * @author Charlie
 */
public class AddCandidateDialogListener extends ClientListener implements ActionListener {
	public AddCandidateDialogListener(ClientController controller) {
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
				RemoteInputStreamServer cvData = null;
				Candidate candidate = controller.getView().getCandidateDialogCandidate(DialogType.ADD_CANDIDATE);
				if (candidate != null) {
					// the candidate is valid and can be added
					try {
						if (candidate.getCV() != null) {
							File file = new File(candidate.getCV());
							inputStream = new FileInputStream(file);
							cvData = new SimpleRemoteInputStream(inputStream);
							candidate.setCV(file.getName());
						}
						boolean candidateAdded = controller.getModel().addCandidate(candidate, cvData);

						if (candidateAdded) {
							controller.getView().hideDialog(DialogType.ADD_CANDIDATE);
							controller.getView().showMessageDialog(MessageDialogType.CANDIDATE_ADDED);
							
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
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.ADD_CANDIDATE_FAIL);
						}
					} catch (FileNotFoundException e1) {
						// TODO NEXT B: handle exception
						e1.printStackTrace();
					} catch (RemoteException e1) {
						controller.exitApplication();
					}
				}
				break;
			case "Cancel ":
				controller.getView().hideDialog(DialogType.ADD_CANDIDATE);
				break;
			case "..":
				File file = controller.getView().showFileChooser("Select CV to add.");
				if (file != null) {
					// update the view to show the new file
					controller.getView().displayFileInDialog(DialogType.ADD_CANDIDATE, file);
				}
				break;
			}
		}
	}
}
