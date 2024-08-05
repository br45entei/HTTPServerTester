/*******************************************************************************
 * 
 * Copyright © 2023 Brian Reid (br45entei@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 *******************************************************************************/
package com.gmail.br45entei.main;

import com.gmail.br45entei.http.server.MimeTypes;
import com.gmail.br45entei.server.Link;
import com.gmail.br45entei.ssl.TrustAnySSLCertificateSSLSocketFactory;
import com.gmail.br45entei.util.SWTUtil;
import com.gmail.br45entei.util.ThreadUtil;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.SocketFactory;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/** @author Brian Reid &lt;br45entei&#064;gmail.com&gt; */
public final class Main {
	
	protected static Main instance = null;
	
	/** @param str The string to check
	 * @return Whether or not the given string is a valid hexadecimal integer value */
	public static final boolean isHexInt(String str) {
		try {
			Integer.parseInt(str, 16);
			return true;
		} catch(NumberFormatException | NullPointerException ex) {
			return false;
		}
	}
	
	public static final String makePathFilesystemSafe(String s) {
		char escape = '%'; // ... or some other legal char.
		int len = s.length();
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if(ch < ' ' || ch >= 0x7F || ch == '?' || ch == '�' || ch == '"' || ch == '*' || ch == '|' || ch == '<' || ch == '>' || ch == escape) {
				sb.append(escape);
				if(ch < 0x10) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	public static final String makeStringFilesystemSafe(String s) {
		char escape = '%'; // ... or some other legal char.
		int len = s.length();
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if(ch < ' ' || ch >= 0x7F || ch == '/' || ch == '\\' || ch == '?' || ch == '�' || ch == ':' || ch == '"' || ch == '*' || ch == '|' || ch == '<' || ch == '>' || (ch == '.' && i == 0) || ch == escape) {
				sb.append(escape);
				if(ch < 0x10) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	//======================================================================================================================================
	
	private volatile boolean isRunning = false;
	
	protected static final SSLSocketFactory sslSocketFactory;
	
	static {
		SSLSocketFactory factory;
		try {
			factory = new TrustAnySSLCertificateSSLSocketFactory();
		} catch(KeyManagementException | NoSuchAlgorithmException e) {
			factory = SSLSocketFactory.class.cast(SSLSocketFactory.getDefault());
		}
		sslSocketFactory = factory;
	}
	
	protected final Thread swtThread;
	protected final Display display;
	protected final Shell shell;
	protected volatile Text requestURI;
	protected volatile CCombo httpMethod;
	protected volatile Text headers;
	protected volatile Text ipAddress;
	protected volatile Text serverResponse;
	protected volatile CCombo protocolVersion;
	protected volatile Spinner portNumber;
	protected volatile Button https;
	protected volatile Button saveDownloadedFile;
	protected volatile Button acceptEncodingGZIP;
	
	/** Default constructor */
	public Main() {
		this.swtThread = Thread.currentThread();
		this.display = Display.getDefault();
		this.shell = new Shell(this.display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		this.shell.setSize(450, 530);
		this.shell.setText("HTTP Server Tester");
		this.shell.setImages(SWTUtil.getTitleImages());
		SWTUtil.centerShellOnPrimaryMonitor(this.shell);
	}
	
	/** @param args System command arguments
	 * @wbp.parser.entryPoint */
	public static final void main(String[] args) {
		instance = new Main();
		instance.open();
	}
	
	/** Updates window elements */
	public final void updateUI() {
		if(this.shell.isDisposed()) {
			return;
		}
		
		// XXX If/when any update logic is needed, put it here
		
	}
	
	/** Executes the application loop
	 * 
	 * @return Whether or not the application should continue running */
	public final boolean runLoop() {
		if(!this.isRunning) {
			return false;
		}
		this.updateUI();
		
		while(!this.shell.isDisposed() && this.display.readAndDispatch());
		if(!this.shell.isDisposed()) {
			this.display.sleep();
		}
		
		return !this.shell.isDisposed();
	}
	
	/** Opens this application's window */
	public final void open() {
		this.createContents();
		
		this.shell.open();
		this.shell.layout();
		
		this.isRunning = true;
		
		while(this.runLoop());
		
		this.isRunning = false;
	}
	
	public final void createContents() {
		Label lblSendARequest = new Label(this.shell, SWT.NONE);
		lblSendARequest.setBounds(10, 10, 424, 36);
		lblSendARequest.setText("Send a request to a web server and review its response.\r\nFill out the text fields below and click the \"Send request\" button to begin.");
		
		Label lblProtocol = new Label(this.shell, SWT.NONE);
		lblProtocol.setBounds(10, 92, 55, 21);
		lblProtocol.setText("Method:");
		
		this.httpMethod = new CCombo(this.shell, SWT.BORDER);
		this.httpMethod.setEditable(true);
		this.httpMethod.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		this.httpMethod.setItems(new String[] {"CONNECT", "GET", "HEAD", "PATCH", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "BREW"});
		this.httpMethod.select(1);
		this.httpMethod.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(Main.this.httpMethod.getText().equalsIgnoreCase("BREW")) {
					SWTUtil.setText(Main.this.protocolVersion, "HTCPCP/1.1");
				} else if(Main.this.protocolVersion.getText().equalsIgnoreCase("HTCPCP/1.1")) {
					SWTUtil.setText(Main.this.protocolVersion, "HTTP/1.1");
				}
			}
		});
		this.httpMethod.setBounds(71, 92, 94, 21);
		
		this.requestURI = new Text(this.shell, SWT.BORDER);
		this.requestURI.setText("/index.html");
		this.requestURI.setBounds(91, 119, 343, 21);
		
		this.protocolVersion = new CCombo(this.shell, SWT.BORDER);
		this.protocolVersion.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		this.protocolVersion.setEditable(true);
		this.protocolVersion.setItems(new String[] {"HTTP/1.1", "HTTP/1.0"});
		this.protocolVersion.setText("HTTP/1.1");
		this.protocolVersion.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(Main.this.protocolVersion.getText().equalsIgnoreCase("HTCPCP/1.1")) {
					SWTUtil.setText(Main.this.httpMethod, "BREW");
				} else if(Main.this.httpMethod.getText().equalsIgnoreCase("BREW")) {
					SWTUtil.setText(Main.this.httpMethod, "GET");
				}
			}
		});
		this.protocolVersion.setBounds(232, 92, 81, 21);
		
