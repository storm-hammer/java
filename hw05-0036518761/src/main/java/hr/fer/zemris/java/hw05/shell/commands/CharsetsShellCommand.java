package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {
	
	private String name = "Charsets";
	private List<String> description;
	
	public CharsetsShellCommand() {
		description = new ArrayList<String>();
		description.add("Lists currently supported charsets for this Java platform, one charset per line.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		for(String s : charsets.keySet()) {
			env.writeln(s);
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
