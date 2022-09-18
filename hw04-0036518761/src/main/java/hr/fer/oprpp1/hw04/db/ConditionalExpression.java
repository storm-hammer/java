package hr.fer.oprpp1.hw04.db;

/**
 * The class is an implementation of a conditional expression.
 * 
 * @author Mislav Prce
 */
public class ConditionalExpression {
	
	final IComparisonOperator oper;
	final IFieldValueGetters getter;
	String literal;
	
	/**
	 * The constructor instances an object with the given values.
	 * 
	 * @param oper the comparison operator 
	 * @param getter the field value getter
	 * @param literal the string literal 
	 */
	public ConditionalExpression(IComparisonOperator oper, IFieldValueGetters getter, String literal) {
		this.oper = oper;
		this.getter = getter;
		this.literal = literal;
	}

	public IComparisonOperator getOper() {
		return oper;
	}

	public IFieldValueGetters getGetter() {
		return getter;
	}

	public String getLiteral() {
		return literal;
	}
	
	
}
