package com.gmail.br45entei.main;

import com.gmail.br45entei.swt.Functions;

import java.awt.Desktop;
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
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/** @author Brian_Entei */
public final class Main {
	
	private static Main			instance	= null;
	
	private volatile boolean	isRunning	= false;
	
	protected final Display		display;
	protected final Shell		shell;
	protected volatile Text		requestURI;
	protected volatile CCombo	httpMethod;
	protected volatile Text		headers;
	protected volatile Text		ipAddress;
	protected volatile Text		serverResponse;
	protected volatile CCombo	protocolVersion;
	protected volatile Spinner	portNumber;
	protected volatile Button	https;
	protected volatile Button	saveDownloadedFile;
	
	/** Default constructor */
	public Main() {
		this.display = Display.getDefault();
		this.shell = new Shell(this.display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		this.shell.setSize(450, 530);
		this.shell.setText("HTTP Server Tester");
		this.shell.setImages(new Image[] {SWTResourceManager.getImage(Functions.class, "/assets/textures/title/Entei-16x16.png"), SWTResourceManager.getImage(Functions.class, "/assets/textures/title/Entei-32x32.png"), SWTResourceManager.getImage(Functions.class, "/assets/textures/title/Entei-64x64.png"), SWTResourceManager.getImage(Functions.class, "/assets/textures/title/Entei-128x128.png")});
		Functions.centerShellOnPrimaryMonitor(this.shell);
	}
	
	/** @param args System command arguments
	 * @wbp.parser.entryPoint */
	public static final void main(String[] args) {
		instance = new Main();
		instance.open();
	}
	
	/** Opens this application's window */
	public final void open() {
		this.isRunning = true;
		this.createContents();
		this.shell.open();
		this.shell.layout();
		while(this.isRunning) {
			if(this.shell.isDisposed()) {
				break;
			}
			this.runLoop();
		}
		this.isRunning = false;
	}
	
	/** Executes the application loop */
	public final void runLoop() {
		runClock();
		updateUI();
		runClock();
	}
	
	/** Updates the shell and ceases execution for a short time */
	public final void runClock() {
		if(this.shell.isDisposed()) {
			return;
		}
		if(this.shell.isVisible()) {
			if(!this.display.readAndDispatch()) {
				Functions.sleep(1L);
			}
			if(this.shell.isDisposed()) {
				return;
			}
			this.shell.update();
		}
		Functions.sleep(10L);
	}
	
	/** Updates window elements */
	public final void updateUI() {
		if(this.shell.isDisposed()) {
			return;
		}
		//TODO
	}
	
	public final void createContents() {
		Label lblSendARequest = new Label(this.shell, SWT.NONE);
		lblSendARequest.setBounds(10, 10, 424, 36);
		lblSendARequest.setText("Send a request to a web server and review its response.\r\nFill out the text fields below and click the \"Send request\" button to begin.");
		
		Label lblProtocol = new Label(this.shell, SWT.NONE);
		lblProtocol.setBounds(10, 92, 55, 21);
		lblProtocol.setText("Method:");
		
		this.httpMethod = new CCombo(this.shell, SWT.BORDER);
		this.httpMethod.setEditable(false);
		this.httpMethod.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		this.httpMethod.setItems(new String[] {"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "TRACE", "CONNECT"});
		this.httpMethod.setText("GET");
		this.httpMethod.setBounds(71, 92, 94, 21);
		
		this.requestURI = new Text(this.shell, SWT.BORDER);
		this.requestURI.setText("index.html");
		this.requestURI.setBounds(91, 119, 343, 21);
		
		this.protocolVersion = new CCombo(this.shell, SWT.BORDER);
		this.protocolVersion.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		this.protocolVersion.setEditable(false);
		this.protocolVersion.setItems(new String[] {"HTTP/1.1", "HTTP/1.0"});
		this.protocolVersion.setText("HTTP/1.1");
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
				Main.this.serverResponse.setText(sendRequest(Main.this.ipAddress.getText(), Main.this.portNumber.getSelection(), Main.this.https.getSelection(), Main.this.httpMethod.getText(), Main.this.protocolVersion.getText(), Main.this.requestURI.getText(), Main.this.headers.getText(), Main.this.saveDownloadedFile.getSelection()));
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
		
	}
	
	public static final void println(String str) {
		System.out.println(str);
	}
	
	@SuppressWarnings("resource")
	public static final String sendRequest(String ip, int port, boolean https, String method, String protocol, String requestURI, String headers, boolean saveDownloadedFile) {
		requestURI = requestURI.startsWith("/") ? requestURI : "/" + requestURI;
		InetSocketAddress addr = new InetSocketAddress(ip, port);
		boolean failedToConnect = addr.isUnresolved();
		if(failedToConnect) {
			return "Failed to connect to server \"" + ip + ":" + port + "\": Address is unresolved";
		}
		try {
			Socket server = https ? SSLSocketFactory.getDefault().createSocket(ip, port) : SocketFactory.getDefault().createSocket(ip, port);
			server.setTcpNoDelay(true);
			if(https) {
				((SSLSocket) server).startHandshake();
			}
			OutputStream outStream = server.getOutputStream();
			final InputStream in = server.getInputStream();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
			out.println(method + " " + requestURI.replace(" ", "%20").replace("#", "%23") + " " + protocol);
			out.println("host: " + ip);
			out.println("User-Agent: HTTPServerTester/1.0 (Windows NT 6.1");
			out.println("Connection: close");
			out.println(headers);
			if(!headers.isEmpty()) {
				out.println();
			}
			
			String response = "";
			String line;
			while((line = readLine(in)) != null && !line.trim().isEmpty()) {
				response += line + "\n";
			}
			if(line != null && line.trim().isEmpty() && saveDownloadedFile) {
				final String reqURI = requestURI;
				new Thread(new Runnable() {
					@Override
					public void run() {
						String fileName = FilenameUtils.getName(reqURI.split(Pattern.quote("?"))[0].split(Pattern.quote("#"))[0]);
						File folder = new File(System.getProperty("user.dir") + File.separatorChar + "downloads");
						if(!folder.exists()) {
							folder.mkdirs();
						}
						try {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							byte[] buf = new byte[4096];
							int read;
							while((read = in.read(buf)) != -1) {
								baos.write(buf, 0, read);
							}
							
							File file = new File(folder, fileName);
							if(file.exists()) {
								int i = 0;
								String namePart = FilenameUtils.getBaseName(fileName);
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
							server.close();
						} catch(IOException e) {
							System.err.println("Failed to download file \"" + fileName + "\": " + throwableToStr(e));
						}
					}
				}).start();
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
