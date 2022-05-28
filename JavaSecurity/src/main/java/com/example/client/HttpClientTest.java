package com.example.client;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	static {
		// import certificate into java ceacert file by cmd.
		//System.setProperty("javax.net.ssl.trustStore", "src/main/resources/certificate/localhost.jks");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	}

	public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
	 String resToken = getToken();
	 System.out.println(resToken);
	 String token = resToken.substring(9, resToken.length());
	 
	 String respUser = getUser(token);
	 System.out.println(respUser);
	 
	 String addUser = addUser(token);
	 System.out.println(addUser);
	 
	}

	private static String getToken() throws UnsupportedEncodingException {
		HttpPost request = new HttpPost("https://localhost:9091/token?username=admin&password=nimda");
		 request.addHeader(HttpHeaders.DATE, new Date().toString());
		 request.addHeader("content-type", "application/json");
		 
	    request.setConfig(reqConfig());
		return getResponse(request);
	}

	private static String getUser(String token) {
		HttpGet request = new HttpGet("https://localhost:9091/api/userid/12");
		 request.addHeader(HttpHeaders.DATE, new Date().toString());
		 request.addHeader("content-type", "application/json");
		 request.addHeader("Authorization", "Bearer "+token);

		 request.setConfig(reqConfig());
		return getResponse(request);
	}

	private static String addUser(String token) throws UnsupportedEncodingException {
		HttpPost request = new HttpPost("https://localhost:9091/api/adduser");
		 request.addHeader(HttpHeaders.DATE, new Date().toString());
		 request.addHeader("content-type", "application/json");
		 request.addHeader("Accept", "application/xml");
		 request.addHeader("Authorization", "Bearer "+token);

		 
		 StringBuilder json = new StringBuilder();
	        json.append("{");
	        json.append("\"name\":\"Dharmendra\",");
	        json.append("\"username\":\"dkumar9\",");
	        json.append("\"password\":\"dkumar\"");
	        
	        json.append("}");
	        // send a JSON data
	     
	    request.setEntity(new StringEntity(json.toString()));
		request.setConfig(reqConfig());
		
		return getResponse(request);
		
	}

	private static RequestConfig reqConfig() {
		RequestConfig requestConfig = RequestConfig.custom()
		            .setConnectionRequestTimeout(30000)
		            .setConnectTimeout(30000)
		            .setSocketTimeout(30000)
		            .build();
		return requestConfig;
	}

	private static String getResponse(HttpRequestBase request) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
		         CloseableHttpResponse response = httpClient.execute(request)) {
		        // Get HttpResponse Status
		        System.out.println("Status Code: "+response.getStatusLine().getStatusCode());   // 200

		        HttpEntity entity = response.getEntity();
		        if (entity != null) {
		            // return it as a String
		            String result = EntityUtils.toString(entity);
		            return result;
		        }

		    }catch (Exception e) {
		    	e.printStackTrace();
			}
		return null;
	}

}

