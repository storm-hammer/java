package hr.fer.oprpp2.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.data.Vrijednosti;

@WebServlet(urlPatterns = "/trigonometric")
public class TrigonometryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] params = req.getQueryString().split("[&]");
		int first, last;
		if(params.length < 2) {//ako je samo jedan parametar poslan
			if(params[0].contains("a")) {//ako je to a
				first = Integer.parseInt(params[0].split("[=]")[1]);
				last = 360;
			} else {//ako je to b
				first = 0;
				last = Integer.parseInt(params[0].split("[=]")[1]);
			}
		} else {//ako su oba poslana
			first = Integer.parseInt(params[0].split("[=]")[1]);
			last = Integer.parseInt(params[1].split("[=]")[1]);
		}
		
		if(first > last) {
			int temp = first;
			first = last;
			last = temp;
		}
		if(last > first + 720) {
			last = first + 720;
		}
		
		List<Vrijednosti> lista = new ArrayList<>();
		
		for(int i = first; i <= last; i++) {
			lista.add(new Vrijednosti(i));
		}
		
		req.setAttribute("list", lista);
		req.getRequestDispatcher("trigonometric.jsp").forward(req, resp);
	}	
		
}
