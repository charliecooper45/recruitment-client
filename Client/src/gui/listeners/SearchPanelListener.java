package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.Candidate;
import database.beans.Search;

/**
 * Listener for events on the search panel.
 * @author Charlie
 */
public class SearchPanelListener extends ClientListener implements ActionListener {
	public SearchPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			
			String name = button.getName();
			switch (name) {
			case "AddSkillButton":
				controller.getView().addSkillToSearch(); 
				break;
			case "RemoveSkillButton":
				controller.getView().removeSkillFromSearch();
			case "SearchButton":
				Search search = controller.getView().getSearchPanelSearch();
				List<Candidate> candidates = controller.getModel().searchCandidates(search);
				
				if(candidates != null) 
					controller.getView().updateSearchPanel(candidates);
				break;
			}
		}
	}
}
