package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class Tester {
	public static void main(String[] args) {
		
		testOnlyOneTextNode();
		
		testEscapedTagStart();
		
		testEscapedEscape();
		
		testIllegalEscape();
		
		testIllegalEscapeInTextNode();
		
		testIllegalStringInMultipleRows();
		
		testStringInFourRows();
		
		testIllegalStringInMultipleRows();
		
		testIllegalEscapeInTag();
	}
	
	public static void testOnlyOneTextNode() {
		String text = "Ovo je \r\n"
				+ "sve jedan text node\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 1: " + node.toString());
	}
	
	public static void testEscapedTagStart() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 2: " + node.toString());
	}
	
	public static void testEscapedEscape() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 3: " + node.toString());
	}
	
	public static void testIllegalEscape() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 4: " + node.toString());
	}
	
	public static void testIllegalEscapeInTextNode() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 5: " + node.toString());
	}
	
	public static void testStringInMultipleRows() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 6: " + node.toString());
	}
	
	public static void testStringInFourRows() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 7: " + node.toString());
	}
	
	public static void testIllegalStringInMultipleRows() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 8: " + node.toString());
	}
	
	public static void testIllegalEscapeInTag() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		System.out.println("Test 9: " + node.toString());
	}
}
