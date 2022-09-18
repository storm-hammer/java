package hr.fer.zemris.java.webserver.workers;

import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" +
				"  <head>\r\n" + 
				"    <title>Ispis parametara</title>\r\n"+
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <table border='1'>\r\n");
		
		sb.append("      <tr><td>")
		.append("Naziv parametra")
		.append("</td><td>")
		.append("Vrijednost parametra");
		
			for(String paramName : context.getParameterNames()) {
				sb.append("      <tr><td>")
					.append(paramName)
					.append("</td><td>")
					.append(context.getParameter(paramName))
					.append("</td></tr>\r\n");
			}
			sb.append(
				"    </table>\r\n" + 
				"  </body>\r\n" + 
				"</html>\r\n");
			byte[] tijeloOdgovora = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
			context.setEncoding("ISO_8859_1");
			context.setMimeType("text/html");
			context.setContentLength((long)tijeloOdgovora.length);
			context.setStatusCode(200);
			context.setStatusText("OK");
			context.write(tijeloOdgovora);
			
	}
}
