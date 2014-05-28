package se.kth.ict.docaid.documents;

public class InputDocument {

	String type;
	String title;
	String body;
	int numberOfPages;

	public InputDocument(String type, String title, String body, int numberOfPages) {
		super();
		this.type = type;
		this.title = title;
		this.body = body;
		this.numberOfPages = numberOfPages;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	@Override
	public String toString() {
		return "InputDocument [type=" + type + ", title=" + title + ", numberOfPages=" + numberOfPages + ", body=" + body + "]";
	}

}
