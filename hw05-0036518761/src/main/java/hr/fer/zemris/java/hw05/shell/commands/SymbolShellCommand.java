package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {

	private String name = "Symbol";
	private List<String> description;
	
	public SymbolShellCommand() {
		description = new ArrayList<String>();
		description.add("If started with only a name of a symbol, the command writes the designated character for that symbol name");
		description.add("If started with a name of a symbol followed by a character, the command changes the selected symbol to the given character");
		description.add("The symbols which can be changed are for the prompt symbol, multiline symbol and morelines symbol");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		if(args.length == 1) {
			switch (args[0]) {//we can reduce code redundancy by getting the symbol in the switch, and writing it after the switch
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '"+env.getMorelinesSymbol()+"'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '"+env.getMultilineSymbol()+"'");
				break;
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '"+env.getPromptSymbol()+"'");
				break;
			default:
				env.writeln("Unknown command!");
				return ShellStatus.CONTINUE;
			}
		} else {
			if(args[1].length() != 1) {
				env.writeln("Invalid symbol!");
				return ShellStatus.CONTINUE;
			}
			char symbol = args[1].charAt(0);
			switch (args[0]) {
			case "MORELINES":
				env.writeln("Symbol for MORELINES changed from '"+env.getMorelinesSymbol()+"' to '"+symbol+"'");
				env.setMorelinesSymbol(symbol);
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE changed from '"+env.getMultilineSymbol()+"' to '"+symbol+"'");
				env.setMultilineSymbol(symbol);
				break;
			case "PROMPT":
				env.writeln("Symbol for PROMPT changed from '"+env.getPromptSymbol()+"' to '"+symbol+"'");
				env.setPromptSymbol(symbol);
				break;
			default:
				env.writeln("Unknown command!");
				return ShellStatus.CONTINUE;
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
