package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private String path;
	
	public BarChartDemo(String path, BarChart chart) {
		this.path = path;
		this.chart = chart;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		initGUI();
		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel label = new JLabel(path);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(label , BorderLayout.NORTH);
		
		JComponent chartComponent = new BarChartComponent(chart);
		chartComponent.setOpaque(true);
		chartComponent.setVisible(true);
		cp.add(chartComponent, BorderLayout.CENTER);
		cp.setBackground(Color.WHITE);
	}

	public static void main(String[] args) throws IOException {
		
		Path p = Paths.get(args[0]);
		List<String> params = Files.readAllLines(p);
		
		List<XYValue> list = new ArrayList<>();
		for(String s : params.get(2).split("\\s+")) {
			String[] xy = s.split(",");
			list.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		int minY = Integer.parseInt(params.get(3));
		int maxY = Integer.parseInt(params.get(4));
		int x = Integer.parseInt(params.get(5));
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(args[0], new BarChart(list, params.get(1), params.get(0), minY, maxY, x));
		});
	}	
}
