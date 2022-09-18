package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * 
 * @author User
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * 
	 * @return
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * 
	 * @return
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * 
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * 
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * 
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * 
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * 
	 * @return
	 */
	int getNumberOfDocuments();
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	SingleDocumentModel getDocument(int index);
}
