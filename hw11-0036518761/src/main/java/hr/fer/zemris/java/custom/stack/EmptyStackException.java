package hr.fer.zemris.java.custom.stack;

/**
 * The class is an implementation of an exception in case of trying to
 * view or delete objects from an empty stack.
 * 
 * @author Mislav Prce
 */
	@SuppressWarnings("serial")
public class EmptyStackException extends RuntimeException {
	
	/**
	 * The default constructor for this class.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * The constructor with a parameter for receiving messages
	 * about the exceptions. 
	 * 
	 * @param message a message about the exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
