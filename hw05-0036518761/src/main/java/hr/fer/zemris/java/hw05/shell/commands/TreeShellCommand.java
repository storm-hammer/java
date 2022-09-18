package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class TreeShellCommand implements ShellCommand {

	private String name = "Tree";
	private List<String> description;
	
	public TreeShellCommand() {
		description = new ArrayList<String>();
		description.add("The tree command expects a single argument: directory name and prints a tree.");
		description.add("Each directory level shifts output two charatcers to the right.");
	}

	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Utility util = new Utility(arguments.toCharArray());
		String folder = Utility.next();
		File file = new File(folder);
		
		if(file.isDirectory()) {
			ispis(file, 0);
		}
		
		return ShellStatus.CONTINUE;
	}
	
	private static void ispis(File staza, int razina) {
		if(staza.isDirectory()) {
			System.out.printf("%s%s%n", " ".repeat(razina*2), staza.getName());
			File[] djeca = staza.listFiles();
			if(djeca == null) return;
			for(File d : djeca) {
				ispis(d, razina+1);
			}
		}
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
