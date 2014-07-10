package gui.listeners;

import gui.ErrorDialogType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;

import controller.ClientController;
import database.beans.EventType;
import database.beans.Organisation;
import database.beans.Report;
import database.beans.ReportType;
import database.beans.User;
import database.beans.Vacancy;

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

				if (report != null) {
					if (report.getReportType() == ReportType.CONSULTANT) {
						Map<User, Map<EventType, Integer>> results = controller.getModel().getUserReport(report);

						if (results != null) {
							controller.getView().updateDisplayedUserReport(results);
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.GET_REPORT_FAIL);
						}
					} else if (report.getReportType() == ReportType.VACANCY) {
						Map<Vacancy, Map<EventType, Integer>> results = controller.getModel().getVacancyReport(report);
						
						if (results != null) {
							controller.getView().updateDisplayedVacancyReport(results);
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.GET_REPORT_FAIL);
						}
					} else if (report.getReportType() == ReportType.ORGANISATION) {
						Map<Organisation, Map<Boolean, Integer>> results = controller.getModel().getOrganisationReport(report);
						
						if (results != null) {
							controller.getView().updateDisplayedOrganisationReport(results);
						} else {
							controller.getView().showErrorDialog(ErrorDialogType.GET_REPORT_FAIL);
						}
					}
				}
			}
		} else if (source instanceof JComboBox<?>) {
			JComboBox<?> cmbBox = (JComboBox<?>) source;

			ReportType reportType = (ReportType) cmbBox.getSelectedItem();
			controller.getView().changeDisplayedReportTable(reportType);
		}
	}
}
