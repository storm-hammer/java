package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * 
 * @author User
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * 
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * 
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}