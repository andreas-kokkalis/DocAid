package aid.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import ws.palladian.preprocessing.scraping.ReadabilityContentExtractor;
import aid.project.recovery.InputDocument;

/**
 * Static class that contains the generic tools. Implemented as singleton to avoid multi-initialization of objects.
 * 
 * @author adrian
 * 
 */
public class UtilClass {

	/**
	 * File types
	 */

	public static String PDFFILE = "pdf";
	public static String DOCFILE = "msword";
	public static String ODTFILE = "vnd.oasis.opendocument.text";

	public static ReadabilityContentExtractor extractor;
	static TikaConfig tikaConfig;

	private static UtilClass instance = null;

	protected UtilClass() {
		// Exists only to defeat instantiation.
		extractor = new ReadabilityContentExtractor();
	}

	public static UtilClass getInstance() {
		if (instance == null) {
			instance = new UtilClass();
		}
		return instance;
	}

	public static ReadabilityContentExtractor getExtractor() {
		return extractor;
	}

	/**
	 * 
	 * @param f name of file
	 * @return the file type -- currently, "msword"(Doc/Docx), "vnd.oasis.opendocument.text"(ODT) and "pdf"(PDF)
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getTypeOfFile(File f) throws FileNotFoundException, IOException {
		TikaConfig tika = getTikaConfig();
		Metadata metadata = new Metadata();
		metadata.set(Metadata.RESOURCE_NAME_KEY, f.toString());
		MediaType mimetype = tika.getDetector().detect(TikaInputStream.get(f), metadata);
		return mimetype.getSubtype();
	}

	public InputDocument getInputDocument(File f) throws FileNotFoundException, IOException, SAXException, TikaException {
		if (getTypeOfFile(f).equalsIgnoreCase(DOCFILE))
			return createDocDocument(f);
		else if (getTypeOfFile(f).equalsIgnoreCase(ODTFILE))
			return createOdtDocument(f);
		else if(getTypeOfFile(f).equalsIgnoreCase(PDFFILE))
			return createPdfDocument(f);
		throw new NullPointerException("da fuck are you doing?");
	}

	/**
	 * Generates an InputDocument object from a pdf file
	 * 
	 * @param f The file
	 * @return InputDocument
	 * @throws IOException
	 */
	public static InputDocument createPdfDocument(File f) throws IOException {
		PDDocument document = null;
		try {
		document = PDDocument.load(f);
		}
		catch(IOException e) {
			System.out.println("PDF file: " + "\n" + e);
		}
		
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setStartPage(1);
		stripper.setEndPage(document.getNumberOfPages());
		String documentTitle = document.getDocumentInformation().getTitle();
		Integer numOfPages = document.getNumberOfPages();
		String content = stripper.getText(document);
		document.close();
		return new InputDocument(PDFFILE, documentTitle, content, numOfPages);
	}

	public static InputDocument createOdtDocument(File f) throws IOException, SAXException, TikaException {
		Metadata metadata = new Metadata();
		metadata.set(Metadata.RESOURCE_NAME_KEY, f.getAbsolutePath());
		ParseContext context = new ParseContext();

		ContentHandler textHandler = new BodyContentHandler();
		InputStream input = new FileInputStream(f);
		Parser parser = new AutoDetectParser();
		parser.parse(input, textHandler, metadata, context);
		return new InputDocument(ODTFILE, f.getName().substring(0, f.getName().length() - 4), textHandler.toString(), Integer.parseInt(metadata.get("Page-Count")));
	}

	public InputDocument createDocDocument(File f) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(f.getAbsolutePath()));
		WordExtractor wordExtractor = new WordExtractor(wordDoc);
		String text = wordExtractor.getText();
		int pageCount = wordExtractor.getSummaryInformation().getPageCount();
		wordExtractor.close();
		return new InputDocument(DOCFILE, f.getName().substring(0, f.getName().length() - 4), text, pageCount);
	}

	/**
	 * Foo method for testing files
	 * 
	 * @return a linked list of files used in testin
	 */
	public static LinkedList<File> getTestFiles() {
		File folder = new File("testdata");
		File[] listOfFiles = folder.listFiles();
		LinkedList<File> myListOfFiles = new LinkedList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				myListOfFiles.add(listOfFiles[i]);
				System.out.println("File " + listOfFiles[i].getName());
				
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return myListOfFiles;
	}

	private static TikaConfig getTikaConfig() {

		try {
			if (tikaConfig == null)
				tikaConfig = new TikaConfig();
			return tikaConfig;
		} catch (TikaException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Couldn't get tika config");
		return null;
	}
}
