package hr.fer.zemris.java.gui.layouts.generators;

import java.awt.Component;
import java.awt.Dimension;

import hr.fer.zemris.java.gui.layouts.Size;

public class MinimumSize implements Size {

	@Override
	public Dimension generate(Component c) {
		return c.getMinimumSize();
	}

}
