package gui.listeners;

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
				System.err.println("Add linkedin");
			} else if(button.getText().equals("Remove LinkedIn")) {
				System.err.println("Remove linkedin");
			}
		}
	}
}
