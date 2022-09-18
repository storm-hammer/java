package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestContext {
	
	private OutputStream outputStream;
	private Charset charset = Charset.forName("UTF-8");
	public String encoding = "UTF-8", statusText = "OK", mimeType = "text/html";
	public Long contentLength = null;
	public int statusCode = 200;
	private Map<String,String> parameters, temporaryParameters, persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated = false;
	private IDispatcher dispatcher;
	
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, Map<String,String> persistentParameters,
			List<RCCookie> outputCookies) {
		if(outputStream == null) throw new IllegalArgumentException("Output stream must not be null");
		this.outputStream = outputStream;
		if(parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		if(persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if(outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
		this.temporaryParameters = new HashMap<>();
	}
	
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, Map<String,String> persistentParameters,
			List<RCCookie> outputCookies, Map<String,String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}
	
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public void addRCCookie(RCCookie cookie) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		outputCookies.add(cookie);
	}
	
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		this.encoding = encoding;
	}

	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		this.mimeType = mimeType;
	}
	
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		this.contentLength = contentLength;
	}

	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated, cannot change header properties");
		}
		this.statusCode = statusCode;
	}
	
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	public String getSessionID() {
		return "";
	}
	
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	public RequestContext write(byte[] data) throws IOException {
		if(headerGenerated) {
			outputStream.write(data);
		} else {
			generateHeader();
			outputStream.write(data);
		}
		outputStream.flush();
		return this;
	}
	
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(headerGenerated) {
			outputStream.write(data, offset, len);
		} else {
			generateHeader();
			outputStream.write(data, offset, len);
		}
		outputStream.flush();
		return this;
	}
	
	public RequestContext write(String text) throws IOException {
		byte[] data = text.getBytes(charset);
		if(headerGenerated) {
			outputStream.write(data);
		} else {
			generateHeader();
			outputStream.write(data);
		}
		outputStream.flush();
		return this;
	}
	
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 "+statusCode+" "+statusText+"\r\n");
		sb.append("Content-Type: "+(!mimeType.startsWith("text/") ? mimeType : mimeType+"; charset="+encoding)+"\r\n");
		if(contentLength != null) sb.append("Content-Length: "+contentLength+"\r\n");
		if(outputCookies.size() != 0) {
			for(RCCookie cookie : outputCookies) {
				sb.append("Set cookie: "+cookie.getName()+"=\""+cookie.getValue()+"\"");
				if(cookie.getDomain() != null) {
					sb.append("; Domain="+cookie.getDomain());
				}
				if(cookie.getPath() != null) {
					sb.append("; Path="+cookie.getPath());
				}
				if(cookie.getMaxAge() != null) {
					sb.append("; Max-Age="+cookie.getMaxAge());
				}
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		System.out.print("header is: "+sb.toString());
		outputStream.write(sb.toString().getBytes(StandardCharsets.US_ASCII));//ISO_8859_1
		headerGenerated = true;
	}
	
	public static class RCCookie {
		
		private String name, value, domain, path;
		private Integer maxAge;
		
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return value;
		}
		public String getDomain() {
			return domain;
		}
		public String getPath() {
			return path;
		}
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
