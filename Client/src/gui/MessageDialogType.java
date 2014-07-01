package gui;

public enum MessageDialogType {
	VACANCY_ADDED("Vacancy added"), VACANCY_REMOVED("Vacancy removed"), ORGANISATION_ADDED("Organisation added"), ORGANISATION_REMOVED("Organisation removed"), 
	CANDIDATE_ADDED("Candidate added"), REMOVE_CANDIDATE("Candidate removed"), CONTACT_ADDED("Contact added"), CONTACT_REMOVED("Contact removed"), 
	LINKEDIN_PROFILE_ADDED("LinkedIn profile added"), LINKEDIN_PROFILE_REMOVED("LinkedIn profile removed"), ADD_TO_SHORTLIST("Candidate(s) added to shortlist"), 
	REMOVED_FROM_SHORTLIST("Candidate removed from shortlist"), CANDIDATE_UPDATED("Candidate updated"), CANDIDATE_SKILL_ADDED("Skill added to candidate"), 
	CANDIDATE_SKILL_REMOVED("Skill removed from candidate"), VACANCY_UPDATED("Vacancy updated"), ORGANISATION_UPDATED("Organisation updated"), EVENT_ADDED("Event added"), 
	EVENT_REMOVED("Event removed"), CANDIDATE_NOTES_SAVED("Candidate notes saved"), USER_ADDED("User added"), USER_REMOVED("User removed"), USER_UPDATED("User updated"), 
	SKILL_ADDED("Skill added"), SKILL_REMOVED("Skill removed");
	
	private String confirmMessage;
	
	private MessageDialogType(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

	public Object getMessage() {
		return confirmMessage;
	}
}
