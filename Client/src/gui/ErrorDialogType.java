package gui;

public enum ErrorDialogType {
	VACANCY_NO_PROFILE("No profile currently stored for this vacancy"), ADD_VACANCY_FAIL("Unable to add the vacancy"), REMOVE_VACANCY_FAIL("Unable to remove vacancy");
	
	private String errorMessage;
	
	private ErrorDialogType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getMessage() {
		return errorMessage;
	}
}
