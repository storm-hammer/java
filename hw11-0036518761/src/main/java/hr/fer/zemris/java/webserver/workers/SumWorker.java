package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a, b;
		String aParam = context.getParameter("a"), bParam = context.getParameter("b");
		try {
			if(aParam != null) {
				a = Integer.parseInt(aParam);
			} else {
				a = 1;
			}
		} catch (NumberFormatException e) {
			a = 1;
		}
		try {
			if(bParam != null) {
				b = Integer.parseInt(bParam);
			} else {
				b = 2;
			}
		} catch (NumberFormatException e) {
			b = 2;
		}
		
		int sum = a+b;
		context.setTemporaryParameter("zbroj", String.valueOf(sum));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		//C:\\javaproj2\\hw03-0036518761\\webroot\\
		String amongUs = "images\\amongUs.png";
		String dota = "images\\dota.png";
		context.setTemporaryParameter("imgName", sum % 2 == 0 ? amongUs : dota);
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
}
