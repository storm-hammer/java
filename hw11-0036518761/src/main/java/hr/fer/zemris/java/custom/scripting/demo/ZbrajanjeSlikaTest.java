package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class ZbrajanjeSlikaTest {
	
	public static void main(String[] args) {
		String documentBody = null;
		try {
			documentBody = Files.readString(Paths.get("webroot\\private\\pages\\calc.smscr"));
		} catch (IOException e) {
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		Map<String,String> tparameters = new HashMap<String, String>();
		//tparameters.put("varA", "4");
		tparameters.put("varB", "2");
		tparameters.put("zbroj", "3");
		tparameters.put("imgName", "C:\\javaproj2\\hw03-0036518761\\webroot\\images\\amongUs.png");
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies, tparameters, null)
		).execute();
	}
}
