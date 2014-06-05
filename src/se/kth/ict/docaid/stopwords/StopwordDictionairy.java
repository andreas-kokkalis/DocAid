package se.kth.ict.docaid.stopwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Loads English, Swedish and relevant to courses stopwords from the corresponding files.
 * It can be used to read from the files and populate the database.
 * <p>It is faster to use the database than iterate sequentially a large dictionary to find a match.</p>
 * 
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
@Deprecated
public class StopwordDictionairy {
	private HashSet<String> stopwordsSv;
	private HashSet<String> stopwordsEn;
	private HashSet<String> stopwordsCourse;

	public StopwordDictionairy() {
		stopwordsSv = new HashSet<String>();
		File fileSv = new File("data/stopwords/stopwords_sv.txt");
		InputStreamReader isSv;
		try {
			isSv = new InputStreamReader(new FileInputStream(fileSv), "UTF-8");
			String wordSv = null;
			BufferedReader br = new BufferedReader(isSv);
			while ((wordSv = br.readLine()) != null) {
				stopwordsSv.add(wordSv);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		stopwordsEn = new HashSet<String>();
		File txtEn = new File("data/stopwords/stopwords_en.txt");
		InputStreamReader isEn;
		try {
			isEn = new InputStreamReader(new FileInputStream(txtEn), "UTF-8");
			String wordEn = null;
			BufferedReader br = new BufferedReader(isEn);
			while ((wordEn = br.readLine()) != null) {
				stopwordsEn.add(wordEn);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		stopwordsCourse = new HashSet<String>();
		File txtCourse = new File("data/stopwords/course_stopwords.txt");
		InputStreamReader isCourse;
		try {
			isCourse = new InputStreamReader(new FileInputStream(txtCourse), "UTF-8");
			String wordCourse = null;
			BufferedReader br = new BufferedReader(isCourse);
			while ((wordCourse = br.readLine()) != null) {
				stopwordsCourse.add(wordCourse);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public HashSet<String> getStopwordsSv() {
		return stopwordsSv;
	}

	public HashSet<String> getStopwordsEn() {
		return stopwordsEn;
	}

	public HashSet<String> getStopwordsCourse() {
		return stopwordsCourse;
	}
}
