package hr.fer.zemris.java.gui.calc;

import javax.swing.JLabel;

public class Screen extends JLabel implements CalcValueListener {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void valueChanged(CalcModel model) {
		this.setText(model.toString());
	}
}
