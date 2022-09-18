package hr.fer.oprpp1.hw04.db;

import java.util.List;

@SuppressWarnings("unused")
public class QueryParser {
	
	private String command;
	private List<ConditionalExpression> expressions;
	
	public QueryParser(String command) {
		
		if(!command.startsWith("query"))
			throw new IllegalArgumentException("Unknown command!");
		
		this.command = command.substring(5);
	}
}
