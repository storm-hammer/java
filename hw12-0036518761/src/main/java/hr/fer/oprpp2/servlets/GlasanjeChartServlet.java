package hr.fer.oprpp2.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet(urlPatterns = "/glasanje-grafika")
public class GlasanjeChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		JFreeChart chart = getChart(req);
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
	}

	private JFreeChart getChart(HttpServletRequest req) {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> rez = null;
		try {
			rez = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < rez.size(); i++) {
			String[] dijelovi = rez.get(i).split("\t");
			dataset.setValue(String.valueOf(dijelovi[0]), Integer.parseInt(dijelovi[1]));
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart3D("Glasanje za najdraži bend", dataset, legend, tooltips, urls);

		return chart;	
	}
}
