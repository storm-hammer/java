package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class TestMain {
	
	public static void main(String[] args) {
		String text = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println(node.toString());
	}
	
}
