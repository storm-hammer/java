package hr.fer.zemris.java.gui.calc;

import java.util.function.Function;

import javax.swing.JButton;

@SuppressWarnings("unused")
public class UnaryOperationButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	private Function<Double, Double> regular, inverse;
	private String regularText, inverseText;
	
	/**
	 * 
	 * @param regular
	 * @param inverse
	 * @param regularText
	 * @param inverseText
	 */
	public UnaryOperationButton(Function<Double, Double> regular, Function<Double, Double> inverse, 
			String regularText, String inverseText) {
		super(regularText);
		this.regularText = regularText;
		this.inverseText = inverseText;
		this.regular = regular;
		this.inverse = inverse;
	}
	
	/**
	 * 
	 */
	public void switchText() {
		this.setText(this.getText().equals(regularText) ? inverseText : regularText);
	}
	
}
