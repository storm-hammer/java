package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.stack.ObjectStack;

/**
 * 
 * @author Mislav Prce
 *
 */
public class SmartScriptParser {
	
	private SmartScriptLexer lexer;
	private Node currentNode, parentNode;
	private ObjectStack stack;
	
	/**
	 * Constructor that receives a text argument which it then parses.
	 * 
	 * @param text the text to be parsed
	 */
	public SmartScriptParser(String text) {
		if(text == null) {
			throw new SmartScriptParserException("Parser input cannot be null!");
		}
		this.lexer = new SmartScriptLexer(text);
		parse();
	}
	
	/**
	 * The method returns a document node of the parsed text.
	 * 
	 * @return the document node of the parsed text.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.pop();
	}
	
	/**
	 * The method parses the text given in the constructor.
	 */
	private void parse() {
		
		stack = new ObjectStack();
		currentNode = new DocumentNode();
		stack.push(currentNode);
		
		try {
			while(lexer.nextToken().getType() != SmartScriptTokenType.EOF) {
				if(lexer.getSmartScriptLexerState() == SmartScriptLexerState.TEXT) {
					parseText();//pogresno procita zadnji tag pa  zapne u petlji
				} else {
					parseTag();
				}
			}
		} catch(Exception ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
		
		if(stack.isEmpty() || stack.size() != 1) {
			throw new SmartScriptParserException("There was an uneven number of for and end tags");
		}
	}
	
	/**
	 * The method parses a piece of text according to the rules of tag-parsing.
	 */
	private void parseTag() {
		
		String name = (String)lexer.getToken().getValue();
		
		if(name.toUpperCase().equals("FOR")) {
			parseForNode();
		}
		
		if(name.toUpperCase().equals("END")) {
			parseEndNode();
		}
		
		if(name.equals("=") || lexer.getToken().getType() == SmartScriptTokenType.VARIABLE) {
			parseEchoNode();
		}
	}
	
	/**
	 * The method parses an Echo node.
	 */
	private void parseEchoNode() {
		
		Element[] nodeElements;
		List<Element> elementList = new ArrayList<>();
		
		while(lexer.nextToken().getType() != SmartScriptTokenType.TAG_END) {
			elementList.add(saveExpression(lexer.getToken().getType()));
		}
		
		nodeElements = new Element[elementList.size()];
		for(int j = 0; j < elementList.size(); j++) {
			nodeElements[j] = elementList.get(j);
		}
		currentNode = new EchoNode(nodeElements);
		addChildToStack();
		
		lexer.setSmartScriptLexerState(SmartScriptLexerState.TEXT);
	}
	
	/**
	 * The method parses an end node.
	 */
	private void parseEndNode() {
		stack.pop();
		if(lexer.nextToken().getType() != SmartScriptTokenType.TAG_END) {
			throw new SmartScriptParserException("Invalid end tag");
		}
		lexer.setSmartScriptLexerState(SmartScriptLexerState.TEXT);
	}
	
	/**
	 * The method parses a node according to the rules of parsing a "for" node.
	 */
	private void parseForNode() {
		
		ElementVariable variable;
		Element[] args = new Element[3];
		
		if(lexer.nextToken().getType() == SmartScriptTokenType.VARIABLE) {
			variable = new ElementVariable((String)lexer.getToken().getValue());
		} else {
			throw new SmartScriptParserException("First token must be a variable");
		}
		
		int i = 0;
		while(i < 2) {
			if(validateType(lexer.nextToken().getType())) {
				SmartScriptTokenType type = lexer.getToken().getType();
				args[i] = saveExpression(type);
			} else {
				throw new SmartScriptParserException("Invalid for loop argument");
			}
			i++;
		}
		
		lexer.nextToken();
		if(validateType(lexer.getToken().getType())) {
			args[2] = saveExpression(lexer.getToken().getType());
			if(lexer.nextToken().getType() != SmartScriptTokenType.TAG_END) {
				throw new SmartScriptParserException("Too many arguments");
			}
		} else if(lexer.getToken().getType() != SmartScriptTokenType.TAG_END) {
			throw new SmartScriptParserException("Invalid step expression");
		}
		
		currentNode = new ForLoopNode(variable, args[0], args[1], args[2]);
		addChildToStack();
		stack.push(currentNode);
		
		lexer.setSmartScriptLexerState(SmartScriptLexerState.TEXT);
	}
	
	private Element saveExpression(SmartScriptTokenType type) {
		if(type == SmartScriptTokenType.DOUBLE) {
			return new ElementConstantDouble((Double)lexer.getToken().getValue());
		} else if(type == SmartScriptTokenType.INTEGER) {
			return new ElementConstantInteger((Integer)lexer.getToken().getValue());
		} else if(type == SmartScriptTokenType.VARIABLE) {
			return new ElementVariable((String)lexer.getToken().getValue());
		} else if(type == SmartScriptTokenType.STRING) {
			return new ElementString((String)lexer.getToken().getValue());
		} else if(type == SmartScriptTokenType.FUNCTION) {
			return new ElementFunction((String)lexer.getToken().getValue());
		} else if(type == SmartScriptTokenType.OPERATOR) {
			return new ElementOperator((String)lexer.getToken().getValue());
		}
		return null;
	}

	/**
	 * The method processes a part of text according to the given rules.
	 */
	private void parseText() {
		
		if(lexer.getToken().getType() == SmartScriptTokenType.STRING) {
			currentNode = new TextNode((String)lexer.getToken().getValue());
			addChildToStack();
		} else {
			lexer.setSmartScriptLexerState(SmartScriptLexerState.TAG);
		}
	}
	
	/**
	 * The method adds the current node to the child nodes of the node on top of the stack.
	 */
	private void addChildToStack() {
		parentNode = (Node) stack.peek();
		parentNode.addChildNode(currentNode);
	}
	
	/**
	 * The method returns true if the type is valid.
	 * 
	 * @param type the type we are checking is correct or not
	 * @return <code>true</code> if the type is valid; <code>false</code> otherwise
	 */
	private boolean validateType(SmartScriptTokenType type) {
		if(type == SmartScriptTokenType.DOUBLE
				|| type == SmartScriptTokenType.INTEGER
				|| type == SmartScriptTokenType.STRING
				|| type == SmartScriptTokenType.VARIABLE) {
			return true;
		} else {
			return false;
		}
	}
}
