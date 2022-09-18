package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * 
 * @author User
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * 
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * 
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * 
	 * @param model
	 */
	void documentRemoved(SingleDocumentModel model);
}
