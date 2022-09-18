package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author User
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private List<SingleDocumentModel> documents;
	private List<MultipleDocumentListener> listeners;
	private SingleDocumentModel current;

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public DefaultMultipleDocumentModel() {
		super();
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				fireDocumentChanged();
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		
		return new Iterator<SingleDocumentModel>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				return index != documents.size();
			}

			@Override
			public SingleDocumentModel next() {
				return documents.get(index++);
			}
		};
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		documents.add(newDocument);
		JScrollPane current = new JScrollPane(newDocument.getTextComponent());
		this.current = newDocument;
		this.addTab("(unnamed)", current);
		this.setSelectedComponent(current);
		fireAdded();
		return this.current;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {//done
		return this.current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		
		Objects.requireNonNull(path);
		for(SingleDocumentModel m : documents) {
			if(m.getFilePath() == null) {
				continue;
			}
			if(m.getFilePath().equals(path)) {
				this.current = m;
				this.setSelectedIndex(this.documents.indexOf(m));
				return this.current;
			}
		}
		String content = null;
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this, 
					"Pogreška prilikom čitanja datoteke "+path+".", 
					"Pogreška", 
					JOptionPane.ERROR_MESSAGE);
		}
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, content);
		documents.add(newDocument);
		
		String fullName = path.toString();
		String name = fullName.substring(fullName.lastIndexOf('\\')+1);
		JScrollPane current = new JScrollPane(newDocument.getTextComponent());
		this.current = newDocument;
		this.addTab(name, current);
		this.setSelectedComponent(current);
		return this.current;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		Path destination = newPath == null ? model.getFilePath() : newPath;
		for(SingleDocumentModel m : documents) {
			if(m.getFilePath() == null) {
				continue;
			}
			if(m.getFilePath().equals(newPath)) {
				throw new IllegalArgumentException("Specified file already opened!");
			} 
		}
		File toBeSaved = destination.toFile();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(toBeSaved, false);
			byte[] bytes = model.getTextComponent().getText().getBytes();
			os.write(bytes);
			os.close();//UTF-8, obicni stream ako ne bude mogao FOS
		} catch (FileNotFoundException e1) {
			System.out.println("Could not save file, destination file was not found!");
		} catch (IOException e1) {
			System.out.println("Could not save file, interrupted input exception!");
		}
		model.setModified(false);
		model.setFilePath(destination);
		fireDocumentChanged();
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {//done
		if(this.documents.size() == 0) {
			return;
		}
		documents.remove(model);
		fireRemoved();
		this.removeTabAt(this.getSelectedIndex());
		if(documents.size() != 0) {
			this.current = documents.get(this.getSelectedIndex());
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return index < 0 ? null : documents.get(index);
	}
	
	/**
	 * 
	 */
	private void fireDocumentChanged() {
		for(MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(this.current, this.getDocument(this.getSelectedIndex()));
		}
		this.current = this.getDocument(this.getSelectedIndex());
	}
	
	/**
	 * 
	 */
	private void fireRemoved() {
		for(MultipleDocumentListener l : listeners) {
			l.documentRemoved(this.current);
		}
	}
	
	/**
	 * 
	 */
	private void fireAdded() {
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(this.current);
		}
	}
}
