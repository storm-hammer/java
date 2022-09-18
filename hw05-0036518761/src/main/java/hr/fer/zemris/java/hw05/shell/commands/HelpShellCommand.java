package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {

	private String name = "Help";
	private List<String> description;
	
	public HelpShellCommand() {
		description = new ArrayList<String>();
		description.add("If started with no arguments, the command lists names of all supported commands.");
		description.add("If started with single argument, the command prints name and the description of selected command.");
		description.add("If no such command exists, the command prints appropriate error message.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			for(String s : env.commands().keySet()) {
				env.writeln(s);
			}
		} else {
			ShellCommand c = env.commands().get(arguments);
			if(c == null) {
				env.writeln("Non-existing command.");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Command name: "+arguments);
			for(String s : c.getCommandDescription()) {
				env.writeln(s);
			}
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
