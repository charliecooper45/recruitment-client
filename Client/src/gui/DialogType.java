package gui;

public enum DialogType {
	VACANCY_ADD_PROFILE(""), VACANCY_REMOVE_PROFILE("Are you sure you want to remove this profile?"), VACANCY_CHANGE_STATUS_OPEN("Are you sure you want to open this vacancy?"),
	VACANCY_CHANGE_STATUS_CLOSE("Are you sure you want to close this vacancy?"), REMOVE_VACANCY("Are you sure you want to remove this vacancy?"), 
	ORGANISATION_REMOVE_TOB("Are you sure you want to remove these terms of business?"), REMOVE_ORGANISATION("Are you sure you want to remove this organisation? All vacancies will " +
			"also be removed");
	
	private String message;
	
	private DialogType(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
