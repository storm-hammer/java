package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author User
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private List<SingleDocumentListener> listeners;
	private boolean modified;
	private JTextArea text;
	private Path path;
	
	/**
	 * 
	 * @param path
	 * @param content
	 */
	public DefaultSingleDocumentModel(Path path, String content) {
		this.text = new JTextArea(content);
		this.path = path;
		this.listeners = new ArrayList<SingleDocumentListener>();
		this.text.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
		this.setModified(false);
	}

	@Override
	public JTextArea getTextComponent() {
		return text;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null) {
			throw new IllegalArgumentException("The path cannot be null!");
		}
		this.path = path;
		firePath();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		fireModified();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}
	
	/**
	 * 
	 */
	private void fireModified() {
		for(SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * 
	 */
	private void firePath() {
		for(SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}
}
