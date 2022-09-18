package hr.fer.zemris.java.custom.scripting.lexer;

public class SmartScriptLexer {
	
	private char[] data;
	private int currentIndex;
	private Token currentToken;
	private SmartScriptLexerState currentState;
	
	public SmartScriptLexer(String text) {
		if(text == null) {
			throw new NullPointerException("A constructor of this class must receive a non-null reference.");
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.currentState = SmartScriptLexerState.TEXT;
	}
	
	public Token getToken() {
		return this.currentToken;
	}
	
	public Token nextToken() {
		
		if(currentToken != null && currentToken.getType().equals(SmartScriptTokenType.EOF)) {
			throw new LexerException("No more tokens remaining!");
		}
		
		skipBlanks();
		
		if(currentIndex >= data.length) {
			currentToken = new Token(SmartScriptTokenType.EOF, null);
			return getToken();
		}

		StringBuilder sb = new StringBuilder();
		
		if(currentState == SmartScriptLexerState.TEXT) {
			extractTokenFromText(sb);
		} else {
			extractTokenFromTag(sb);
		}
		return getToken();
	}
	
	private void extractTokenFromText(StringBuilder sb) {
		
		char current = data[currentIndex];
		if(currentIndex == data.length - 1) {
			currentToken = new Token(SmartScriptTokenType.STRING, String.valueOf(current));
			return;
		}
		char next = data[currentIndex+1];
		
		if(current == '{' && next == '$') {
			currentToken = new Token(SmartScriptTokenType.TAG_START, null);
			currentIndex += 2;
			return;
		}

		while(currentIndex < data.length) {
			
			current = data[currentIndex];
			
			if(currentIndex < data.length - 1) {
				next = data[currentIndex+1];
			} else {
				break;
			}
			
			if(current == '\\') {
				
				currentIndex++;
				current = data[currentIndex];
				
				if(current == '{' || current == '\\') {
					appendCharacterAndAdvance(sb);
				} else {
					throw new LexerException("In a text node you can only escape '{' and '\\'");
				}
				
			} else {
				
				if(current == '{' && next == '$') {
					break;
				} else {
					appendCharacterAndAdvance(sb);
				}
			}
		}
		
		currentToken = new Token(SmartScriptTokenType.STRING, sb.toString());
		return;
	}
	
	private void extractTokenFromTag(StringBuilder sb) {
		
		char c = data[currentIndex];
		
		if(c == '=') {
			currentToken = new Token(SmartScriptTokenType.STRING, "=");
			currentIndex++;
			return;
		}
		
		if(Character.isLetter(c)) {
			String tagName = extractVariableName(sb);
			currentToken = new Token(SmartScriptTokenType.VARIABLE, tagName); 
			return;//kako znas da je TAG_NAME a ne obicna VARIABLE
		}
		
		if(c == '@') {
			appendCharacterAndAdvance(sb);
			String functionName = extractVariableName(sb);
			currentToken = new Token(SmartScriptTokenType.FUNCTION, functionName);
			return;
		}
		
		if(c == '^' || c == '/' || c == '*' || c == '+' || (c == '-' && !Character.isDigit(data[currentIndex+1]))) {
			currentToken = new Token(SmartScriptTokenType.OPERATOR, String.valueOf(c));
			currentIndex++;
			return;
		}
		
		if((c == '-' && Character.isDigit(data[currentIndex+1]) || Character.isDigit(c))) {
			
			String number = extractNumber(sb);
			
			try {
				if(number.contains(".")) {
					currentToken = new Token(SmartScriptTokenType.DOUBLE, Double.parseDouble(number));
					return;
				} else {
					currentToken = new Token(SmartScriptTokenType.INTEGER, Integer.parseInt(number));
					return;
				}
			} catch (NumberFormatException ex) {
				throw new LexerException("Invalid number format!");
			}
		}
		
		if(c == '\"') {
			currentIndex++;
			String someText = extractString(sb);
			currentToken = new Token(SmartScriptTokenType.STRING, someText);
			return;
		}
		
		if(c == '$' && data[currentIndex+1] == '}') {
			currentIndex += 2;
			currentToken = new Token(SmartScriptTokenType.TAG_END, null);
			return;
		}
	}
	
	public void setSmartScriptLexerState(SmartScriptLexerState state) {
		this.currentState = state;
	}
	
	public SmartScriptLexerState getSmartScriptLexerState() {
		return this.currentState;
	}
	
	private String extractString(StringBuilder sb) {
		char c;
		while(currentIndex < data.length) {
			c = data[currentIndex];
			
			if(c == '\"') {
				currentIndex++;
				break;
			}
			if(c == '\\') {
				currentIndex++;
				c = data[currentIndex];
				if(c == 'r') {
					sb.append("\r");
					currentIndex++;
				} else if(c == 'n'){
					sb.append("\n");
					currentIndex++;
				} else if(c == '\\'){
					sb.append("\\");
					currentIndex++;
				} else if(c == '\"') {
					sb.append("\"");
					currentIndex++;
				} else {
					throw new LexerException("Invalid escape sequence in a string inside a tag");
				}
//				if(c == '\\' || c == '\"' || c == 'r' || c == 'n') {//old version
//					sb.append("\\"+c);
//					currentIndex++;
//				} else {
//					throw new LexerException("Invalid escape sequence in a string inside a tag");
//				}
			} else {
				appendCharacterAndAdvance(sb);
			}
		}
		return sb.toString();
	}
	
	private String extractNumber(StringBuilder sb) {
		appendCharacterAndAdvance(sb);
		char c;
		while(currentIndex < data.length) {
			c = data[currentIndex];
			if(Character.isDigit(c) || c == '.') {
				appendCharacterAndAdvance(sb);
			} else {
				break;
			}
		}
		return sb.toString();
	}
	
	private String extractVariableName(StringBuilder sb) {
	
		char c = data[currentIndex];
		
		if(Character.isLetter(c)) {
			appendCharacterAndAdvance(sb);
		} else {
			throw new LexerException("Illegal variable name!");
		}
		
		while(currentIndex < data.length) {
			c = data[currentIndex];
			
			if(isBlank(c)) {
				break;
			}
			
			if(Character.isLetter(c) || Character.isDigit(c) || c == '_') {
				appendCharacterAndAdvance(sb);
			} else {
				break;
			}
		}
		
		return sb.toString();
	}
	
	private void appendCharacterAndAdvance(StringBuilder sb) {
		sb.append(data[currentIndex]);
		currentIndex++;
	}
	
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(isBlank(c)) {
				currentIndex++;
				continue;
			} else {
				break;
			}
		}
	}
	
	private boolean isBlank(char c) {
		if(c == ' ' || c == '\n' || c == '\t' || c == '\r') {
			return true;
		}
		return false;
	}
}
