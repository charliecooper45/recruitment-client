package gui;

public enum ErrorDialogType {
	VACANCY_NO_PROFILE("No profile currently stored for this vacancy"), ADD_VACANCY_FAIL("Unable to add the vacancy"), REMOVE_VACANCY_FAIL("Unable to remove vacancy"), 
	ORGANISATION_NO_TOB("No terms of business currently stored for this vacancy"), ADD_ORGANISATION_FAIL("Unable to add the organisation"), REMOVE_ORGANISATION_FAIL("Unable to remove " +
			"organisation"), ADD_CANDIDATE_FAIL("Unable to add candidate"), REMOVE_CANDIDATE_FAIL("Unable to remove the candidate");
	
	private String errorMessage;
	
	private ErrorDialogType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getMessage() {
		return errorMessage;
	}
}
