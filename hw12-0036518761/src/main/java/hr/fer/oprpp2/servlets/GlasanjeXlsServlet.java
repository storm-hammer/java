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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet(urlPatterns = "/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> rez = Files.readAllLines(Paths.get(fileName));
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Id benda");
		rowhead.createCell(1).setCellValue("Broj glasova");
		
		for(int i = 1; i <= rez.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			String[] dijelovi = rez.get(i-1).split("\t");
			row.createCell(0).setCellValue(dijelovi[0]);
			row.createCell(1).setCellValue(dijelovi[1]);
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}