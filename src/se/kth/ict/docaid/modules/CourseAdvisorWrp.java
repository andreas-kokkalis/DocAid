package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.reader.RegistrationReader;

public class CourseAdvisorWrp {
	private ArrayList<String> docInput;
	private InputDocument registrationInput;
	private String preferencesInput;
	private ArrayList<DocAnalyzerWrp> analyzer;

	public CourseAdvisorWrp(ArrayList<String> docInput, InputDocument registrationInput, String preferencesInput) {
		this.docInput = docInput;
		this.registrationInput = registrationInput;
		this.preferencesInput = preferencesInput;

	}

	public void getSuggestions(Connection connection) throws IOException, InvalidTextLengthException {
		for (String doc : docInput) {
			analyzer.add(new DocAnalyzerWrp(doc, connection));
		}
		if (registrationInput != null) {
			LinkedList<String> courseCodes = RegistrationReader.detectCourseCodes(registrationInput.getBody());
		}
		if (!preferencesInput.isEmpty()) {
			ArrayList<String> phrase = new ArrayList<String>(Arrays.asList(preferencesInput.split(",")));
		}

		// Recommender something

		// Get list of recommendations.
	}
}
