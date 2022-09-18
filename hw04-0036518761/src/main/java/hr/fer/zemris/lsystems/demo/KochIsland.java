package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class KochIsland {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKoch2(LSystemBuilderImpl::new));
	}
	
	private static LSystem createKoch2(LSystemBuilderProvider provider) {
		String[] data = new String[] {"origin                 0.3 0.75",
				"angle                  0",
				"unitLength             0.45",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 90",
				"command - rotate -90",
				"",
				"axiom F-F-F-F",
				"",
				"production F FF-F-F-F-FF",
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
