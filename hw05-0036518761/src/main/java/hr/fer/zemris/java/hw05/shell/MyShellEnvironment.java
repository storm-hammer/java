package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw05.shell.commands.*;

public class MyShellEnvironment implements Environment {
	
	private Scanner sc;
	private Character multilineSymbol;
	private Character promptSymbol;
	private Character morelinesSymbol;
	private SortedMap<String, ShellCommand> commands;
	
	public MyShellEnvironment() {
		this.morelinesSymbol = '\\';
		this.multilineSymbol = '|';
		this.promptSymbol = '>';
		commands = new TreeMap<>();
		commandInit();
		sc = new Scanner(System.in);
	}
	
	private void commandInit() {
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			line = sc.nextLine();
		} catch(NoSuchElementException | IllegalStateException e) {
			throw new ShellIOException(e.getMessage());
		}
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch(Exception ex) {
			throw new ShellIOException(ex.getMessage());
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch(Exception ex) {
			throw new ShellIOException(ex.getMessage());
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
