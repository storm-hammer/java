package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class CopyShellCommand implements ShellCommand {

	private String name = "Copy";
	private List<String> description;
	
	public CopyShellCommand() {
		description = new ArrayList<String>();
		description.add("The command expects two arguments: source file name and destination file name.");
		description.add("If destination file exists, the command asks user if it is allowed to overwrite it.");
		description.add("The command works only with files");
		description.add("If the second argument is directory, the command copies the original file into that directory using the original file name.");
	}

	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Utility util = new Utility(arguments.toCharArray());
		String origin = Utility.next();
		String destination = Utility.next();
		
		File file = new File(destination);
		
		if(Paths.get(destination) != null) {
			if(file.isDirectory()) {
				String[] parts = origin.split("\\");
				String newFile = destination+parts[parts.length-1];
				return writeToFile(env, origin, newFile);
			}
			env.writeln("File already exists, overwrite it? [y/n]");
			env.writeln(env.getPromptSymbol() + " ");
			if(env.readLine().equals("y")) {
				return writeToFile(env, origin, destination);
			} else {
				return ShellStatus.CONTINUE;
			}
		}
		return ShellStatus.CONTINUE;
		//File newFile = new File();
	}
	
	private ShellStatus writeToFile(Environment env, String origin, String destination) {
		try {
			FileOutputStream fos = new FileOutputStream(destination);
			FileInputStream fis = new FileInputStream(origin);
			byte[] bytes;
			bytes = fis.readAllBytes();
			fos.write(bytes);
			fos.close();
			fis.close();
			return ShellStatus.CONTINUE;
		} catch (IOException e) {
			env.writeln("File copying failed!");
			return ShellStatus.CONTINUE;
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
