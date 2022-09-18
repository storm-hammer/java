package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class MkdirShellCommand implements ShellCommand {

	private String name = "Mkdir";
	private List<String> description;
	
	public MkdirShellCommand() {
		description = new ArrayList<String>();
		description.add("The command takes a single argument: directory name, and creates the appropriate directory structure.");
	}

	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Utility util = new Utility(arguments.toCharArray());
		Path p = Paths.get(Utility.next());
		try {
			Files.createDirectories(p);
		} catch (IOException e) {
			env.writeln("Creating directories failed!");
		}
		return ShellStatus.CONTINUE;
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
