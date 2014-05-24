package gui;

public enum ErrorDialogType {
	VACANCY_NO_PROFILE("No profile currently stored for this vacancy");
	
	private String errorMessage;
	
	private ErrorDialogType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getMessage() {
		return errorMessage;
	}
}
