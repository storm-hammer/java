package hr.fer.zemris.lsystems.demo;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;

public class Demo {
	
	public static void main(String[] args) {
		
		final IComparisonOperator oper = ComparisonOperators.LIKE;
		
		System.out.println(oper.satisfied("Zagreb", "Za*b"));
	}
}
