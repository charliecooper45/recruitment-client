package gui;

public enum ConfirmDialogType {
	VACANCY_ADDED("Vacancy added"), VACANCY_REMOVED("Vacancy removed"), ORGANISATION_ADDED("Organisation added");
	
	private String confirmMessage;
	
	private ConfirmDialogType(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

	public Object getMessage() {
		return confirmMessage;
	}
}
