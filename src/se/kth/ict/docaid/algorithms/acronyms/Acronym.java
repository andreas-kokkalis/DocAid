package se.kth.ict.docaid.algorithms.acronyms;

public class Acronym {
	private String acronym;
	private String[] spelledOut;
	private boolean isSpelledOut;
	private boolean isSpelledOnFirstCheck;
	private String description;

	public Acronym(String acronym, String[] spelledOut, boolean isSpelledOut, boolean isSpelledOnFirstCheck) {
		super();
		this.acronym = acronym;
		this.spelledOut = spelledOut;
		this.isSpelledOut = isSpelledOut;
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
	}

	public Acronym (String acronym, String description) {
		this.acronym = acronym;
		this.setDescription(description);
	}
	
	public Acronym(String acronym) {
		super();
		this.acronym = acronym;
		this.isSpelledOnFirstCheck = false;
		this.isSpelledOut = false;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String[] getSpelledOut() {
		return spelledOut;
	}

	public void setSpelledOut(String[] spelledOut) {
		this.spelledOut = spelledOut;
	}

	public boolean isSpelledOut() {
		return isSpelledOut;
	}

	public void setIsSpelledOut(boolean isSpelledOut) {
		this.isSpelledOut = isSpelledOut;
	}

	public String spelloutString() {
		String st = "";

		if (spelledOut != null)
			for (String s : spelledOut)
				st = st + " " + s;
		else
			st = "not spelled out";
		return st;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {

		String objs = "";

		if (spelledOut != null)
			for (String s : spelledOut)
				objs = objs + " " + s;
		else
			objs = "not spelled out";
		if(description == null)
			return "Acronym [acronym=" + acronym + ", spelledOut=" + objs + ", isSpelledOut=" + isSpelledOut + ", isSpelledOnFirstCheck=" + isSpelledOnFirstCheck + "]";
		else
			return "Acronym [acronym=" + acronym + " spelledOut=" + description;
	}

	public boolean isSpelledOnFirstCheck() {
		return isSpelledOnFirstCheck;
	}

	public void setSpelledOnFirstCheck(boolean isSpelledOnFirstCheck) {
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
	}

}
