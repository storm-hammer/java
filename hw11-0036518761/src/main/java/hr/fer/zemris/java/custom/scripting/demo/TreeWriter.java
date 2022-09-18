package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

	public static void main(String[] args) {
		String docBody = null;
		try {
			docBody = Files.readString(Paths.get(args[0]));
		} catch (IOException e) {
		}
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder sb = new StringBuilder("{$FOR ");
			
			sb.append(node.getVariable().asText()+" ");
			sb.append(node.getStartExpression().asText()+" ");
			sb.append(node.getEndExpression().asText()+" ");
			if(node.getStepExpression() != null) {
				sb.append(node.getStepExpression().asText());
			}
			sb.append("$}\n");
			
			for(int i = 0; i < node.numberOfChildren(); i++) {
				sb.append(node.getChild(i).toString());
			}
			sb.append("\n{$END$}\n");
			
			System.out.println(sb.toString());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			StringBuilder sb = new StringBuilder("{$");
			for(int i = 0; i < node.getElements().length; i++) {
				Element el = node.getElements()[i];
				sb.append(el.asText()+" ");
			}
			sb.append("$}");
			System.out.println(sb.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < node.numberOfChildren(); i++) {
				sb.append(node.getChild(i).toString());
			}
			System.out.println(sb.toString());
		}
		
		
		
	}
}
