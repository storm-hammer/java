package hr.fer.oprpp1.hw02;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ParserTest {
	
	@SuppressWarnings("unused")
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
					bos.write(buffer, 0, read);
			} return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
	
	private String readExample(String filename) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
		    if(is==null) throw new RuntimeException("Datoteka "+filename+" je nedostupna.");
		    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer1.txt").readAllBytes();//filename treba
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
	@Test
	public void testOnlyOneTextNode() {
		String text = readExample("extra/primjer1.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(1, node.numberOfChildren(), "The node has more than one child node");
	}
	
	@Test
	public void testEscapedTagStart() {
		String text = readExample("extra/primjer2.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(1, node.numberOfChildren(), "The node has more than one child node");
	}
	
	@Test
	public void testEscapedEscape() {
		String text = readExample("extra/primjer3.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(1, node.numberOfChildren(), "The node has more than one child node");
	}
	
	@Test
	public void testIllegalEscape() {
		String text = readExample("extra/primjer4.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testIllegalEscapeInTextNode() {
		String text = readExample("extra/primjer5.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testStringInMultipleRows() {
		String text = readExample("extra/primjer6.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(1, node.numberOfChildren(), "The node has more than one child node");
	}
	
	@Test
	public void testStringInFourRows() {
		String text = readExample("extra/primjer7.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(1, node.numberOfChildren(), "The node has more than one child node");
	}
	
	@Test
	public void testIllegalStringInMultipleRows() {
		String text = readExample("extra/primjer8.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testIllegalEscapeInTag() {
		String text = readExample("extra/primjer9.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testFromHomework() {
		String text = readExample("doc1.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		assertEquals(4, node.numberOfChildren(), "The node has more or less children than expected");
	}
}
