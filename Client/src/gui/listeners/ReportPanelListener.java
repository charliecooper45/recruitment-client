package gui.listeners;

import gui.ErrorDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

import controller.ClientController;
import database.beans.EventType;
import database.beans.Report;
import database.beans.User;

public class ReportPanelListener extends ClientListener implements ActionListener {

	public ReportPanelListener(ClientController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			String text = button.getText();
			
			if (text.equals("Get Report")) {
				Report report = controller.getView().getReportPanelReport();
				
				Map<User, Map<EventType, Integer>> results = controller.getModel().getUserReport(report);
				
				if(report != null) {
					controller.getView().updateDisplayedReport(results);
				} else {
					controller.getView().showErrorDialog(ErrorDialogType.GET_REPORT_FAIL);
				}
			}
		}
	}
}
