package gui;

public enum ConfirmDialogType {
	VACANCY_REMOVE_PROFILE("Are you sure you want to remove this profile?"), REMOVE_VACANCY("Are you sure you want to remove this vacancy?"), 
	ORGANISATION_REMOVE_TOB("Are you sure you want to remove these terms of business?"), REMOVE_ORGANISATION("Are you sure you want to remove this organisation? All vacancies and " +
			"contacts will also be removed"), REMOVE_CANDIDATE("Are you sure you want to remove this candidate?"), REMOVE_CONTACT("Are you sure you want to remove this contact?"), 
	CANDIDATE_REMOVE_LINKEDIN("Are you sure you want to remove this LinkedIn profile?"), CANDIDATE_REMOVE_CV("Are you sure you want to remove this CV?"), 
	REMOVE_FROM_SHORTLIST("Are you sure you want to remove this shortlist event?"), REMOVE_TASK("Are you sure you want to remove this task?"), 
	REMOVE_SKILL("Are you sure you want to remove this skill?");
	
	private String message;
	
	private ConfirmDialogType(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
