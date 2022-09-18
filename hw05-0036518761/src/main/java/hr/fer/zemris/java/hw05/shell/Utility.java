package hr.fer.zemris.java.hw05.shell;

public class Utility {
	
	private static char[] data;
	private static int index = 0;
	
	public Utility(char[] newData) {
		index = 0;
		data = newData;
	}
	
	public static String extractName(String line, Environment e) {
		String command = line.split("\\s+")[0];
		if(!e.commands().containsKey(command)) {//can change to returning the string nevertheless
			e.writeln("Unknown command!");
			return null;
		}
		return command;
	}
	
	public static String extractArguments(String line, String name) {
		return line.equals(name) ? "" : line.substring(name.length()+1);
	}

	public static String readCommand(Environment e) {
		e.write(e.getPromptSymbol().toString() + " ");
		String line = e.readLine();
		
		while(line.charAt(line.length() - 1) == e.getMorelinesSymbol()) {
			e.write(e.getMultilineSymbol().toString() + " ");
			line = line.substring(0, line.length() - 1) + e.readLine();
		}
		
		return line;
	}
	
	public static String next() {
		StringBuilder sb = new StringBuilder();
		boolean escaped = false;
		skipBlanks();
		
		while(index < data.length) {
			if(data[index] == '\"' && !escaped) {
				escaped = true;
				index++;
			}
			if(data[index] == '\"' && escaped) {
				index++;
				break;
			}
			
			if(data[index] == ' ' && !escaped) {
				break;
			}
			
			sb.append(data[index++]);
		}
		
		return sb.toString();
	}
	
	private static void skipBlanks() {
		while(index < data.length) {
			if(isBlank(data[index])) {
				index++;
			} else {
				break;
			}
		}
	}
	
	private static boolean isBlank(char c) {
		if(c == ' ' || c == '\t' || c == '\n') {
			return true;
		}
		return false;
	}
}
