package Step;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ConnectionParam {
	public CloseableHttpClient GetConnectionParam() throws KeyManagementException, NoSuchAlgorithmException
	{
		//Below code is for turning the SSL Certifications off in the connection.
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		TrustManager[] trustManager = new TrustManager[] {
		    new X509TrustManager() {
		       public X509Certificate[] getAcceptedIssuers() {
		           return new X509Certificate[0];
		       }
		       public void checkClientTrusted(X509Certificate[] certificate, String str) {}
		       public void checkServerTrusted(X509Certificate[] certificate, String str) {}
		    }
		};
		context.init(null, trustManager, new SecureRandom());

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context,
		        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
//		CredentialsProvider provider = new BasicCredentialsProvider();
//		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("xxxxxxx","xxxxxxxx");
//		provider.setCredentials(AuthScope.ANY, credentials);
//		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		
		//Below code is for Http Client 
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50 * 1000).setConnectionRequestTimeout(50 * 1000).setSocketTimeout(120 * 1000).build();
//		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).setSSLSocketFactory(socketFactory).setDefaultRequestConfig(requestConfig).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).setDefaultRequestConfig(requestConfig).build();
				

		return httpClient;
		
	}

}
