package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class SierpinskiGasket {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createPlant1(LSystemBuilderImpl::new));
	}
	
	private static LSystem createPlant1(LSystemBuilderProvider provider) {
		String[] data = new String[] {
				"origin                 0.15 0.6",
				"angle                  0",
				"unitLength             0.5",
				"unitLengthDegreeScaler 1.0 / 2.2",
				"",
				"command R draw 1",
				"command L draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom R",
				"",
				"production R L-R-L",
				"production L R+L+R"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
		}
}
