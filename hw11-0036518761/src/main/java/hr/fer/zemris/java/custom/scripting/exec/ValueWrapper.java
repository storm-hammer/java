package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

public class ValueWrapper {
	
	private Object value;
	
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public void add(Object incValue) {
		calculate(incValue, (a, b) -> a + b, (a, b) -> a + b);
	}

	public void subtract(Object decValue) {
		calculate(decValue, (a, b) -> a - b, (a, b) -> a - b);
	}
	
	public void multiply(Object mulValue) {
		calculate(mulValue, (a, b) -> a * b, (a, b) -> a * b);
	}
	
	public void divide(Object divValue) {
		calculate(divValue, (a, b) -> a / b, (a, b) -> a / b);
	}
	
	public int numCompare(Object withValue) {
		if(this.value == null && withValue == null) {
			return 0;
		}
		if(this.value == null && withValue != null) {
			return parseNumber(withValue).intValue();
		}//the value of the argument determines if it is bigger
		if(this.value != null && withValue == null) {
			return parseNumber(this.value).intValue();
		}//same logic
		
		return parseNumber(this.value).intValue() - parseNumber(withValue).intValue();
	}
	
	private void calculate(Object other, BinaryOperator<Integer> integers, BinaryOperator<Double> doubles) {
		Number a = parseNumber(this.value), b = parseNumber(other);
		if(a instanceof Double || b instanceof Double) {
			this.value = doubles.apply(a.doubleValue(), b.doubleValue());
		} else {
			this.value = integers.apply(a.intValue(), b.intValue());
		}
	}
	
	private Number parseNumber(Object number) {
		if(number instanceof ValueWrapper) {
			ValueWrapper v = (ValueWrapper)number;
			return v.parseNumber(v.getValue());
		}
		if(number == null) {
			return Integer.valueOf(0);
		}
		if(number instanceof String) {
			String numberString = (String)number;
			if(numberString.contains(".") || numberString.contains("E")) {
				return Double.parseDouble(numberString);
			} else {
				return Integer.parseInt(numberString);
			}
		}
		if(number instanceof Double) {
			return (Double)number;
		}
		if(number instanceof Integer) {
			return (Integer)number;
		}
		throw new RuntimeException("Illegal number format");
	}

}
