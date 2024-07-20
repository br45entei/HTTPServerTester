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
package com.gmail.br45entei.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/** <b>Warning!</b>&nbsp;This class is not to be used in production code for anything other than testing purposes!
 *
 * @author Brian Reid &lt;br45entei&#064;gmail.com&gt; */
public class TrustAnySSLCertificateSSLSocketFactory extends SSLSocketFactory {
	
	private final SSLContext ctx;
	
	public TrustAnySSLCertificateSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
		
		this.ctx = SSLContext.getInstance("TLS");
		
		TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];// (This method MUST NOT return null)
			}
		};
		
		this.ctx.init(null, new TrustManager[] {tm}, SecureRandom.getInstanceStrong());
	}
	
	@Override
	public String[] getDefaultCipherSuites() {
		return this.ctx.getSocketFactory().getDefaultCipherSuites();
	}
	
	@Override
	public String[] getSupportedCipherSuites() {
		return this.ctx.getSocketFactory().getSupportedCipherSuites();
	}
	
	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		return this.ctx.getSocketFactory().createSocket(s, host, port, autoClose);
	}
	
	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return this.ctx.getSocketFactory().createSocket(host, port);
	}
	
	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
		return this.ctx.getSocketFactory().createSocket(host, port, localHost, localPort);
	}
	
	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return this.ctx.getSocketFactory().createSocket(host, port);
	}
	
	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
		return this.ctx.getSocketFactory().createSocket(address, port, localAddress, localPort);
	}
	
}
