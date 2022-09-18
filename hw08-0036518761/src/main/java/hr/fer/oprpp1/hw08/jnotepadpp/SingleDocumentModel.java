package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * 
 * @author User
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * 
	 * @return
	 */
	JTextArea getTextComponent();
	
	/**
	 * 
	 * @return
	 */
	Path getFilePath();
	
	/**
	 * 
	 * @param path
	 */
	void setFilePath(Path path);
	
	/**
	 * 
	 * @return
	 */
	boolean isModified();
	
	/**
	 * 
	 * @param modified
	 */
	void setModified(boolean modified);
	
	/**
	 * 
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * 
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
