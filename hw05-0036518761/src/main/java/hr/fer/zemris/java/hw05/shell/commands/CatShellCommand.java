package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class CatShellCommand implements ShellCommand {

	private String name = "Cat";
	private List<String> description;
	
	public CatShellCommand() {
		description = new ArrayList<String>();
		description.add("The command takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that will be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset will be used.");
		description.add("This command opens given file and writes its content to console.");
	}

	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String path, set;
		Charset charset;
		Utility util = new Utility(arguments.toCharArray());
		
		path = Utility.next();
		set = Utility.next();
		
		if(set.equals("")) {
			charset = Charset.defaultCharset();
		} else {
			charset = Charset.forName(set);
		}
		
		try {
			for(String s : Files.readAllLines(Paths.get(path), charset)) {
				env.writeln(s);
			}
		} catch (IOException e) {
			env.writeln("File reading failed!");
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
