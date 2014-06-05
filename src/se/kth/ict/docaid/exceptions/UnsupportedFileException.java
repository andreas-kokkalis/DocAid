package se.kth.ict.docaid.exceptions;

/**
 * Thrown when tika cannot read the file type from the disk.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class UnsupportedFileException extends Exception{
	private static final long serialVersionUID = 1425773154489427656L;
	public UnsupportedFileException(String message) {
		super(message);
	}
}
