package de.zaunkoenigweg.edi.web.library;

public class LibraryException extends RuntimeException {

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(Throwable cause) {
		super(cause);
	}
}
