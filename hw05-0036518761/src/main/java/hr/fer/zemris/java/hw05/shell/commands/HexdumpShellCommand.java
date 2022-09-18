package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utility;

public class HexdumpShellCommand implements ShellCommand {

	private String name = "Hexdump";
	private List<String> description;
	
	public HexdumpShellCommand() {
		description = new ArrayList<String>();
		description.add("The command expects a single argument: file name, and produces hex-output.");
		description.add("All bytes whose value is less than 32 or greater than 127 are replaced by '.'");
	}

	@SuppressWarnings("unused")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Utility util = new Utility(arguments.toCharArray());
		String fileName = Utility.next();
		byte[] data;
		try {
			data = Files.readAllBytes(Paths.get(fileName));
		} catch (IOException e) {
			env.writeln("Unable to read file!");
			return ShellStatus.CONTINUE;
		}
		
		for(int i = 0; i < data.length; i += 16) {
			int endIndex = i + 16 < data.length ? i + 16 : data.length;
			hexLine(env, i, endIndex, data);
		}
		
		return ShellStatus.CONTINUE;
		//hexdump C:\Users\User\Desktop\FER\cmdCommands.txt
	}
	
	private void hexLine(Environment env, int startIndex, int endIndex, byte[] data) {
		
		env.write(String.format("%8s: ", Integer.toHexString(startIndex)));
		for(int j = startIndex; j < startIndex + 8; j++) {
			env.write(String.format("%s ", Util.bytetohex(new byte[] {data[j]})));
		}
		env.write("| ");
		for(int j = startIndex + 8; j < endIndex; j++) {
			env.write(String.format("%s ", Util.bytetohex(new byte[] {data[j]})));
		}
		env.write("| ");
		byte[] bytes = new byte[16];
		for(int j = startIndex; j < endIndex; j++) {
			bytes[j%16] = (byte) (data[j] >= 32 && data[j] <= 127 ? data[j] : 46);
		}
		env.write(String.format("%s", new String(bytes)));
		env.writeln("");
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
