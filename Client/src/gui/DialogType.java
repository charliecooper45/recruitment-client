package gui;

public enum DialogType {
	VACANCY_ADD_PROFILE(""), VACANCY_REMOVE_PROFILE("Are you sure you want to remove this profile?"), VACANCY_CHANGE_STATUS_OPEN("Are you sure you want to open this vacancy?"),
	VACANCY_CHANGE_STATUS_CLOSE("Are you sure you want to close this vacancy?"), REMOVE_VACANCY("Are you sure you want to remove this vacancy?");
	
	private String message;
	
	private DialogType(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
