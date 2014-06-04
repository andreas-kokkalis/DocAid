package se.kth.ict.docaid.Recommender;

import java.util.ArrayList;

public class RecommendedTutor {

	String code;
	double acronymWeight;
	double keywordWeight;
	double keyphraseWeight;
	ArrayList<String> acronymList;
	ArrayList<String> keywordList;
	ArrayList<String> keyphraseList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAcronymWeight() {
		return acronymWeight;
	}

	public void setAcronymWeight(double acronymWeight) {
		this.acronymWeight = acronymWeight;
	}

	public double getKeywordWeight() {
		return keywordWeight;
	}

	public void setKeywordWeight(double keywordWeight) {
		this.keywordWeight = keywordWeight;
	}

	public double getKeyphraseWeight() {
		return keyphraseWeight;
	}

	public void setKeyphraseWeight(double keyphraseWeight) {
		this.keyphraseWeight = keyphraseWeight;
	}

	public ArrayList<String> getAcronymList() {
		return acronymList;
	}

	public void setAcronymList(ArrayList<String> acronymList) {
		this.acronymList = acronymList;
	}

	public ArrayList<String> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(ArrayList<String> keywordList) {
		this.keywordList = keywordList;
	}

	public ArrayList<String> getKeyphraseList() {
		return keyphraseList;
	}

	public void setKeyphraseList(ArrayList<String> keyphraseList) {
		this.keyphraseList = keyphraseList;
	}

	public RecommendedTutor(String code, double acronymWeight,
			ArrayList<String> acronymList, double keywordWeight,
			ArrayList<String> keywordList, double keyphraseWeight,
			ArrayList<String> keyphraseList) {
		super();
		this.code = code;
		this.acronymWeight = acronymWeight;
		this.keywordWeight = keywordWeight;
		this.keyphraseWeight = keyphraseWeight;
		this.acronymList = acronymList;
		this.keywordList = keywordList;
		this.keyphraseList = keyphraseList;
	}

	@Override
	public String toString() {
		return "RecommendedTutor [code=" + code + ", acronymWeight="
				+ acronymWeight + ", keywordWeight=" + keywordWeight
				+ ", keyphraseWeight=" + keyphraseWeight + ", acronymList="
				+ acronymList + ", keywordList=" + keywordList
				+ ", keyphraseList=" + keyphraseList + "]";
	}
	
	

}
