package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
	
	private boolean editable, isNegative;
	private String digits, frozenValue;;
	private double value, activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners;
	
	public CalcModelImpl() {
		editable = true;
		digits = new String();
		listeners = new ArrayList<CalcValueListener>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("Observer cannot be null!");
		}
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("Observer cannot be null!");
		}
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return isNegative ? value * -1 : value;//mistake myb here?
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		digits = String.valueOf(value);//actually here probably bcz minus goes through valueOf
		editable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		value = 0;
		digits = new String();
		editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = 0;
		pendingOperation = null;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable()) {
			throw new CalculatorInputException("The model is not editable!");
		}
		frozenValue = null;
		isNegative = isNegative ? false : true;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable()) {
			throw new CalculatorInputException("The model is not editable!");
		}
		frozenValue = null;
		if(digits.length() == 0) {
			throw new CalculatorInputException("Cannot put decimal point before any numbers!");
		}
		if(!digits.contains(".")) {
			digits += ".";
			notifyListeners();
		} else {
			throw new CalculatorInputException("Decimal point already present!");
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) {
			throw new CalculatorInputException("The model is not editable!");
		}
		frozenValue = null;
		
		if(!(digits.equals("0") && digit == 0)) {
			value = Double.parseDouble(digits + digit);
			if(value != Double.NEGATIVE_INFINITY && value != Double.POSITIVE_INFINITY && value != Double.NaN) {
				digits += digit;
				notifyListeners();
			} else {
				throw new CalculatorInputException("Invalid number format");
			}
		}//provj leading zeroes
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != 0;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == 0) {
			throw new IllegalStateException("Active operand not set!");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}
	
	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return frozenValue;
		} else {
			String sign = isNegative ? "-" : "";
			if(digits.length() == 0) {
				return sign + "0";
			} else {
				return sign + Double.parseDouble(digits);
			}
		}
	}
	
	private void notifyListeners() {
		for(CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
}
