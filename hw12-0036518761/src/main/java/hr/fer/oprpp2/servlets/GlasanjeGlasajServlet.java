package hr.fer.oprpp2.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File glasanje = new File(fileName);
		if(!glasanje.exists()) {
			glasanje.createNewFile();
		}
		int id = Integer.parseInt(req.getParameter("id"));
		List<String> rezultati = Files.readAllLines(Paths.get(fileName));
		
		for(int i = 0; i < rezultati.size(); i++) {
			String s = rezultati.get(i);
			if(s.contains(String.valueOf(id))) {
				String[] idGlasovi = s.split("\t");
				String newValue = idGlasovi[0] + "\t" + (Integer.parseInt(idGlasovi[1]) + 1);
				int index = rezultati.indexOf(s);
				rezultati.remove(index);
				rezultati.add(index, newValue);
			}
		}
		Files.write(Paths.get(fileName), rezultati);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
