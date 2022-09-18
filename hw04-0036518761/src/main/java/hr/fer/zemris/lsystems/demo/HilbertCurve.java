package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class HilbertCurve {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createHilbertCurve(LSystemBuilderImpl::new));
	}
	
	private static LSystem createHilbertCurve(LSystemBuilderProvider provider) {
		String[] data = new String[] {
				"origin                 0.05 0.05",
				"angle                  0",
				"unitLength             0.9",
				"unitLengthDegreeScaler 1.0 / 2.0",
				"command F draw 1",
				"command + rotate 90",
				"command - rotate -90",
				"",
				"axiom L",
				"",
				"production L +RF-LFL-FR+",
				"production R -LF+RFR+FL-"
				};
				return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
