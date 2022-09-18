package hr.fer.oprpp1.hw02.prob1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ParserTest {
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("resources/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka resources/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
	}
	
	@Test
	public void testOnlyOneTextNode() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(node.numberOfChildren(), 1, "The node has more than one child node");
	}
	
	@Test
	public void testEscapedTagStart() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(node.numberOfChildren(), 1, "The node has more than one child node");
	}
	
	@Test
	public void testEscapedEscape() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(node.numberOfChildren(), 1, "The node has more than one child node");
	}
	
	@Test
	public void testIllegalEscape() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testIllegalEscapeInTextNode() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testStringInMultipleRows() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(node.numberOfChildren(), 1, "The node has more than one child node");
	}
	
	@Test
	public void testStringInFourRows() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(node.numberOfChildren(), 1, "The node has more than one child node");
	}
	
	@Test
	public void testIllegalStringInMultipleRows() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testIllegalEscapeInTag() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
}
