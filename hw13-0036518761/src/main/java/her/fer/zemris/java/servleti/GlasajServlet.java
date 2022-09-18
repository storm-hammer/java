package her.fer.zemris.java.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOProvider;

@WebServlet(urlPatterns = "/servleti/glasanje-glasaj/*")
public class GlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.valueOf(req.getParameter("id"));
		long pollId = Long.valueOf(req.getParameter("pollId"));
		DAO dao = DAOProvider.getDao();
		dao.dodajGlas(id);
		req.setAttribute("anketa", dao.dohvatiAnketu(pollId));
		req.setAttribute("opcije", dao.dohvatiOpcije(pollId));
		req.getRequestDispatcher("/servleti/").forward(req, resp);
		//CONNECT 'jdbc:derby://localhost:1527/votingDB;user=ivica;password=ivo';
	}
}
