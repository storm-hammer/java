package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class Token {
	
	private Object value;
	private SmartScriptTokenType type;
	
	public Token(SmartScriptTokenType type, Object value) {
		if(type == null) {
			throw new SmartScriptParserException("Token type cannot be null!");
		}
		this.value = value;
		this.type = type;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public SmartScriptTokenType getType() {
		return this.type;
	}
}
