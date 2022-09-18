package hr.fer.oprpp2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet(urlPatterns = "/powers")
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null, b = null, n = null;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
			if((a < -100 || a > 100) || (b < -100 || b > 100) || (n < 1 || n > 5)) {
				throw new NumberFormatException();
			}
		} catch(NumberFormatException e) {
			req.getRequestDispatcher("invalidParams.jsp").forward(req, resp);
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		int diff = b - a;
		for(int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet number "+i);
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue("Number to the power of "+i);
			for(int j = a; j <= diff; j++) {
				HSSFRow row = sheet.createRow(j-a+1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
