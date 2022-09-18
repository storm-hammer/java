package hr.fer.zemris.java.custom.collections;

/**
 * The class <code>Processor</code> is a model of an object capable
 * of performing some operation on the received object.
 * 
 * @author Mislav Prce
 */
public interface Processor {
	
	/**
	 * This method processes objects that are passed to it.
	 * 
	 * @param value is an object which will be processed
	 * @return nothing in this implementation
	 */
	void process(Object value);
}
