package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Demo extends JFrame {
	
	public Demo() {
		InputStream is = this.getClass().getResourceAsStream("icons/greenDisk.png");
		if(is==null) throw new NullPointerException("Input stream is null!");
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
		}
		Container cp = this.getContentPane();
		JTabbedPane tabs = new JTabbedPane();
		tabs.add(new JTextArea());
		tabs.setIconAt(0, new ImageIcon(bytes));
		cp.add(tabs);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Demo().setVisible(true);
		});
	}
}
