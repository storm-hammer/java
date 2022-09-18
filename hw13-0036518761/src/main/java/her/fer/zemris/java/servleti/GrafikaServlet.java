package her.fer.zemris.java.servleti;

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

import hr.fer.zemris.java.dao.DAOProvider;

@WebServlet(urlPatterns = "/servleti/glasanje-grafika")
public class GrafikaServlet extends HttpServlet {

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
		
		DAOProvider.getDao().dohvatiAnketu();
		
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
