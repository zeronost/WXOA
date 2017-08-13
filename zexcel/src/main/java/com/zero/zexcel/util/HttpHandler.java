package com.zero.zexcel.util;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HttpHandler {

	static final Logger logger = Logger.getLogger(HttpHandler.class);
	public static final String CHARSET_UTF_8 = "utf-8";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
	private static PoolingHttpClientConnectionManager pool;
	private static RequestConfig requestConfig;

	static {
		try {
			//setProxy();
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();

			pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			pool.setMaxTotal(200);
			pool.setDefaultMaxPerRoute(3);
			int socketTimeout = 10000;
			int connectTimeout = 10000;
			int connectionRequestTimeout = 10000;
			requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout)
					.setConnectTimeout(connectTimeout)
					.build();
		} catch (Exception e) {
			logger.error("Init HttpHandler error ", e);
		}
		requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000)
				.setConnectionRequestTimeout(100000).build();
	}

	public static CloseableHttpClient getHttpClient() {
		//System.setProperty("javax.net.ssl.trustStore", "F:\\Program Files\\Java\\jdk1.8.0_111\\jre\\lib\\security");
		CloseableHttpClient httpClient = HttpClients.custom()
				.setProxy(HttpHost.create("222.175.126.51"))
				// 设置连接池管理
				.setConnectionManager(pool)
				// 设置请求配置
				.setDefaultRequestConfig(requestConfig)
				// 设置重试次数
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
		return httpClient;
	}

	public static void main(String[] args) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(new AuthScope("222.175.126.51",80), 
					new UsernamePasswordCredentials("li_kming","lkm123"));
			httpClient = HttpClients.custom()
					// 设置连接池管理
					.setConnectionManager(pool)
					// 设置请求配置
					.setDefaultRequestConfig(requestConfig)
					// 设置重试次数
					.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
	                .setDefaultCredentialsProvider(provider).build();
			HttpHost target = new HttpHost("61.135.169.125",80,"http");
            HttpHost proxy = new HttpHost("222.175.126.51",80, "https");
            RequestConfig config = RequestConfig.custom()
                //.setProxy(proxy)
                .build();
//            HttpGet httpget = new HttpGet("/cvicdns/xcom/rbac/logon.do");
            HttpGet httpget = new HttpGet("/por/login_psw.csp");
            httpget.setConfig(requestConfig);
            response = httpClient.execute(proxy,httpget);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	/*public static String sendHttpPost(String httpUrl) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return sendHttpPost(httpPost);
	}*/

	public static class MyAuthenticator extends Authenticator {
		String userName;
		String password;
		
		public MyAuthenticator(String userName, String password){
			this.userName = userName;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password.toCharArray());
		}
	}
	
	private static void setProxy(){
		//设置代理服务
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "https://222.175.126.51");
		System.setProperty("https.proxyHost", "https://222.175.126.51");
		
		//设置验证权限
		MyAuthenticator auth = new MyAuthenticator("zhang_lei2","zhang_880860");
		Authenticator.setDefault(auth);
	}

}
