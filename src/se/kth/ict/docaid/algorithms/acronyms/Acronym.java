package se.kth.ict.docaid.algorithms.acronyms;

public class Acronym {
	String acronym;
	String[] spelledOut;
	boolean isSpelledOut;
	boolean isSpelledOnFirstCheck;

	public Acronym(String acronym, String[] spelledOut, boolean isSpelledOut,
			boolean isSpelledOnFirstCheck) {
		super();
		this.acronym = acronym;
		this.spelledOut = spelledOut;
		this.isSpelledOut = isSpelledOut;
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
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

	@Override
	public String toString() {
		
		String objs = "";
		
		for (String s :spelledOut) objs = objs +" "+s;
		
		return "Acronym [acronym=" + acronym + ", spelledOut=" + objs
				+ ", isSpelledOut=" + isSpelledOut + ", isSpelledOnFirstCheck="
				+ isSpelledOnFirstCheck + "]";
	}

	public boolean isSpelledOnFirstCheck() {
		return isSpelledOnFirstCheck;
	}

	public void setSpelledOnFirstCheck(boolean isSpelledOnFirstCheck) {
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
	}

}
