package se.kth.ict.docaid.Recommender;

import java.util.ArrayList;

/**
 * A course that is recomended. Contains the course code, and the recommendation weights.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class RecommendedCourse {
	String code;
	double acronymWeight;
	double keywordWeight;
	double keyphraseWeight;
	ArrayList<String> acronymList;
	ArrayList<String> keywordList;
	ArrayList<String> keyphraseList;

	public RecommendedCourse(String code, double acronymWeight,
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

	public RecommendedCourse(String code, double acronymWeight,
			double keywordWeight, double keyphraseWeight) {
		super();
		this.code = code;
		this.acronymWeight = acronymWeight;
		this.keywordWeight = keywordWeight;
		this.keyphraseWeight = keyphraseWeight;
	}

	@Override
	public String toString() {
		return "RecommendedCourse \t[code=" + code + ", \tacronymWeight="
				+ acronymWeight + ", \tkeywordWeight=" + keywordWeight
				+ ", \tkeyphraseWeight=" + keyphraseWeight + ", \tacronymList="
				+ acronymList + ", keywordList=" + keywordList
				+ ", \tkeyphraseList=" + keyphraseList + "]";
	}

}
