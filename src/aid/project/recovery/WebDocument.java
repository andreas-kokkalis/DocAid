package aid.project.recovery;

import aid.project.utils.UtilClass;
import ws.palladian.classification.numeric.KNNClassifier;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;
import ws.palladian.preprocessing.scraping.ReadabilityContentExtractor;
import ws.palladian.preprocessing.scraping.WebPageContentExtractor;

public class WebDocument {

	String title;
	String body;
	String url;
	
	/**
	 * Create a WebDocument from a website that contains a title and a body 
	 * @param url A String that represents the url of the website that stores the course directory, i.e., http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en
	 */
	public WebDocument(final String url){
		this.url = url;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				generateContent(url);
				//getKeywords();
			}
			
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	
	private void setTitle(String title) {
		this.title = title;
	}

	private void setBody(String body) {
		this.body = body;
	}

	private void generateContent(String url){
		ReadabilityContentExtractor extractor = UtilClass.getInstance().getExtractor();
		try {
			extractor.setDocument(this.url);
			setTitle(extractor.getResultTitle());
			setBody(extractor.getResultText());
		} catch (PageContentExtractorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns a String representation of the WebDocument
	 */
	@Override
	public String toString() {
		return "WebDocument [url=" + url + ", title=" + title + ", body="
				+ body + "]";
	}
	
	
}