		Label lblProtocol_1 = new Label(this.shell, SWT.NONE);
		lblProtocol_1.setBounds(171, 92, 55, 21);
		lblProtocol_1.setText("Protocol:");
		
		Label label = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 146, 424, 2);
		
		Label lblRequest = new Label(this.shell, SWT.NONE);
		lblRequest.setBounds(10, 119, 75, 21);
		lblRequest.setText("Request URI:");
		
		Label lblHeaders = new Label(this.shell, SWT.NONE);
		lblHeaders.setBounds(10, 151, 55, 15);
		lblHeaders.setText("Headers:");
		
		this.headers = new Text(this.shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		this.headers.setBounds(10, 172, 424, 75);
		
		Label label_1 = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(10, 87, 424, 2);
		
		Label label_2 = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 52, 424, 2);
		
		Label lblHostnameip = new Label(this.shell, SWT.NONE);
		lblHostnameip.setBounds(10, 60, 87, 21);
		lblHostnameip.setText("Hostname/IP:");
		
		this.ipAddress = new Text(this.shell, SWT.BORDER);
		this.ipAddress.setText("www.example.com");
		this.ipAddress.setBounds(103, 60, 123, 21);
		
		Label lblNoteThehost = new Label(this.shell, SWT.NONE);
		lblNoteThehost.setBounds(71, 151, 363, 15);
		lblNoteThehost.setText("Note: The \"host\" and \"User agent\" headers are sent automatically.");
		
		Label lblPortNumber = new Label(this.shell, SWT.NONE);
		lblPortNumber.setBounds(232, 60, 87, 15);
		lblPortNumber.setText("Port Number:");
		
		this.portNumber = new Spinner(this.shell, SWT.BORDER);
		this.portNumber.setTextLimit(5);
		this.portNumber.setMaximum(65535);
		this.portNumber.setSelection(80);
		this.portNumber.setBounds(325, 59, 94, 22);
		this.portNumber.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(Main.this.portNumber.getSelection() == 443) {
					Main.this.https.setSelection(true);
				} else if(Main.this.portNumber.getSelection() == 80) {
					Main.this.https.setSelection(false);
				}
			}
		});
		
		this.https = new Button(this.shell, SWT.CHECK);
		this.https.setBounds(326, 92, 108, 21);
		this.https.setText("HTTPS");
		
		Button btnSendRequest = new Button(this.shell, SWT.NONE);
		btnSendRequest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Main.this.serverResponse.setText(sendRequest(Main.this.ipAddress.getText(), Main.this.portNumber.getSelection(), Main.this.https.getSelection(), Main.this.httpMethod.getText(), Main.this.protocolVersion.getText(), Main.this.requestURI.getText(), Main.this.headers.getText(), Main.this.saveDownloadedFile.getSelection(), Main.this.acceptEncodingGZIP.getSelection()));
			}
		});
		btnSendRequest.setBounds(10, 253, 87, 25);
		btnSendRequest.setText("Send request");
		
		Label label_3 = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 284, 424, 2);
		
		Label lblResponse = new Label(this.shell, SWT.NONE);
		lblResponse.setBounds(10, 292, 55, 15);
		lblResponse.setText("Response:");
		
		this.serverResponse = new Text(this.shell, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		this.serverResponse.setEditable(false);
		this.serverResponse.setBounds(10, 313, 424, 178);
		
		this.saveDownloadedFile = new Button(this.shell, SWT.CHECK);
		this.saveDownloadedFile.setBounds(103, 253, 139, 25);
		this.saveDownloadedFile.setText("Save downloaded file");
		
		this.acceptEncodingGZIP = new Button(this.shell, SWT.CHECK);
		this.acceptEncodingGZIP.setBounds(248, 253, 151, 25);
		this.acceptEncodingGZIP.setText("Accept-Encoding: gzip");
		
	}
	
	public static final void println(String str) {
		System.out.println(str);
	}
	
	@SuppressWarnings("resource")
	public static final String sendRequest(String ip, int port, boolean https, String method, String protocol, String requestURI, String headers, boolean saveDownloadedFile, boolean acceptEncodingGZip) {
		requestURI = requestURI.trim().isEmpty() ? "/" : requestURI;//requestURI.startsWith("/") ? requestURI : "/" + requestURI;
		System.out.println("Connecting to http" + (https ? "s" : "") + "://" + ip + ":" + port + " (HTTP Request: " + method + " " + requestURI + " " + protocol + ")");
		InetSocketAddress addr = new InetSocketAddress(ip, port);
		boolean failedToConnect = addr.isUnresolved();
		if(failedToConnect) {
			return "Failed to connect to server \"" + ip + ":" + port + "\": Address is unresolved";
		}
		try {
			String hostHeader = null;
			String userAgentHeader = "User-Agent: HTTPServerTester/1.0 (Windows NT 6.1; Win64; x64)";// TODO Actually fetch the OS name and version instead of hard-coding it here
			
			boolean userAgentDefined = false;
			boolean acceptEncodingDefined = false;
			for(String header : headers.split(Pattern.quote("\n"))) {
				if(header.toLowerCase().startsWith("host:")) {
					hostHeader = header.substring(5).strip();
				}
				if(header.toLowerCase().startsWith("user-agent:")) {
					userAgentDefined = true;
				}
				if(header.toLowerCase().startsWith("accept-encoding:")) {
					acceptEncodingDefined = true;
				}
			}
			
			Socket server = https ? sslSocketFactory.createSocket(ip, port) : SocketFactory.getDefault().createSocket(ip, port);
			server.setTcpNoDelay(true);
			if(https) {
				SSLSocket socket = (SSLSocket) server;
				SSLParameters parameters = socket.getSSLParameters();
				if(hostHeader != null) {
					SNIServerName serverName;
					try {
						serverName = new SNIHostName(hostHeader);
					} catch(IllegalArgumentException ex) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try(PrintWriter pr = new PrintWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8), true)) {
							ex.printStackTrace(pr);
							pr.flush();
						}
						byte[] data = baos.toByteArray();
						String throwableToStr = new String(data, 0, data.length, StandardCharsets.UTF_8);
						
						return "Failed to connect to server \"" + ip + ":" + port + "\": Invalid SNI Hostname: \"" + hostHeader + "\";\r\n" + throwableToStr;
					}
					
					parameters.setServerNames(Arrays.asList(serverName));
				}
				
				socket.setSSLParameters(parameters);
				socket.startHandshake();
			}
			OutputStream outStream = server.getOutputStream();
			InputStream in = server.getInputStream();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
			if(requestURI.contains("://") && method.equalsIgnoreCase("CONNECT")) {
				Link link = new Link(requestURI);
				out.println("CONNECT " + link.protocol + link.host + (link.port == (link.protocol.equalsIgnoreCase("https://") ? 443 : (link.protocol.equalsIgnoreCase("http://") ? 80 : -1)) ? "" : ":".concat(Integer.toString(link.port))) + "/ " + protocol);
			} else {
				out.println(method + " " + requestURI.replace(" ", "%20").replace("#", "%23") + " " + protocol);
			}
			if(hostHeader == null) {
				if(requestURI.contains("://") && method.equalsIgnoreCase("CONNECT")) {
					Link link = new Link(requestURI);
					out.println("Host: " + link.host + (link.port == (link.protocol.equalsIgnoreCase("https://") ? 443 : (link.protocol.equalsIgnoreCase("http://") ? 80 : -1)) ? "" : ":".concat(Integer.toString(link.port))));
				} else {
					out.println("Host: " + ip);
				}
			}
			if(!userAgentDefined) {
				out.println(userAgentHeader);
			}
			if(!acceptEncodingDefined && acceptEncodingGZip) {
				out.println("Accept-Encoding: gzip");
			}
			out.println("Connection: close");
			out.println(headers);
			if(!headers.isEmpty()) {
				out.println();
			}
			out.flush();
			
			String getResponseLine = null;
			final List<String> headerList = new ArrayList<>();
			String response = "";
			String line;
			while((line = readLine(in)) != null && !line.trim().isEmpty()) {
				response += line + "\n";
				if(getResponseLine == null) {
					getResponseLine = line.trim();
					continue;
				}
				headerList.add(line);
			}
			final String responseLine;
			if(getResponseLine != null && getResponseLine.toLowerCase().contains("200 connection established")) {
				if(requestURI.contains("://") && method.equalsIgnoreCase("CONNECT")) {
					method = "GET";
					Link link = new Link(requestURI);
					requestURI = link.requestPath;
					
					if(link.isSSL()) {
						server = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(server, link.host, link.port, true);
						((SSLSocket) server).startHandshake();
						outStream = server.getOutputStream();
						in = server.getInputStream();
						out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
					}
					
					out.println(method + " " + requestURI.replace(" ", "%20").replace("#", "%23") + " " + protocol);
					if(hostHeader == null) {
						out.println("Host: " + link.host);
					}
					if(!userAgentDefined) {
						out.println(userAgentHeader);
					}
					if(!acceptEncodingDefined && acceptEncodingGZip) {
						out.println("Accept-Encoding: gzip");
					}
					StringBuilder sb = new StringBuilder();
					for(String header : headers.split(Pattern.quote("\n"))) {
						header = header.endsWith("\r") ? header.substring(0, header.length() - 1) : header;
						if(header.toLowerCase().startsWith("proxy-")) {
							continue;
						}
						sb.append(header).append("\r\n");
					}
					out.println("Connection: close");
					out.println(sb.toString());
					if(!headers.isEmpty()) {
						out.println();
					}
					
					getResponseLine = null;
					headerList.clear();
					while((line = readLine(in)) != null && !line.trim().isEmpty()) {
						response += line.strip() + "\n";
						if(getResponseLine == null) {
							getResponseLine = line.trim();
							continue;
						}
						headerList.add(line.strip());
					}
					responseLine = getResponseLine == null ? "" : getResponseLine;
				} else {
					responseLine = getResponseLine;
				}
			} else {
				responseLine = getResponseLine == null ? "" : getResponseLine;
			}
			String requestURL = requestURI;
			
			if(line != null && line.trim().isEmpty() && saveDownloadedFile) {
				final Socket SERVER = server;
				final InputStream IN = in;
				final String METHOD = method;
				final String reqURI = requestURI;
				Runnable code = new Runnable() {
					@Override
					public void run() {
						boolean chunkedTransferEncoding = false;
						boolean gzip = false;
						/*for(String header : headers.split(Pattern.quote("\n"))) {
							header = header.endsWith("\r") ? header.substring(0, header.length() - 1) : header;
							String[] split = header.split(Pattern.quote(":"));
							header = split.length == 0 ? header : split[0];
							String value = "";
							for(int i = 1; i < split.length; i++) {
								value = value.concat(split[i]).concat(i + 1 == split.length ? "" : ":");
							}
							if(header.equalsIgnoreCase("Accept-Encoding")) {
								gzip = value.toLowerCase().contains("gzip");
								break;
							}
						}*/
						
						System.out.println(responseLine);
						String location = null;
						String mimeType = null;
						String contentDisposition = null;
						String wwwAuthenticate = null, realm = null;
						for(String header : headerList) {
							String[] split = header.split(Pattern.quote(":"));
							if(split.length >= 2) {
								String hname = split[0].strip();
								String value = "";
								for(int i = 1; i < split.length; i++) {
									value = value.concat(split[i]).concat(i + 1 == split.length ? "" : ":");
								}
								value = value.startsWith(" ") ? value.substring(1) : value;
								if(hname.equalsIgnoreCase("Location")) {
									/*String value = "";
									int i = -1;
									for(String arg : split) {
										i++;
										if(i == 0) {
											continue;
										}
										value = value.concat(arg).concat(i + 1 == split.length ? "" : ":");
									}*/
									location = value.trim();
									break;
								}
								if(hname.equalsIgnoreCase("Transfer-Encoding")) {
									chunkedTransferEncoding = value.trim().equalsIgnoreCase("chunked");
								}
								if(hname.equalsIgnoreCase("Content-Encoding")) {
									gzip = value.trim().equalsIgnoreCase("gzip");
								}
								if(hname.equalsIgnoreCase("Content-Type")) {
									mimeType = value.strip();
								}
								if(hname.equalsIgnoreCase("Content-Disposition")) {
									contentDisposition = value.strip();
								}
								if(hname.equalsIgnoreCase("WWW-Authenticate")) {
									wwwAuthenticate = value.strip();
								}
							}
						}
						
						String[] resSpl = responseLine.split(Pattern.quote(" "));
						String responseProtocol = resSpl.length >= 1 ? resSpl[0] : "";//		HTTP/1.1
						String responseStatusCode = resSpl.length >= 2 ? resSpl[1] : "";//	200
						String responseStatusMessage = "";//								OK
						for(int i = 2; i < resSpl.length; i++) {
							responseStatusMessage = responseStatusMessage.concat(i == 2 ? "" : " ").concat(resSpl[i]);
						}
						
						if(!responseProtocol.equalsIgnoreCase(protocol)) {
							System.err.println("HTTP protocol and/or version mismatch! Expected \"".concat(protocol).concat("\", received: \"").concat(responseProtocol).concat("\"!"));
							return;
						}
						
						if(responseStatusCode.equals("301") || responseStatusCode.equals("302") || responseStatusCode.equals("303") || responseStatusCode.equals("307") || responseStatusCode.equals("308")) {
							if(location != null) {
								if(location.startsWith("http://") || location.startsWith("https://")) {
									Link link = new Link(location);
									String url = location.startsWith("/") ? location : link.requestPath;
									
									System.out.println("Redirect: " + url);
									System.out.println(sendRequest(link.host, link.port, link.isSSL(), METHOD, protocol, url, headers, saveDownloadedFile, acceptEncodingGZip));
								} else {
									System.out.println(location);
									System.out.println(sendRequest(ip, port, https, METHOD, protocol, location, headers, saveDownloadedFile, acceptEncodingGZip));
								}
							}
							return;
						}
						if(responseStatusCode.equals("401")) {
							if(wwwAuthenticate != null) {
								String[] split = wwwAuthenticate.split(Pattern.quote(" "));
								String authScheme = split.length >= 1 ? split[0] : "";
								for(int i = 1; i < split.length; i++) {
									String option = split[i];
									if(option.contains("=")) {
										String[] spl = option.split(Pattern.quote("="));
										String param = spl.length >= 1 ? spl[0] : "";
										String value = "";
										
										if(param.equalsIgnoreCase("realm")) {
											realm = value;
											break;
										}
										
									}
								}
								
								if(authScheme.equalsIgnoreCase("Basic")) {
									Main main = Main.instance;
									if(main != null && main.display != null && !main.display.isDisposed()) {
										final String[][] credentials = new String[][] {null};
										final String title = responseStatusCode.concat(" ").concat(responseStatusMessage).strip().concat(" (Login Required)");
										final String msg = "The webpage you are attempting to access requires a username and password.".concat(realm != null && !realm.isBlank() ? "\r\nThe server says:\r\n\r\n".concat(realm) : "");
										main.display.asyncExec(() -> {
											credentials[0] = new PasswordPromptDialog(main.shell, title, msg).open();
										});
										
										while(credentials[0] == null) {
											ThreadUtil.sleep(10L);
										}
										
										String[] userAndPass = credentials[0];
										if(userAndPass.length >= 2) {
											String username = userAndPass[0];
											String password = userAndPass[1];
											
											if(username != null && !username.isBlank() && password != null) {
												String authorization;
												{
													Charset charset = StandardCharsets.ISO_8859_1;
													byte[] data = username.concat(":").concat(password).getBytes(charset);
													byte[] encoded = Base64.getEncoder().encode(data);
													
													authorization = new String(encoded, 0, encoded.length, charset);
												}
												
												String authHeader = "Authorization: Basic ".concat(authorization);
												
												StringBuilder sb = new StringBuilder();
												for(String header : headers.split(Pattern.quote("\n"))) {
													header = header.endsWith("\r") ? header.substring(0, header.length() - 1) : header;
													
													if(header.contains(":")) {
														String[] spl = header.split(Pattern.quote(":"));
														String hname = spl.length >= 1 ? spl[0] : "";
														
														if(hname.strip().equalsIgnoreCase("Authorization")) {
															continue;
														}
													}
													if(!sb.isEmpty()) {
														sb.append("\r\n");
													}
													sb.append(header);
												}
												if(!sb.isEmpty()) {
													sb.append("\r\n");
												}
												sb.append(authHeader);
												
												String newHeaders = sb.toString();
												System.out.println(sendRequest(ip, port, https, METHOD, protocol, reqURI, newHeaders, saveDownloadedFile, acceptEncodingGZip));
												return;
											}
										}
									}
								}
							}
							return;
						}
						String fileName = FilenameUtils.getName(reqURI.split(Pattern.quote("?"))[0].split(Pattern.quote("#"))[0]);
						if(contentDisposition != null && contentDisposition.toLowerCase().startsWith("attachment;")) {
							String[] split = contentDisposition.split(Pattern.quote(";"));
							for(String s : split) {
								s = s.strip();
								if(!s.contains("=")) {
									continue;
								}
								String[] args = s.split(Pattern.quote("="));
								String pname = args.length >= 1 ? args[0] : "";
								String value = "";
								for(int i = 1; i < args.length; i++) {
									value = value.concat(i > 1 ? "=" : "").concat(args[i]);
								}
								
								if(pname.equalsIgnoreCase("filename")) {
									value = value.startsWith("\"") && value.endsWith("\"") && value.length() > 2 ? value.substring(1, value.length() - 1) : value;
									
									if(!value.isBlank() && !value.contains("\"")) {
										fileName = value;
									}
									break;
								}
							}
						}
						{
							String ext = FilenameUtils.getExtension(fileName);
							if(mimeType != null) {
								mimeType = mimeType.contains(";") ? mimeType.substring(0, mimeType.indexOf(";")) : mimeType;
								String mimeExt = MimeTypes.getFirstExtensionForMimeType(mimeType, null);
								if(mimeExt != null) {
									mimeExt = mimeExt.startsWith(".") ? mimeExt.substring(1) : mimeExt;
									
									if(ext.isBlank()) {
										fileName = fileName.concat(".").concat(mimeExt);
									} else {
										fileName = FilenameUtils.getBaseName(fileName).concat(".").concat(mimeExt);
									}
									ext = mimeExt;
								}
							}
							if(ext.isBlank()) {
								ext = "html";
								
								fileName = fileName.concat(fileName.endsWith(".") ? "" : ".").concat(ext);
							}
							
							if(FilenameUtils.getBaseName(fileName).isBlank()) {
								String name = reqURI.contains("?") ? reqURI.substring(0, reqURI.indexOf("?")) : reqURI;
								while(name.endsWith("/")) {
									name = name.endsWith("/") ? name.substring(0, name.lastIndexOf("/")) : name;
								}
								name = name.contains("/") ? name.substring(name.lastIndexOf("/")) : name;
								if(!name.isBlank()) {
									fileName = name.strip().concat(ext.isBlank() ? "" : ".").concat(ext);
								}
							}
							if(FilenameUtils.getBaseName(fileName).isBlank()) {
								if(fileName.isBlank()) {
									fileName = ip.replace(":", "-").replace("%", "-").concat(".txt");
								} else {
									fileName = ip.replace(":", "-").replace("%", "-").concat(".").concat(FilenameUtils.getExtension(fileName));
								}
							}
						}
						
						fileName = fileName.startsWith("/") ? fileName.substring(1) : fileName;
						File folder = new File(System.getProperty("user.dir") + File.separatorChar + "downloads");
						if(!folder.exists()) {
							folder.mkdirs();
						}
						try {
							InputStream in = IN;
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							/*if(gzip) {
								System.out.println("GZIP: Is the whole thing gzipped?");
								byte[] buf = new byte[4096];
								int read;
								while((read = in.read(buf)) != -1) {
									baos.write(buf, 0, read);
								}
								final byte[] data = baos.toByteArray();
								
								try {
									in = new GZIPInputStream(new ByteArrayInputStream(data));
									System.out.println("GZIP: Looks like it.");
								} catch(ZipException ex) {
									System.out.println("GZIP: Nope, let's put the data back (" + Integer.toString(data.length) + " byte" + (data.length == 1 ? "" : "s") + ")");
									in = new ByteArrayInputStream(data);
									baos = new ByteArrayOutputStream();
								}
							} else {
								System.out.println("GZIP: gzip not specified.");
							}*/
							
							if(chunkedTransferEncoding) {
								System.out.println("Transfer-Encoding is chunked.");
								boolean fallback = false;
								String getLength;
								while((getLength = readLine(in)) != null) {
									String ogLine = getLength;
									getLength = getLength.endsWith("\n") ? getLength.substring(0, getLength.length() - 1) : getLength;
									getLength = getLength.endsWith("\r") ? getLength.substring(0, getLength.length() - 1) : getLength;
									final int length;
									if(isHexInt(getLength)) {
										length = Integer.parseInt(getLength, 16);
										System.out.println("Chunked header hex length: " + getLength + "(" + Integer.toString(length) + ")");
									} else {
										System.out.flush();
										System.err.println("Expected integer hex length; got: " + getLength);
										System.err.flush();
										baos.write(ogLine.getBytes());
										fallback = true;
										break;
									}
									
									int count = 0;
									int b;
									while((b = in.read()) != -1) {
										baos.write(b);
										count++;
										if(count >= length) {
											break;
										}
									}
									//String check = readLine(in);
									byte b1 = (byte) in.read();
									byte b2 = (byte) in.read();
									String check = b1 == -1 || b2 == -1 ? null : new String(new byte[] {b1, b2}, 0, 2, StandardCharsets.UTF_8);
									if(check == null || !check.isBlank()) {// read and discard the next \r\n sequence
										if(check != null) {
											System.out.flush();
											System.err.println("Encountered unexpected data near end of chunk:");
											System.err.println(check);
											System.err.flush();
											baos.write(check.getBytes());
											fallback = true;
										}
										if(b1 != -1) {
											baos.write(b1 & 0xFF);
										}
										break;
									}
								}
								if(fallback) {
									byte[] buf = new byte[4096];
									int read;
									while((read = in.read(buf)) != -1) {
										baos.write(buf, 0, read);
									}
								}
							} else {
								System.out.println("Transfer-Encoding is not chunked.");
								byte[] buf = new byte[4096];
								int read;
								while((read = in.read(buf)) != -1) {
									baos.write(buf, 0, read);
								}
							}
							
							if(gzip) {
								final byte[] data = baos.toByteArray();
								if(chunkedTransferEncoding) {
									System.out.println("GZIP: Let's attempt to uncompress what we read from the chunked transfer encoding (" + Integer.toString(data.length) + " byte" + (data.length == 1 ? "" : "s") + ")...");
								} else {
									System.out.println("GZIP: Let's decompress the stream (" + Integer.toString(data.length) + " byte" + (data.length == 1 ? "" : "s") + ")...");
								}
								baos = new ByteArrayOutputStream();
								try(InputStream gzIn = new GZIPInputStream(new ByteArrayInputStream(data))) {
									System.out.println("GZIP: Looks like we're able to uncompress the chunked data, yay!");
									byte[] buf = new byte[4096];
									int read;
									while((read = gzIn.read(buf)) != -1) {
										baos.write(buf, 0, read);
									}
								} catch(Throwable ignored) {
									System.out.flush();
									System.err.println("Welp, we weren't able to uncompress the data:");
									ignored.printStackTrace(System.err);
									System.err.flush();
									baos = new ByteArrayOutputStream();
									baos.write(data);
								}
								
							}
							
							if(baos.size() > 0) {
								
								File file = new File(folder, makeStringFilesystemSafe(fileName));
								if(file.exists()) {
									int i = 0;
									String namePart = makeStringFilesystemSafe(FilenameUtils.getBaseName(fileName));
									String ext = "." + FilenameUtils.getExtension(fileName);
									while(file.exists()) {
										file = new File(folder, namePart + "_" + i + ext);
										i++;
									}
								}
								FileOutputStream fout = new FileOutputStream(file, false);
								fout.write(baos.toByteArray());
								//response += new String(baos.toByteArray(), StandardCharsets.UTF_8);
								//Desktop.getDesktop().open(file);
								Desktop.getDesktop().open(file.getParentFile());
								fout.close();
							}
							SERVER.close();
						} catch(IOException e) {
							System.err.println("Failed to download file \"" + fileName + "\": " + throwableToStr(e));
						}
					}
				};
				if(Thread.currentThread() == Main.instance.swtThread) {
					new Thread(code).start();
				} else {
					code.run();
				}
			} else {
				server.close();
			}
			return response;
		} catch(IOException e) {
			return throwableToStr(e);
		}
		/*String serverResponse = method + " " + (https ? "https://" : "http://") + ip + ":" + port + requestURI + " " + protocol + "\r\n";
		serverResponse += headers + "\r\n";
		return serverResponse;*/
	}
	
	protected static final String readLine(InputStream in) throws IOException {
		if(in == null) {
			return null;
		}
		String line = "";
		int read;
		while((read = in.read()) != -1) {
			String r = new String(new byte[] {(byte) read});
			line += r;
			if(r.equals("\n") || read == -1) {
				break;
			}
		}
		if(read == -1 && line.isEmpty()) {
			return null;
		}
		//line = line.trim();
		//System.out.println("Read line: \"" + line + "\";");
		return line;
	}
	
	/** @param stackTraceElements The elements to convert
	 * @return The resulting string */
	public static final String stackTraceElementsToStr(StackTraceElement[] stackTraceElements) {
		String str = "";
		for(StackTraceElement stackTrace : stackTraceElements) {
			str += (!stackTrace.toString().startsWith("Caused By") ? "     at " : "") + stackTrace.toString() + "\r\n";
		}
		return str;
	}
	
	public static String throwableToStr(Throwable t) {
		if(t == null) {
			return "null";
		}
		String str = t.getClass().getName() + ": ";
		if((t.getMessage() != null) && !t.getMessage().isEmpty()) {
			str += t.getMessage() + "\r\n";
		} else {
			str += "\r\n";
		}
		str += stackTraceElementsToStr(t.getStackTrace());
		if(t.getCause() != null) {
			str += "Caused by:\r\n" + throwableToStr(t.getCause());
		}
		return str;
	}
}
