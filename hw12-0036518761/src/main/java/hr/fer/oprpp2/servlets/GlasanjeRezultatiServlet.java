package hr.fer.oprpp2.servlets;

import java.io.File;
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

@WebServlet(urlPatterns = "/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File glasanje = new File(fileName);
		if(!glasanje.exists()) {
			glasanje.createNewFile();
		}
		
		List<String> rezultati = Files.readAllLines(Paths.get(fileName));
		List<String> idBend = Files.readAllLines(Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt")));
		List<Bend> bendovi = new ArrayList<>();
		
		for(int i = 0; i < rezultati.size(); i++) {
			String[] idGlasovi = rezultati.get(i).split("\t");
			String glasovi = idGlasovi[1];
			String name = null;
			for(int j = 0; j < idBend.size(); j++) {
				if(idBend.get(j).split("\t")[0].equals(idGlasovi[0])) {
					name = idBend.get(j).split("\t")[1];
				}
			}
			bendovi.add(new Bend(0, name, Integer.parseInt(glasovi)));
		}
		bendovi.sort((a, b) -> b.getGlasovi() - a.getGlasovi());
		req.setAttribute("bendovi", bendovi);
		
		String linkPrvi = null, linkDrugi = null;
		for(String s : idBend) {
			String[] dijelovi = s.split("\t");
			if(dijelovi[1].equals(bendovi.get(0).getName())) {
				linkPrvi = dijelovi[2];
			}
			if(dijelovi[1].equals(bendovi.get(1).getName())) {
				linkDrugi = dijelovi[2];
			}
		}
		req.setAttribute("prviBend", bendovi.get(0).getName());
		req.setAttribute("drugiBend", bendovi.get(1).getName());
		req.setAttribute("prviLink", linkPrvi);
		req.setAttribute("drugiLink", linkDrugi);
		
		req.getRequestDispatcher("glasanjeRez.jsp").forward(req, resp);

	}
}
