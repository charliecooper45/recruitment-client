package gui;

public enum MessageDialogType {
	VACANCY_ADDED("Vacancy added"), VACANCY_REMOVED("Vacancy removed"), ORGANISATION_ADDED("Organisation added"), ORGANISATION_REMOVED("Organisation removed"), 
	CANDIDATE_ADDED("Candidate added"), REMOVE_CANDIDATE("Candidate removed"), CONTACT_ADDED("Contact added"), CONTACT_REMOVED("Contact removed"), 
	LINKEDIN_PROFILE_ADDED("LinkedIn profile added"), LINKEDIN_PROFILE_REMOVED("LinkedIn profile removed");
	
	private String confirmMessage;
	
	private MessageDialogType(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

	public Object getMessage() {
		return confirmMessage;
	}
}
