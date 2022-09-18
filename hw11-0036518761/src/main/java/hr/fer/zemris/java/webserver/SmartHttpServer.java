package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

@SuppressWarnings("unused")
public class SmartHttpServer {
	
	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String,IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	private Random sessionRandom = new Random();

	
	public SmartHttpServer(String configFileName) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File(configFileName)));
		} catch (IOException e) {
		}
		address = p.getProperty("server.address");
		domainName = p.getProperty("server.domainName");
		port = Integer.parseInt(p.getProperty("server.port"));
		workerThreads = Integer.parseInt(p.getProperty("server.workerThreads"));
		documentRoot = Paths.get(p.getProperty("server.documentRoot"));
		sessionTimeout = Integer.parseInt(p.getProperty("session.timeout"));
		initMimeTypes(p.getProperty("server.mimeConfig"));
		initWorkerMappings(p.getProperty("server.workers"));
		serverThread = new ServerThread();
		start();
	}
	
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer(args[0]);
	}
	
	
	private void initWorkerMappings(String name) {
		Properties workers = new Properties();
		try {
			workers.load(new FileInputStream(new File(name)));
		} catch (IOException e) {
		}
		for(Entry<Object, Object> e : workers.entrySet()) {
			if(e.getKey() instanceof String && e.getValue() instanceof String) {
				IWebWorker iww = getWorker((String)e.getValue());
				this.workersMap.put((String)e.getKey(), iww);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private IWebWorker getWorker(String value) {
		Class<?> referenceToClass = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(value);
		} catch (ClassNotFoundException e1) {
		}
		
		Object newObject = null;
		try {
			newObject = referenceToClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			System.out.println(e1.getMessage());
		}
		return (IWebWorker)newObject;
	}

	private void initMimeTypes(String name) {
		Properties mimeTypes = new Properties();
		try {
			mimeTypes.load(new FileInputStream(new File(name)));
		} catch (IOException e) {
		}
		for(Entry<Object, Object> e : mimeTypes.entrySet()) {
			if(e.getKey() instanceof String && e.getValue() instanceof String) {
				this.mimeTypes.put((String)e.getKey(), (String)e.getValue());
			}
		}
	}

	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if(!serverThread.isAlive()) {
			serverThread.start();
		}
	}
	
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	private static class SessionMapEntry {
		String sid;
		String host;
		long validUntil;
		Map<String,String> map;
	}
	
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			ServerSocket socket = null;
			try {
				socket = new ServerSocket();
				socket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while(true) {
					Socket client = socket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private class ClientWorker implements Runnable, IDispatcher {
		
		private Socket csocket;
		private InputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String,String> params = new HashMap<String, String>();
		private Map<String,String> tempParams = new HashMap<String, String>();
		private Map<String,String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		private RequestContext myContext = null;
		
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				
				Optional<byte[]> request = readRequest(istream);
				if(request.isEmpty()) {
					return;
				}
				
				String requestStr = new String(request.get(), StandardCharsets.US_ASCII);
				List<String> headers = extractHeaders(requestStr);
				
				if(headers.size() < 1) {
					sendEmptyResponse(400, "Bad request");
					return;
				}

				String[] dijelovi = headers.isEmpty() ? null : headers.get(0).split(" ");
				String requestedPath = dijelovi[1];
				
				method = dijelovi[0].toUpperCase();				
				if(!method.equals("GET")) {
					sendEmptyResponse(405, "Method Not Allowed");
					return;
				}

				version = dijelovi[2].toUpperCase();
				if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendEmptyResponse(505, "HTTP Version Not Supported");
					return;
				}
				
				boolean hostSet = false;
				for(String headerLine : headers) {
					if(headerLine.startsWith("Host: ")) {
						String newHost = headerLine.substring(6).trim();
						if(newHost.contains(":")) {
							host = newHost.split(":")[0];
						} else {
							host = newHost;
						}
						hostSet = true;
					}
				}
				if(!hostSet) {
					host = domainName;
				}
				
				String path = null; String paramString = null;
				
				if(requestedPath.contains("?")) {
					String[] pathAndParams = requestedPath.split("[?]");
					path = pathAndParams[0];
					paramString = pathAndParams[1];
				} else {
					path = requestedPath;
				}
				
				fillParameters(params, paramString);
				
				try {
					internalDispatchRequest(path, true);
				} catch(Exception e) {
					System.out.println("Dogodila se pogreška: "+e.getMessage());
				}
				
			} catch (IOException e) {
				System.out.println("Dogodila se pogreška: "+e.getMessage());
			}
		}
		
		public void dispatchRequest(String urlPath) throws Exception {
			 internalDispatchRequest(urlPath, false);
		}
		
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			
			Path documentRoot = Paths.get("./webroot").toAbsolutePath().normalize();
			Path reqPath = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath().normalize();
			if(!reqPath.toString().startsWith(documentRoot.toString())) {
				sendEmptyResponse(403, "Forbidden");
				return;
			}
			
			RequestContext rc;
			if(myContext != null) {
				rc = myContext;
			} else {
				myContext = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				rc = myContext;
			}
			
			if(urlPath.equals("/private") || urlPath.startsWith("/private/")) {
				if(directCall) {
					sendEmptyResponse(404, "Not Found");
					return;
				}
			}

			if(urlPath.startsWith("/ext/")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers.";
				fqcn += urlPath.substring(urlPath.lastIndexOf("/")+1);
				IWebWorker iww = getWorker(fqcn);
				iww.processRequest(rc);
				return;
			}
			
			IWebWorker requestedWorker = workersMap.get(urlPath);
			if(requestedWorker != null) {
				rc.setStatusCode(200);
				rc.setStatusText("OK");
				requestedWorker.processRequest(rc);
				return;
			}
			
			if(!Files.isReadable(reqPath) || !Files.exists(reqPath) || !Files.isRegularFile(reqPath)) {
				sendEmptyResponse(404, "File not found");
				return;
			}
			
			String ext = extractExtension(reqPath.getFileName().toString());
			if(ext.equals("smscr")) {
				String docBody = Files.readString(reqPath);
				SmartScriptParser parser = new SmartScriptParser(docBody);
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), rc);
				rc.setStatusCode(200);//ovo sve bi trebalo biti prije execute prije writea
				rc.setStatusText("OK");//kad se header pravi, i kad se flush poziva?
				rc.setMimeType("text/html");
				engine.execute();
			} else {
				String mt = determineMimeType(ext);
				byte[] okteti = Files.readAllBytes(reqPath);
				sendResponseWithData(200, "OK", mt, okteti);
			}
		}

		private void sendResponseWithData(int statusCode, String statusText, String contentType, byte[] data) throws IOException {
			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setMimeType(contentType);
			rc.setStatusCode(200);
			rc.setStatusText(statusText);
			if(data.length != 0) {
				rc.setContentLength((long)data.length);
			}
			rc.write(data);
		}
		
		private void sendEmptyResponse(int statusCode, String statusText) throws IOException {
			sendResponseWithData(statusCode, statusText, "text/plain", new byte[0]);
		}
		
		private String extractExtension(String string) {
			int p = string.lastIndexOf(".");
			if(p<1) return "";
			return string.substring(p+1).toLowerCase();
		}
		
		private String determineMimeType(String ekstenzija) {
			String mime = mimeTypes.get(ekstenzija);
			return mime == null ? "application/octet-stream" : mime;
		}
		
		private void fillParameters(Map<String, String> parameterMap, String query) {
			if(query == null) {
				return;
			}
			String[] parovi = query.split("[&]");
			for(String par : parovi) {
				String[] dijelovi = par.split("[=]", 2);
				if(dijelovi.length == 2) {
					parameterMap.put(dijelovi[0], dijelovi[1]);
				} else {
					parameterMap.put(dijelovi[0], null);				
				}
			}
		}
		
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		private Optional<byte[]> readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = is.read();
				if(b==-1) {
					if(bos.size()!=0) {
						throw new IOException("Incomplete header received.");
					}
					return Optional.empty();
				}
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return Optional.of(bos.toByteArray());
		}
	}
}
