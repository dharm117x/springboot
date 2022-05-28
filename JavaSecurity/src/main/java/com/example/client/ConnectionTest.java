package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public class ConnectionTest {
	
	static String token;
	public static void main(String[] args) throws MalformedURLException, IOException {
		getConnection();
	}

	private static void getConnection() throws MalformedURLException, IOException {
		URL url = new URL("https://localhost:9091/token?username=admin&password=nimda");
		URLConnection conn = url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection con = getResponse(url, "POST");
			String result = getResResult(con);
			token = result.substring(9, result.length());
			HttpsURLConnection connection = getResponse(new URL("https://localhost:9091/api/userid/12"), "GET");
			String resResult = getResResult(connection);
			
		}
	}

	private static HttpsURLConnection getResponse(URL url, String method) throws IOException, ProtocolException {
		
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				System.out.println("true");
				return true;
			}
		});
		
		con.setRequestMethod(method);
		con.setRequestProperty("Authorization", "Bearer "+token);
		
		try {
			System.out.println("Response Code : " + con.getResponseCode());
			System.out.println("Cipher Suite : " + con.getCipherSuite());
			System.out.println("\n");
			Certificate[] certs = con.getServerCertificates();
			for (Certificate cert : certs) {
				System.out.println("Cert Type : " + cert.getType());
				System.out.println("Cert Hash Code : " + cert.hashCode());
				System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
				System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
				System.out.println("\n");
			}

		} catch (SSLPeerUnverifiedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return con;
	}

	private static String getResResult(HttpsURLConnection con) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			System.out.println("****** Content of the URL ********");
			StringBuffer buffer = new StringBuffer();
			String input;
			while ((input = br.readLine()) != null) {
				System.out.println(input);
				buffer.append(input);
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
