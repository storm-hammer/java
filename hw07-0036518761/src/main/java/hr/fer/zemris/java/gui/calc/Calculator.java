package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * 
 * @author User
 *
 */
public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private CalcModelImpl calculator;
	private Stack<Double> stack;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
	
	/**
	 * 
	 */
	public Calculator() {
		super("My calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		calculator = new CalcModelImpl();
		stack = new Stack<Double>();
		initGUI();
		setSize(600, 400);
	}
	
	/**
	 * 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		cp.add(number(0), new RCPosition(5, 3));
		cp.add(number(1), new RCPosition(4, 3));
		cp.add(number(2), new RCPosition(4, 4));
		cp.add(number(3), new RCPosition(4, 5));
		cp.add(number(4), new RCPosition(3, 3));
		cp.add(number(5), new RCPosition(3, 4));
		cp.add(number(6), new RCPosition(3, 5));
		cp.add(number(7), new RCPosition(2, 3));
		cp.add(number(8), new RCPosition(2, 4));
		cp.add(number(9), new RCPosition(2, 5));
		
		cp.add(screen(), new RCPosition(1, 1));
		
		cp.add(button((e) -> calculator.swapSign(), "+/-"), new RCPosition(5, 4));
		cp.add(button((e) -> calculator.insertDecimalPoint(), "."), new RCPosition(5, 5));
		cp.add(button((e) -> calculator.clear(), "clr") , new RCPosition(1, 7));
		cp.add(button((e) -> calculator.clearAll(), "reset"), new RCPosition(2, 7));
		cp.add(button((e) -> stack.push(calculator.getValue()), "push"), new RCPosition(3, 7));
		cp.add(button((e) -> stack.pop(), "pop"), new RCPosition(4, 7));
		cp.add(button((e) -> calculator.setValue(calculator.getPendingBinaryOperation().applyAsDouble(
				calculator.getActiveOperand(), calculator.getValue())), "="), new RCPosition(1, 6));
		
		cp.add(binary((l, r) -> l / r, "/"), new RCPosition(2, 6));
		cp.add(binary((l, r) -> l * r,  "*"), new RCPosition(3, 6));
		cp.add(binary((l, r) -> l - r, "-"), new RCPosition(4, 6));
		cp.add(binary((l, r) -> l + r, "+"), new RCPosition(5, 6));
		
		JCheckBox checkBox = checkBox(cp);
		cp.add(checkBox, new RCPosition(5, 7));
		
		cp.add(unary((d) -> 1.0 / d, (d) -> 1.0 / d, "1/x", "1/x", checkBox), new RCPosition(2, 1));
		cp.add(unary((d) -> Math.log10(d), (d) -> Math.pow(10, d), "log", "10^x", checkBox), new RCPosition(3, 1));
		cp.add(unary((d) -> Math.log(d), (d) -> Math.pow(Math.E, d), "ln", "e^x", checkBox), new RCPosition(4, 1));
		cp.add(unary((d) -> Math.sin(d), (d) -> Math.asin(d), "sin", "arcsin", checkBox), new RCPosition(2, 2));
		cp.add(unary((d) -> Math.cos(d), (d) -> Math.acos(d), "cos", "arccos", checkBox), new RCPosition(3, 2));
		cp.add(unary((d) -> Math.tan(d), (d) -> Math.atan(d), "tan", "arctan", checkBox), new RCPosition(4, 2));
		cp.add(unary((d) -> (1.0 / Math.tan(d)) , (d) -> Math.atan(1.0 / (1.0 / Math.tan(d))), "ctg", "arcctg", checkBox), new RCPosition(5, 2));
		cp.add(unary((d) -> d, (d) -> d, "x^n", "x^(1/n)", checkBox), new RCPosition(5, 1));
	}
	
	/**
	 * 
	 * @param cp
	 * @return
	 */
	private JCheckBox checkBox(Container cp) {
		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.addActionListener((e) -> {
			for(Component c : cp.getComponents()) {
				if(c instanceof UnaryOperationButton) {
					((UnaryOperationButton) c).switchText();
				}
			}
		});
		return checkBox;
	}

	/**
	 * 
	 * @return
	 */
	private Screen screen() {
		Screen screen = new Screen();
		calculator.addCalcValueListener(screen);
		screen.setFont(screen.getFont().deriveFont(30f));
		screen.setEnabled(false);
		screen.setOpaque(true);
		screen.setBackground(Color.YELLOW);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		return screen;
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	private JButton number(int number) {
		JButton button = new JButton(String.valueOf(number));
		button.setFont(button.getFont().deriveFont(30f));
		button.addActionListener((e) -> calculator.insertDigit(number));
		return button;
	}
	
	/**
	 * 
	 * @param regular
	 * @param inverse
	 * @param regText
	 * @param invText
	 * @param checkBox
	 * @return
	 */
	private UnaryOperationButton unary(Function<Double, Double> regular, Function<Double, Double> inverse,
			String regText, String invText, JCheckBox checkBox) {
		UnaryOperationButton button = new UnaryOperationButton(regular, inverse, regText, invText);
		button.addActionListener((e) -> {
			double value = calculator.getValue();
			double res = checkBox.isSelected() ? inverse.apply(value) : regular.apply(value);
			calculator.setValue(res);
		});
		return button;
	}
	
	/**
	 * 
	 * @param oper
	 * @return
	 */
	private JButton binary(DoubleBinaryOperator operation, String oper) {
		JButton button = new JButton(oper);
		button.addActionListener((e) -> { 
			calculator.setActiveOperand(calculator.getValue());
			calculator.clear();
			calculator.setPendingBinaryOperation(operation);
		});
		return button;
	}
	
	/**
	 * 
	 * @param l
	 * @param text
	 * @return
	 */
	private JButton button(ActionListener l, String text) {
		JButton button = new JButton(text);
		button.addActionListener(l);
		return button;
	}
	
}
