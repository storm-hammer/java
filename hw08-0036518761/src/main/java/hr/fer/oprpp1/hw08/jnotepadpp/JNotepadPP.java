package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JTextArea;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Utilities;

/**
 * 
 * @author User
 *
 */
public class JNotepadPP extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel documents;
	private String buffer;
	private ImageIcon redDisk, greenDisk;
	private JPanel statusBar;
	private LocalizationProvider lp = LocalizationProvider.getInstance();
	private FormLocalizationProvider flp = new FormLocalizationProvider(lp, this);
	private static boolean endClock = false;
	private JMenu toolMenu;
	
	/**
	 * 
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(300, 300);
		setSize(800, 400);
		redDisk = loadIcon("redDisk.png");
		greenDisk = loadIcon("greenDisk.png");
		initGUI();
	}
	
	/**
	 * 
	 */
	private void initGUI() {
		
		this.getContentPane().setLayout(new BorderLayout());
		documents = new DefaultMultipleDocumentModel();
		this.getContentPane().add(documents, BorderLayout.CENTER);
		documents.addMultipleDocumentListener(l);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkModifications();
			}
		});
		
		createStatusBar();
		createActions();
		createMenus();
		createToolbars();
	}
	
	//Status bar
	
	private void createStatusBar() {
		
		statusBar = new JPanel();
		//floatable toolbar, jel misli da se moze i dolje zalijepiti ??
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setLayout(new GridLayout());
		
		statusBar.add(new JLabel("length:"));
		statusBar.add(new JLabel("Ln:"+""+" Col:"+""+" Sel:"+""));

		Sat clock = new Sat();
		clock.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBar.add(clock);
		
		this.add(statusBar, BorderLayout.SOUTH);
	}
	
	//Toolbars
	
	/**
	 * 
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeCurrentAction));
		toolBar.add(new JButton(exitAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutTextAction));
		toolBar.add(new JButton(copyTextAction));
		toolBar.add(new JButton(pasteTextAction));
		toolBar.add(new JButton(showStatsAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	//Menus
	
	/**
	 * 
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LocalizableMenu("file", flp);
		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeCurrentAction));
		fileMenu.add(new JMenuItem(exitAction));
		menuBar.add(fileMenu);
		
		JMenu editMenu = new LocalizableMenu("edit", flp);
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		editMenu.add(new JMenuItem(showStatsAction));
		menuBar.add(editMenu);
		
		JMenu languageMenu = new LocalizableMenu("languages", flp);
		languageMenu.add(new JMenuItem(englishAction));
		languageMenu.add(new JMenuItem(germanAction));
		languageMenu.add(new JMenuItem(croatianAction));
		menuBar.add(languageMenu);
		
		toolMenu = new LocalizableMenu("tools", flp);
		
		JMenu caseMenu = new LocalizableMenu("case", flp);
		caseMenu.add(new JMenuItem(upperCaseAction));
		caseMenu.add(new JMenuItem(lowerCaseAction));
		caseMenu.add(new JMenuItem(invertCaseAction));
		
		JMenu sortMenu = new LocalizableMenu("sort", flp);
		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));
		
		toolMenu.add(new JMenuItem(uniqueAction));
		toolMenu.add(sortMenu);
		toolMenu.add(caseMenu);
		menuBar.add(toolMenu);
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * 
	 * @author User
	 *
	 */
	static class Sat extends JLabel {

		private static final long serialVersionUID = 1L;
		
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		public Sat() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(JNotepadPP.endClock != true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		private void updateTime() {
			this.setText(date.format(LocalDate.now())+" "+time.format(LocalTime.now()));
		}
	}
	
	//Listeners
	
	/**
	 * 
	 */
	private CaretListener caretListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			calculate();
		}
	};
	
	/**
	 * 
	 */
	private MultipleDocumentListener l = new MultipleDocumentListener() {
		
		@Override
		public void documentRemoved(SingleDocumentModel model) {
			if(model != null) {
				model.removeSingleDocumentListener(singleListener);
				model.getTextComponent().removeCaretListener(caretListener);
			}
		}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
			if(model != null) {
				model.addSingleDocumentListener(singleListener);
				model.getTextComponent().addCaretListener(caretListener);
			}
		}
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if(previousModel == null && currentModel == null) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Cannot change current document", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(previousModel != null) {
				previousModel.removeSingleDocumentListener(singleListener);
				previousModel.getTextComponent().removeCaretListener(caretListener);
			}
			Path path = null;
			if(currentModel != null) {
				currentModel.addSingleDocumentListener(singleListener);
				currentModel.getTextComponent().addCaretListener(caretListener);
				path = currentModel.getFilePath();
			}
			if(documents.getNumberOfDocuments() == 0) {
				JNotepadPP.this.setTitle("JNotepad++");
				return;
			}
			JNotepadPP.this.setTitle((path != null ? path.toString() : "(unnamed)") + " - JNotepad++");
		}
	};
	
	/**
	 * 
	 */
	private SingleDocumentListener singleListener = new SingleDocumentListener() {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			ImageIcon icon = model.isModified() ? redDisk : greenDisk;
			documents.setIconAt(documents.getSelectedIndex(), icon);
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			String name = getName(model.getFilePath());
			documents.setTitleAt(documents.getSelectedIndex(), name);
		}
	};
	
	//Action variables
	
	/**
	 * 
	 */
	private Action ascendingAction = new LocalizableAction("ascending", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortLines(false);
		}
	};
	
	/**
	 * 
	 */
	private Action descendingAction = new LocalizableAction("descending", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortLines(true);
		}
	};
	
	/**
	 * 
	 */
	private Action uniqueAction = new LocalizableAction("unique", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextArea current = documents.getCurrentDocument().getTextComponent();
			Document currentDoc = current.getDocument();
			
			try {
				int firstLine = current.getLineOfOffset(current.getSelectionStart());
				int lastLine = current.getLineOfOffset(current.getSelectionEnd());
				int offset = current.getLineStartOffset(firstLine);
				String text = "";
				
				text = current.getText().lines()
								 .skip(firstLine)
								 .limit(lastLine - firstLine + 1)
								 .distinct()
								 .collect(Collectors.joining("\n"));
				
				currentDoc.remove(offset, current.getSelectedText().length());
				currentDoc.insertString(offset, text, null);
				
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
					 						 

		}
	};
	
	/**
	 * 
	 */
	private Action upperCaseAction = new LocalizableAction("upper", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase((c) -> {
				return Character.toUpperCase(c);
			});
		}
	};
	
	/**
	 * 
	 */
	private Action lowerCaseAction = new LocalizableAction("lower", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase((c) -> {
				return Character.toLowerCase(c);
			});
		}
	};
	
	/**
	 * 
	 */
	private Action invertCaseAction = new LocalizableAction("invert", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase((c) -> {
				return Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
			});
		}
	};
	
	/**
	 * 
	 */
	private Action englishAction = new LocalizableAction("english", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			lp.setLanguage("en");
		}
	};
	
	/**
	 * 
	 */
	private Action germanAction = new LocalizableAction("german", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			lp.setLanguage("de");
		}
	};
	
	/**
	 * 
	 */
	private Action croatianAction = new LocalizableAction("croatian", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			lp.setLanguage("hr");
		}
	};
	
	/**
	 * 
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Path filePath = chooseFile();
			if(filePath == null) {
				return;
			}
			documents.loadDocument(filePath);
		}
	};
	
	/**
	 * 
	 */
	private Action closeCurrentAction = new LocalizableAction("close", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			documents.closeDocument(documents.getCurrentDocument());
		}
	};
	
	/**
	 * 
	 */
	private Action createDocumentAction = new LocalizableAction("create", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			documents.createNewDocument();
		}
	};
	
	/**
	 * 
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!documents.getCurrentDocument().isModified()) {
				return;	
			}
			Path filePath = chooseFile();
			if(filePath == null) {
				return;
			}
			documents.saveDocument(documents.getCurrentDocument(), filePath);
		}
	};
	
	/**
	 * 
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!documents.getCurrentDocument().isModified()) {
				return;
			}
			Path p = null;
			if(documents.getCurrentDocument().getFilePath() == null) {
				p = chooseFile();
			}
			documents.saveDocument(documents.getCurrentDocument(), p);
		}
	};
	
	/**
	 * 
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {//done
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkModifications();
		}
	};
	
	/**
	 * 
	 */
	private Action cutTextAction = new LocalizableAction("cut", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = documents.getCurrentDocument();
			if(current == null) {
				return;
			}
			JTextArea textArea = current.getTextComponent();
			Document doc = textArea.getDocument();
			int l1, l2;
			buffer = textArea.getSelectedText();
			Caret caret = textArea.getCaret();
			l1 = Math.min(caret.getDot(), caret.getMark());
			l2 = Math.max(caret.getDot(), caret.getMark());

			try {
				doc.remove(l1, l2-l1);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};
	
	/**
	 * 
	 */
	private Action copyTextAction = new LocalizableAction("copy", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = documents.getCurrentDocument();
			if(current == null) {
				return;
			}
			JTextArea textArea = current.getTextComponent();
			buffer = textArea.getSelectedText();
		}
	};
	
	/**
	 * 
	 */
	private Action pasteTextAction = new LocalizableAction("paste", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = documents.getCurrentDocument();
			if(buffer == null || current == null) {
				return;
			}
			JTextArea textArea = current.getTextComponent();
			Document doc = textArea.getDocument();
			int l1, l2;
			Caret caret = textArea.getCaret();
			l1 = Math.min(caret.getDot(), caret.getMark());
			l2 = Math.min(caret.getDot(), caret.getMark());
			try {
				doc.remove(l1, l2-l1);
				doc.insertString(l1, buffer, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};
	
	/**
	 * 
	 */
	private Action showStatsAction = new LocalizableAction("stats", flp) {//done
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int chars, nonBlankChars;
			long lines;
			SingleDocumentModel current = documents.getCurrentDocument();
			if(current == null) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "There are no documents opened!");
				return;
			}
			String text = current.getTextComponent().getText();
			chars = text.length();
			nonBlankChars = text.length() - text.split("\\s+").length-1;
			lines = text.lines().count();
			JOptionPane.showMessageDialog(JNotepadPP.this, "Your document has "+chars+" characters, "+nonBlankChars+" non-blank characters and "+lines+" lines.");
		}
	};
	
	//Private helper methods
	
	/**
	 * 
	 */
	private void sortLines(boolean reversed) {
		Locale locale = new Locale(lp.getLanguage());
		Collator collator = Collator.getInstance(locale);
		Comparator<String> comparator = reversed ? collator.reversed()::compare : collator::compare;
		
		JTextArea current = documents.getCurrentDocument().getTextComponent();
		Document currentDoc = current.getDocument();
		
		try {
			int firstLine = current.getLineOfOffset(current.getSelectionStart());
			int lastLine = current.getLineOfOffset(current.getSelectionEnd());
			int offset = current.getLineStartOffset(firstLine);
			
			String text = "";
			text = current.getText().lines()
							 .skip(firstLine)
							 .limit(lastLine - firstLine + 1)
							 .sorted(comparator)
							 .collect(Collectors.joining("\n"));
			
			currentDoc.remove(offset, text.length());
			currentDoc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param function
	 */
	private void changeCase(Function<Character, Character> function) {
		JTextArea current = documents.getCurrentDocument().getTextComponent();
		Document currentDoc = current.getDocument();
		int len = Math.abs(current.getCaret().getDot()-current.getCaret().getMark());
		int offset = Math.min(current.getCaret().getDot(),current.getCaret().getMark());
		
		try {
			String text = currentDoc.getText(offset, len);
			
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				znakovi[i] = function.apply(znakovi[i]);
			}
			text = new String(znakovi);
			
			currentDoc.remove(offset, len);
			currentDoc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void checkModifications() {
		boolean exit = true;
		for(SingleDocumentModel doc : documents) {
			if(doc.isModified()) {
				Path p = doc.getFilePath();
				int n = JOptionPane.showOptionDialog(this, "Želite li spremiti promjene?", p == null ? "(unnamed)" : p.toString(),
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION); 
				if(n == JOptionPane.YES_OPTION) {
					if(p == null) {
						p = chooseFile();//mozda premjestiti ovo u defaultMultiple jer je lakse da je sve u save metodi tj jedino tako ima smisla
					}
					documents.saveDocument(doc, p);
				} else if(n == JOptionPane.NO_OPTION) {
					continue;
				} else {
					exit = false;
					break;
				}	
			}
		}
		if(exit) {
			dispose();
			endClock = true;
		}
		return;
	}
	
	/**
	 * 
	 * @return
	 */
	private Path chooseFile() {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select file");
		if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if(!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
					"Pogreška", 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return filePath;
	}
	
	/**
	 * 
	 */
	private void calculate() {
		JTextArea current = documents.getCurrentDocument().getTextComponent();
		JLabel length = (JLabel) statusBar.getComponent(0);
		length.setText("length:"+current.getText().length());
		
		int ln = 0, col = 0, sel = 0;
		try {
			int caretPos = current.getCaretPosition();
			ln = current.getLineOfOffset(caretPos) + 1;
			
			int offset = Utilities.getRowStart(current, caretPos);
			col =  caretPos - offset + 1;
			
			sel = current.getSelectionEnd() - current.getSelectionStart();
		} catch (BadLocationException e) {
			
		}
		JLabel stats = (JLabel) statusBar.getComponent(1);
		stats.setText("Ln:"+ln+" Col:"+col+" Sel:"+sel);

		if(current.getSelectedText() != null) {
			toolMenu.setEnabled(true);
		} else {
			toolMenu.setEnabled(false);
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private String getName(Path path) {
		String fullName = path.toString();
		return fullName.substring(fullName.lastIndexOf('\\')+1);
	}
	
	/**
	 * 
	 * @param icon
	 * @return
	 */
	private ImageIcon loadIcon(String icon) {
		InputStream is = this.getClass().getResourceAsStream("icons/" + icon);
		if(is==null) throw new NullPointerException("Input stream is null!");
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
		}
		return new ImageIcon(bytes);
	}
	
	//Actions
	
	/**
	 * 
	 */
	private void createActions() {
		
		invertCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control Y"));
		invertCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Y); 
		invertCaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Inverts the case of the selected text.");
		
		lowerCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W"));
		lowerCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_W); 
		lowerCaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets the selected text to lower case.");
		
		upperCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control U"));
		upperCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_U); 
		upperCaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets the selected text to upper case.");
		
		ascendingAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control D"));
		ascendingAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D); 
		ascendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sorts the selected line with ascending order.");
		
		descendingAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control Z"));
		descendingAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Z); 
		descendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sorts the selected line with descending order.");
		
		uniqueAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control Q"));
		uniqueAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Q); 
		uniqueAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Removes all occurrences of the selected text, except the first one.");
		
		englishAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I"));
		englishAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_I); 
		englishAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets english as the language for the editor.");

		germanAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control G"));
		germanAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_G); 
		germanAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets german as the language for the editor.");

		croatianAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control R"));
		croatianAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_R); 
		croatianAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets croatian as the language for the editor.");

		showStatsAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control T"));
		showStatsAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_T); 
		showStatsAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Shows number of characters, non-blank characters and lines.");

		pasteTextAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control V")); 
		pasteTextAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_V); 
		pasteTextAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Pastes previously copied text at the designated location.");

		copyTextAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")); 
		copyTextAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C); 
		copyTextAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Copies the currently selected text.");

		cutTextAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X")); 
		cutTextAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		cutTextAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Copies the currently selected text and removes it from the file.");

		createDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")); 
		createDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N); 
		createDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Creates a new blank file and switches to its tab.");

		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Opens an already existing file.");

		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Saves the changes to the current file on disk.");

		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control A")); 
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A); 
		saveAsDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Saves the changes to the current file to the desired location with the desired name.");
		
		closeCurrentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control L")); 
		closeCurrentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_L); 
		closeCurrentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Closes the current tab.");
		
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control E")); 
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_E); 
		exitAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Exits the program.");
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
	
}
