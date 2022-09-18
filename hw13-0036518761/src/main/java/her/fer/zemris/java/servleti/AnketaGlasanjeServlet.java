package her.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Anketa;
import hr.fer.zemris.java.model.AnketaOpcija;

@WebServlet(urlPatterns = "/servleti/glasanje/*")
public class AnketaGlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.valueOf(req.getParameter("pollId"));
		Anketa a = DAOProvider.getDao().dohvatiAnketu(id);
		List<AnketaOpcija> o = DAOProvider.getDao().dohvatiOpcije(id);
		req.setAttribute("anketa", a);
		req.setAttribute("opcije", o);
		req.setAttribute("pollId", req.getParameter("pollId"));
		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
	}
}
