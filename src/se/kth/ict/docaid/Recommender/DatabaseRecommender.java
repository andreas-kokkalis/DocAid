package se.kth.ict.docaid.Recommender;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.database.DatabaseConnection;

public class DatabaseRecommender {

	static Connection connection = null;

	static DatabaseRecommender currentInstance = null;

	private DatabaseRecommender() {
		connection = new DatabaseConnection().getConnection();
	}

	public static DatabaseRecommender getInstance() {
		if (currentInstance == null)
			currentInstance = new DatabaseRecommender();
		return currentInstance;
	}

	public HashMap<RecommendedCourse, Double> getRecommendations(
			ArrayList<Acronym> aL, ArrayList<Keyword> kL,
			ArrayList<Keyphrase> kpL) {
		HashMap<RecommendedCourse, Double> courses = new HashMap<RecommendedCourse, Double>();
		if (aL.size()>0 && kL.size()>0 && kpL.size()>0){
		String acronymString = "'{";
		String keywordString = "'{";
		String keyphraseString = "'{";

		
		for (Acronym acc : aL)
			acronymString = acronymString + acc.getAcronym() + " , ";

		acronymString = acronymString
				.subSequence(0, acronymString.length() - 2) + "}'";

		//System.out.println(acronymString);

		for (Keyword k : kL)
			keywordString = keywordString + k.getStem() + " , ";

		keywordString = keywordString
				.subSequence(0, keywordString.length() - 2) + "}'";
		
		//System.out.println(keywordString);

		for (Keyphrase k : kpL)
			keyphraseString = keyphraseString + k.getPhrase() + " , ";

		keyphraseString = keyphraseString.subSequence(0,
				keyphraseString.length() - 2)
				+ "}'";
		
		//System.out.println(keyphraseString);
		String query = " select * from suggestcoursesapproximate(" + acronymString+" , " + keywordString +" , " +keyphraseString+" ) order by similarity_acronym + similarity_keywords+similarity_keyphrases desc";
		//System.out.println(query);

		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next())
				{
				String code = results.getString(1);
				double acronym_weight = results.getDouble(2);
				double keyword_weight = results.getDouble(3);
				double keyphrase_weight = results.getDouble(4);
				
				RecommendedCourse rC = new RecommendedCourse(code, acronym_weight, keyword_weight, keyphrase_weight);
				
				System.out.println(rC.toString());
				
				courses.put(rC, acronym_weight+keyword_weight+keyphrase_weight);
				
				}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		}
		else System.out.println("NO PARTIAL RESULTS ALLOWED");
		return courses;
	}

	public HashMap<RecommendedCourse, Double> getCourseRecommendation(
			LinkedList<String> courseCodes, 
			LinkedList<Acronym> aL, ArrayList<Keyword> kL,
			ArrayList<Keyphrase> kpL, int limit) {
		HashMap<RecommendedCourse, Double> courses = new HashMap<RecommendedCourse, Double>();
		String courseString ="'{";
		String acronymString = "'{";
		String keywordString = "'{";
		String keyphraseString = "'{";


		if (courseCodes.size()==0) courseString="'{}'";else{
		for (String s  : courseCodes)
			courseString = courseString +s + " , ";

		courseString = courseString
				.subSequence(0, courseString.length() - 2) + "}'";}

		
		if (aL.size()==0) acronymString="'{}'";else{
		for (Acronym acc : aL)
			acronymString = acronymString + acc.getAcronym() + " , ";

		acronymString = acronymString
				.subSequence(0, acronymString.length() - 2) + "}'";}

		//System.out.println(acronymString);

		if (kL.size()==0) keywordString="'{}'"; else
		{for (Keyword k : kL)
			keywordString = keywordString + k.getStem() + " , ";

		keywordString = keywordString
				.subSequence(0, keywordString.length() - 2) + "}'";
		}
		
		//System.out.println(keywordString);

		if (kpL.size()==0) keyphraseString="'{}'"; else{
		for (Keyphrase k : kpL)
			keyphraseString = keyphraseString + k.getPhrase() + " , ";

		keyphraseString = keyphraseString.subSequence(0,
				keyphraseString.length() - 2)
				+ "}'";
		}
		
		//System.out.println(keyphraseString);
		String query = " select * from suggestcoursesfinal(" +courseString +" , "+ acronymString+" , " + keywordString +" , " +keyphraseString+" ) order by total_weight desc limit "+limit;
		System.out.println(query);

		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next())
				{
				System.out.println();
				String code = results.getString(1);
				double acronym_weight = results.getDouble(2);
				Object SQLsched = results.getObject(3);
				ArrayList<String> acronymList = new ArrayList<>(); 
				for (String s: SQLsched.toString().split(",")) acronymList.add(s);

				double keyword_weight = results.getDouble(4);
				
				SQLsched = results.getObject(5); 
				ArrayList<String> keywordList = new ArrayList<>();
				
				for (String s: SQLsched.toString().split(",")) keywordList.add(s);
				
				double keyphrase_weight = results.getDouble(6);
				
				SQLsched = results.getObject(7); 
				ArrayList<String> keyphraseList = new ArrayList<>();
				
				for (String s: SQLsched.toString().split(",")) keyphraseList.add(s);

				
				RecommendedCourse rC = new RecommendedCourse(code, acronym_weight, acronymList,
						keyword_weight, keywordList, keyphrase_weight, keyphraseList);
				
				System.out.println(rC.toString());
				
				courses.put(rC, acronym_weight+keyword_weight+keyphrase_weight);
				
				}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return courses;
	}
	

	public HashMap<RecommendedTutor, Double> getTutorRecommendation(
			LinkedList<String> courseCodes, 
			LinkedList<Acronym> aL, ArrayList<Keyword> kL,
			ArrayList<Keyphrase> kpL, int limit ) {
		HashMap<RecommendedTutor, Double> tutors = new HashMap<RecommendedTutor, Double>();
		String courseString ="'{";
		String acronymString = "'{";
		String keywordString = "'{";
		String keyphraseString = "'{";


		if (courseCodes.size()==0) courseString="'{}'";else{
		for (String s  : courseCodes)
			courseString = courseString +s + " , ";

		courseString = courseString
				.subSequence(0, courseString.length() - 2) + "}'";}

		
		if (aL.size()==0) acronymString="'{}'";else{
		for (Acronym acc : aL)
			acronymString = acronymString + acc.getAcronym() + " , ";

		acronymString = acronymString
				.subSequence(0, acronymString.length() - 2) + "}'";}

		//System.out.println(acronymString);

		if (kL.size()==0) keywordString="'{}'"; else
		{for (Keyword k : kL)
			keywordString = keywordString + k.getStem() + " , ";

		keywordString = keywordString
				.subSequence(0, keywordString.length() - 2) + "}'";
		}
		
		//System.out.println(keywordString);

		if (kpL.size()==0) keyphraseString="'{}'"; else{
		for (Keyphrase k : kpL)
			keyphraseString = keyphraseString + k.getPhrase() + " , ";

		keyphraseString = keyphraseString.subSequence(0,
				keyphraseString.length() - 2)
				+ "}'";
		}
		
		//System.out.println(keyphraseString);
		String query = " select * from suggesttutorfinal(" +courseString +" , "+ acronymString+" , " + keywordString +" , " +keyphraseString+" ) order by total_weight desc limit "+limit;
		System.out.println(query);

		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next())
				{
				System.out.println();
				String code = results.getString(1);
				double acronym_weight = results.getDouble(2);
				Object SQLsched = results.getObject(3);
				ArrayList<String> acronymList = new ArrayList<>(); 
				for (String s: SQLsched.toString().split(",")) acronymList.add(s);

				double keyword_weight = results.getDouble(4);
				
				SQLsched = results.getObject(5); 
				ArrayList<String> keywordList = new ArrayList<>();
				
				for (String s: SQLsched.toString().split(",")) keywordList.add(s);
				
				double keyphrase_weight = results.getDouble(6);
				
				SQLsched = results.getObject(7); 
				ArrayList<String> keyphraseList = new ArrayList<>();
				
				for (String s: SQLsched.toString().split(",")) keyphraseList.add(s);

				
				RecommendedTutor rC = new RecommendedTutor(code, acronym_weight, acronymList,
						keyword_weight, keywordList, keyphrase_weight, keyphraseList);
				
				System.out.println(rC.toString());
				
				tutors.put(rC, acronym_weight+keyword_weight+keyphrase_weight);
				
				}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tutors;
	}

	public static void cancelRecommender() throws SQLException {
		connection.close();
		connection = null;
		currentInstance = null;
	}

}
