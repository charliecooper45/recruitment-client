package gui;

public enum ErrorMessages {
	VACANCYNOPROFILE;

	public Object getMessage() {
		switch (this.toString()) {
		case "VACANCYNOPROFILE":
			return "No profile currently stored for this vacancy";
		}
		return null;
	}
}
