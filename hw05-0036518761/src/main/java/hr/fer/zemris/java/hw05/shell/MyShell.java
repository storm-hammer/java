package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

public class MyShell {
	
	public static void main(String[] args) {
		
		MyShellEnvironment e = new MyShellEnvironment();
		SortedMap<String, ShellCommand> commands = e.commands();
		String line, commandName, arguments;
		ShellCommand command;
		ShellStatus status;
		
		e.writeln("Welcome to MyShell v 1.0");
		do {
			try {
				line = Utility.readCommand(e);
				commandName = Utility.extractName(line, e);
				arguments = Utility.extractArguments(line, commandName);
				command = commands.get(commandName);
				status = command.executeCommand(e, arguments);
			} catch (ShellIOException ex) {
				status = commands.get("exit").executeCommand(e, "");
			}
		} while(status != ShellStatus.TERMINATE);
	}
}
