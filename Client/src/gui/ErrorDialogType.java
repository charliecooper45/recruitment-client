package gui;

public enum ErrorDialogType {
	VACANCY_NO_PROFILE("No profile currently stored for this vacancy"), ADD_VACANCY_FAIL("Unable to add the vacancy"), REMOVE_VACANCY_FAIL("Unable to remove vacancy"), 
	ORGANISATION_NO_TOB("No terms of business currently stored for this vacancy"), ADD_ORGANISATION_FAIL("Unable to add the organisation"), REMOVE_ORGANISATION_FAIL("Unable to remove " +
			"organisation"), ADD_CANDIDATE_FAIL("Unable to add candidate"), REMOVE_CANDIDATE_FAIL("Unable to remove the candidate"), ADD_CONTACT_FAIL("Unable to add contact"), 
	REMOVE_CONTACT_FAIL("Unable to remove contact"), ADD_LINKEDIN_FAIL("Unable to add LinkedIn profile, please check the URL"),	REMOVE_LINKEDIN_FAIL("Unable to remove LinkedIn profile"), 
	CANDIDATE_NO_CV("No CV currently stored for this candidate"), ADD_TO_SHORTLIST_FAIL("Cannot add candidate(s) to shortlist"), REMOVE_FROM_SHORTLIST_FAIL("Unable to remove candidate from shortlist)"), 
	CANDIDATE_UPDATE_FAIL("Unable to update candidate"), ADD_SKILL_FAIL("Unable to add skill to candidate"), REMOVE_SKILL_FAILED("Unable to remove skill from candidate"), 
	VACANCY_UPDATE_FAIL("Unable to update vacancy"), ORGANISATION_UPDATE_FAIL("Unable to update organisation");
	
	private String errorMessage;
	
	private ErrorDialogType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getMessage() {
		return errorMessage;
	}
}
