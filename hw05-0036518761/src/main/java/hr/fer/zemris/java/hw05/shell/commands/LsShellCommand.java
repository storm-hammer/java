package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class LsShellCommand implements ShellCommand {

	private String name = "Ls";
	private List<String> description;
	
	public LsShellCommand() {
		description = new ArrayList<String>();
		description.add("The command takes a single argument – directory – and writes a directory listing.");
		description.add("The output consists of 4 columns.");
		description.add("First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		description.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		description.add("Third column contains creation time.");
		description.add("Fourth column contains file name.");
	}
	
	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Utility util = new Utility(arguments.toCharArray());
		String origin = Utility.next();
		
		if(!Files.isDirectory(Paths.get(origin))) {
			env.writeln("Invalid argument, argument should be directory!");
			return ShellStatus.CONTINUE;
		}
		File folder = new File(origin);
		String[] children = folder.list();
		
		for(String s : children) {
			
			String currentFilePath = origin + "\\" + s;
			StringBuilder line = new StringBuilder();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File currentFile = new File(currentFilePath);
			Path current = Paths.get(currentFilePath);
			
			BasicFileAttributeView faView = Files.getFileAttributeView(current, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				env.writeln("Accessing file attributes failed!");
				return ShellStatus.CONTINUE;
			}
			
			long size;
			if(currentFile.isDirectory()) {
				size = velicina(currentFile);
			} else {
				size = currentFile.length();
			}
			
			line.append(currentFile.isDirectory() ? "d" : "-");
			line.append(currentFile.canRead() ? "r" : "-");
			line.append(currentFile.canWrite() ? "w" : "-");
			line.append(currentFile.canExecute() ? "x " : "- ");
	
			line.append(String.format("%10d", size)+" ");
			FileTime fileTime = attributes.creationTime();
			line.append(sdf.format(new Date(fileTime.toMillis()))+" ");
			line.append(s);
			env.writeln(line.toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	private long velicina(File currentFile) {
		long size = 0;
		if(currentFile.isDirectory()) {
			File[] djeca = currentFile.listFiles();
			if(djeca == null) return 0;
			for(File d : djeca) {
				size += velicina(d);
			}
		} else {
			size += currentFile.length();
		}
		return size;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
