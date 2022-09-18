package hr.fer.zemris.java.hw05.shell;

@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {
	
	public ShellIOException() {
		super();
	}
	
	public ShellIOException(String message) {
		super(message);
	}
}
