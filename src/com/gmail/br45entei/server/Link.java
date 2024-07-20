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
package com.gmail.br45entei.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/** @author Brian Reid &lt;br45entei&#064;gmail.com&gt; */
public final class Link {
	
	public static final Socket createSocket(InetSocketAddress endpoint, boolean ssl) throws IOException {
		return createSocket(endpoint, 0, ssl);
	}
	
	public static final Socket createSocket(InetSocketAddress endpoint, int timeout, boolean ssl) throws IOException {
		Socket sock = ssl ? SSLSocketFactory.getDefault().createSocket() : new Socket();
		sock.connect(endpoint, timeout);
		if(ssl) {
			((SSLSocket) sock).startHandshake();
		}
		return sock;
	}
	
	/** @param in The input stream to read from
	 * @return The read line
	 * @throws IOException Thrown if an error occurred while reading data
	 *             from the input stream */
	public static final String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read;
		while((read = in.read()) != -1) {
			String s = new String(new byte[] {(byte) read});
			if(s.equals("\n")) {
				break;
			}
			baos.write(read);
		}
		if(baos.size() == 0 && read == -1) {
			return null;
		}
		String rtrn = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		return rtrn.endsWith("\r") ? rtrn.substring(0, rtrn.length() - 1) : rtrn;
	}
	
	/** @param s The String to test
	 * @return Whether or not the given String is a valid long value */
	public static final boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public final String URI;
	
	public final String protocol;
	public final String host;
	public final int port;
	public final String requestPath;
	
	private static final boolean isValid(String host) {
		if(host.startsWith("[") && host.endsWith("]")) {
			host = host.substring(1, host.length() - 1);
		}
		try {
			InetAddress.getByName(host).getHostAddress();
			return true;
		} catch(Throwable ex) {
			return false;
		}
	}
	
	public static final boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException | NullPointerException ex) {
			return false;
		}
	}
	
	public Link(String uri) {
		this.URI = uri;
		String protocol = uri.contains("://") ? uri.substring(0, uri.indexOf("://") + 3) : "http://";
		uri = uri.contains("://") ? uri.substring(protocol.length()) : uri;
		String requestPath = uri.contains("/") ? uri.substring(uri.indexOf("/")) : "/";
		String host = uri.endsWith(requestPath) ? uri.substring(0, uri.length() - requestPath.length()) : uri;
		String getPort = protocol.equalsIgnoreCase("https://") ? "443" : "80";
		if(host.contains(":")) {
			if(host.indexOf(":") != host.lastIndexOf(":")) {//IPv6
				String tmp = getPort;
				getPort = host.substring(host.lastIndexOf(":") + 1);
				String check = host.substring(0, host.lastIndexOf(":"));
				if(isInt(getPort) && isValid(check)) {
					host = check;
					/*} else if(CodeUtil.isInt(getPort)) {
						check = host.substring(0, host.lastIndexOf(":") + 1);
						getPort = host.substring(host.lastIndexOf(":") + 1);
						if(CodeUtil.isInt(getPort) && isValid(check)) {
							host = check;
						} else {
							getPort = tmp;
						}*/
				} else {
					getPort = tmp;
				}
			} else {//IPv4, hostnames
				getPort = host.substring(host.lastIndexOf(":") + 1);
				host = host.substring(0, host.lastIndexOf(":"));
			}
		}
		if(host.startsWith("[") && host.endsWith("]")) {
			host = host.substring(1, host.length() - 1);
		}
		if(isInt(getPort)) {
			this.port = Integer.parseInt(getPort);
		} else {
			this.port = -1;
		}
		if(this.port == 443 && !this.URI.contains("://")) {
			protocol = "https://";
		}
		this.protocol = protocol;
		this.host = host;
		this.requestPath = requestPath;
	}
	
	public static final boolean isSSL(String protocol, int port) {
		return(protocol != null && protocol.equalsIgnoreCase("https://") ? true : /*? (port != 80 && port != 8080 && port != 8880) : false) || (*/port == 443 || port == 8443 || port == 8843);
	}
	
	public boolean isSSL() {
		return isSSL(this.protocol, this.port);
	}
	
	public InetSocketAddress getAddress() {
		return new InetSocketAddress(this.host, this.port);
	}
	
	public ServerSocket bindSocket() throws UnknownHostException, IOException {
		return this.bindSocket(InetAddress.getByName(this.host));
	}
	
	public ServerSocket bindSocket(InetAddress ifAddress) throws IOException {
		ServerSocketFactory factory = this.isSSL() ? SSLServerSocketFactory.getDefault() : ServerSocketFactory.getDefault();
		return this.bindSocket(factory, ifAddress);
	}
	
	public ServerSocket bindSocket(int backlog, InetAddress ifAddress) throws IOException {
		ServerSocketFactory factory = this.isSSL() ? SSLServerSocketFactory.getDefault() : ServerSocketFactory.getDefault();
		return this.bindSocket(factory, backlog, ifAddress);
	}
	
	public ServerSocket bindSocket(ServerSocketFactory factory) throws IOException {
		return this.bindSocket(factory, null);
	}
	
	public ServerSocket bindSocket(ServerSocketFactory factory, InetAddress ifAddress) throws IOException {
		return this.bindSocket(factory, 0, ifAddress);
	}
	
	public ServerSocket bindSocket(ServerSocketFactory factory, int backlog, InetAddress ifAddress) throws IOException {
		return factory.createServerSocket(this.port, backlog, ifAddress);
	}
	
	public Socket connectSocket() throws IOException {
		return this.connectSocket(0);
	}
	
	public Socket connectSocket(int timeout) throws IOException {
		return createSocket(this.getAddress(), timeout, this.isSSL());
	}
	
	public URI toURI() throws URISyntaxException {
		return new URI(this.URI);
	}
	
	public URL toURL() throws MalformedURLException, URISyntaxException {
		return this.toURI().toURL();
	}
	
	@Override
	public String toString() {
		return this.URI;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.URI);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj instanceof CharSequence) {
			return Objects.equals(this.URI, ((CharSequence) obj).toString());
		}
		if(!(obj instanceof Link)) {
			return false;
		}
		Link other = (Link) obj;
		return other.URI == null ? this.URI == null : Objects.equals(this.URI, other.URI);
	}
	
}
