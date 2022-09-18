package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	
	private char[] data;
	private Token currentToken;
	private int currentIndex;
	private LexerState currentState;

	public Lexer(String text) {
		
		if(text == null) {
			throw new NullPointerException("A constructor of this class must receive a non-null reference.");
		}
		
		this.currentIndex = 0;
		this.data = text.toCharArray();
		this.currentState = LexerState.BASIC;
	}

	public Token nextToken() {
		
		//if we had a token and its type was EOF we have reached the end of the file
		if(currentToken != null && currentToken.getType() == TokenType.EOF) {
			throw new LexerException("No tokens remaining");
		}
		
		//reaching the start of the next token (could be EOF)
		skipBlanks();
		
		//reaching the end of the file
		if(currentIndex >= data.length) {
			currentToken = new Token(TokenType.EOF, null);
			return getToken();
		}
		
		StringBuilder sb = new StringBuilder();
		boolean wasWord = false;
		
		if(currentState == LexerState.EXTENDED) {
			if(data[currentIndex] == '#') {
				currentToken = new Token(TokenType.SYMBOL, '#');
				currentIndex++;
				return getToken();
			}
			char c;
			while(currentIndex < data.length) {
				c = data[currentIndex];

				if(c != '\r' && c != ' ' && c != '\n' && c != '\t' && c != '#') {
					appendCharacterAndAdvance(sb);
				} else {
					break;
				}
			}
			currentToken = new Token(TokenType.WORD, sb.toString());
			return getToken();
		}
		
		//checking if the next token is a word and constructing it if it's a word
		if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			wasWord = true;
			while (currentIndex < data.length) {
				
				if(data[currentIndex] == '\\') {
					
					currentIndex++;
					if(currentIndex >= data.length) {
						throw new LexerException("Escape cannot be the last character");
					}
					if(data[currentIndex] != '\\' && !Character.isDigit(data[currentIndex])) {
						throw new LexerException("Escape cannot be followed by a letter or symbol");
					}
					appendCharacterAndAdvance(sb);
					
				} else if(Character.isLetter(data[currentIndex])){
					appendCharacterAndAdvance(sb);
					
				} else {
					break;//the next symbol is not a part of the current token
				}
			}
		}
		
		if(wasWord) {
			currentToken = new Token(TokenType.WORD, sb.toString());
			return getToken();
		}
		
		boolean wasNumber = false;
		//checking if the next token is a number and constructing it if it's a number
		if(Character.isDigit(data[currentIndex])) {
			wasNumber = true;
			while(currentIndex < data.length) {
				if(Character.isDigit(data[currentIndex])) {
					appendCharacterAndAdvance(sb);
				} else {
					break;
				}
			}
		}
		
		if(wasNumber) {
			Long number;
			try {
				number = Long.parseLong(sb.toString());
			} catch (NumberFormatException e) {
				throw new LexerException("Number was too big");
			}
			currentToken = new Token(TokenType.NUMBER, number);
			return getToken();
		}
		
		//if these lines execute that means the token must be a symbol (it wasn't a WORD, NUMBER or EOF type)
		currentToken =  new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
		currentIndex++;
		return getToken();
		
	}
	
	private void appendCharacterAndAdvance(StringBuilder sb) {
		sb.append(data[currentIndex]);
		currentIndex++;
	}

	public Token getToken() {
		return currentToken;
	}
	
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("The state cannot be null");
		}
		this.currentState = state;
	}
	
	private boolean isBlank(char c) {
		return c==' ' || c=='\t' || c=='\r' || c=='\n';
	}
	
	private void skipBlanks() {
		
		while(currentIndex < data.length) {
			if(isBlank(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
