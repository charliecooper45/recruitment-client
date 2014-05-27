package gui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.beans.Contact;
import database.beans.Organisation;
import database.beans.Vacancy;

public abstract class RecruitmentDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	protected JPanel panel;
	protected GridBagConstraints gbc;
	
	public RecruitmentDialog(JFrame frame, String title) {
		super(frame, title);

		setSize(400, 500);
		setLocationRelativeTo(frame);
		setModal(true);
		setResizable(false);
		
		panel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
	}
	
	public void setDisplayedFile(File file) {
		// by default this does nothing, can be overriden in appropriate dialogs
	}

	public void setDisplayedOrganisations(List<Organisation> organisations) {
		// by default this does nothing, can be overriden in appropriate dialogs
	}
	
	public void setDisplayedContacts(List<Contact> contacts) {
		// by default this does nothing, can be overriden in appropriate dialogs
	}
	
	public void setDisplayedVacancies(List<Vacancy> vacancies) {
		// by default this does nothing, can be overriden in appropriate dialogs
	}
	
	public abstract void setActionListener(ActionListener actionListener);
}
