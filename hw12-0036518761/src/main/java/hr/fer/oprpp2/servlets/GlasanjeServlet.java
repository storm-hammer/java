package hr.fer.oprpp2.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.data.Bend;

@WebServlet(urlPatterns = "/glasanje")
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<Bend> bendovi = new ArrayList<>();
		Files.readAllLines(Paths.get(fileName))
			 .stream()
			 .forEach((s) -> bendovi.add(new Bend(Integer.parseInt(s.split("\t")[0]), s.split("\t")[1], 0)));
		
		req.setAttribute("bendovi", bendovi);
		req.getRequestDispatcher("glasanjeIndex.jsp").forward(req, resp);
	}
}
