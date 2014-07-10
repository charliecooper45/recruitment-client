package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents a request for a report to be run on the recruitment database.
 * @author Charlie
 */
public class Report implements Serializable {
	private static final long serialVersionUID = 4498045330036970956L;
	private ReportType reportType;
	private Date fromDate;
	private Date toDate;

	public Report(ReportType reportType, Date fromDate, Date toDate) {
		this.reportType = reportType;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	public ReportType getReportType() {
		return reportType;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
}
