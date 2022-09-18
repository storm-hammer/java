package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * The class demonstrates how the <code>ObjectStack</code> class can be used.
 * 
 * @author Mislav Prce
 */
public class StackDemo {
	
	/**
	 * The method demonstrates how we can use a stack to solve problems.
	 * 
	 * @param args expression in postfix notation which needs to be processed
	 * @throws <code>IllegalArgumentException</code> if the expression was not valid
	 */
	public static void main(String[] args) {
		
		String[] expression = args[0].split("\\s+");
		
		ObjectStack stack = new ObjectStack();
		
		for(int i = 0; i < expression.length; i++) {
			
			if(isNumeric(expression[i])) {
				stack.push(expression[i]);
			} else {
				int a = Integer.parseInt((String) stack.pop());
				int b = Integer.parseInt((String) stack.pop());
				
				stack.push(performOperation(expression[i], a, b));
			}
		}
		
		if(stack.size() != 1) {
			throw new IllegalArgumentException();
		} else {
			System.out.println(stack.pop());
		}
	}
	
	/**
	 * The method checks if a string is a representation of an integer.
	 * 
	 * @param arg the string that is tested if it is an integer or not
	 * @return <code>true</code> if the given argument is a number;
	 * <code>false</code> otherwise
	 */
	public static boolean isNumeric(String arg) {
		
		try {
			Integer.parseInt(arg);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	/**
	 * The method performs an operation on the two given integer parameters.
	 * 
	 * @param arg the string which represents an operator
	 * @param a the first operand
	 * @param b the second operand
	 * @return the result of the operation
	 * @throws <code>Error</code> if division by zero is attempted
	 * @throws <code>IllegalArgumentException</code> if an invalid 
	 * operator is received as an argument
	 */
	public static int performOperation(String arg, int a, int b) {
		
		switch (arg) {
		
			case "+":
				return a + b;
				
			case "-":
				return b - a;
				
			case "/":
				if(a == 0) {
					throw new Error("Division by zero was attempted.");
				} else {
					return b / a;
				}
				
			case "*":
				return a * b;
				
			case "%":
				if(b == 0) {
					throw new Error("Division by zero was attempted");
				} else {
					return b % a;	
				}
				
			default:
				throw new IllegalArgumentException("This operator is not allowed");
		}
	}
}
