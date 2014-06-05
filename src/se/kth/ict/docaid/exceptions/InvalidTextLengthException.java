package se.kth.ict.docaid.exceptions;

/**
 * Thrown when the text is not sufficient to complete an operation.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class InvalidTextLengthException extends Exception {
	private static final long serialVersionUID = -4286075414512910287L;

	public InvalidTextLengthException(String message) {
		super(message);
	}

}
