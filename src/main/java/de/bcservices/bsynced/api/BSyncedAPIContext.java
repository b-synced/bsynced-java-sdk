package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.APPLICATION_JSON;
import static de.bcservices.bsynced.api.Constants.HTTP_HEADER_API_TOKEN;
import static de.bcservices.bsynced.api.Constants.HTTP_HEADER_CONTENT_TYPE;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 *
 * BSyncedAPIContext is the base for using b-synced RESTful API.
 * <p>It wraps the authentication and handles the internal HTTP(S) communication to the b-synced API server.</p>
 *
 * <p>An example usage:</p>
 * <pre>
 * {@code
 *
 *      String API_TOKEN = "VVLNhyuMGvE4o5nj5yqc";
 *      BSyncedAPIContext apiContext = new BSyncedAPIContext(API_TOKEN);
 * }
 * </pre>
 *
 */
public class BSyncedAPIContext {

    private String host = "app.b-synced.io";
    private String port = "80";
    private String protocol = "http";

    int DEFAULT_TIMEOUT = 300;

    private String apiToken;

    private RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_TIMEOUT * 1000)
            .setConnectionRequestTimeout(DEFAULT_TIMEOUT * 1000).setSocketTimeout(DEFAULT_TIMEOUT * 1000).build();

    private PoolingHttpClientConnectionManager connectionManager;

    private CloseableHttpClient httpClient;

    /**
     * Default constructor, with apiToken as parameter
     * @param apiToken API Token of the current API user
     * @throws BSyncedException Any exceptions encountered
     */
    public BSyncedAPIContext(String apiToken) throws BSyncedException {
        this.apiToken = apiToken;
        initConnectionManager();
    }

    /**
     * Constructor with , with host, port and apiToken as parameter
     * @param host In the form of "app.b-synced.io"
     * @param port In the form of "80"
     * @param apiToken API Token of the current API user
     * @throws BSyncedException Any exceptions encountered
     */
    public BSyncedAPIContext(String host, String port, String apiToken) throws BSyncedException {
        this.host = host;
        this.port = port;
        this.protocol = (this.port.equals("443")) ? "https" : "http";
        this.apiToken = apiToken;
        initConnectionManager();
    }

    public PoolingHttpClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(PoolingHttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

		public void setTimeouts(
				int connectionTimeout,
				int connectionRequestTimeout,
				int socketTimeout
				) throws 
			BSyncedException
		{
			config = RequestConfig.custom().setConnectTimeout(connectionTimeout)
            .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
			initConnectionManager();
		}

    /**
     * This method initialises the underlying
     * {@link PoolingHttpClientConnectionManager} and {@link CloseableHttpClient}.
     *
     * @throws BSyncedException Any exceptions encountered
     *
     */
    private void initConnectionManager() throws BSyncedException {
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setDefaultMaxPerRoute(20);
        this.connectionManager.setMaxTotal(20);
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new BSyncedException(e.getMessage(), e);
        }

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setConnectionManager(connectionManager)
                .setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    }

    /**
     * Wrapper for HTTP POST method.
     *
     * It adds the authentication credential automatically.
     *
     * @param path        URL of the HTTP request
     * @param entity      entity to post
     * @param contentType content type of the entity
     * @param headers     additional headers
     * @return the HTTP response from the POST request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse post(String path, HttpEntity entity, String contentType, List<Header> headers)
            throws BSyncedException {

        HttpPost postRequest = new HttpPost(path);
        postRequest.setEntity(entity);
        if (headers != null) {
            postRequest.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        postRequest.setHeader(HTTP_HEADER_CONTENT_TYPE, contentType);
        assignCrenditialToHttpRequest(postRequest);

        try {
            CloseableHttpResponse response = httpClient.execute(postRequest);
            return response;
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * Wrapper for HTTP GET method
     *
     * It adds the authentication credential automatically.
     *
     * @param path    URL of the HTTP request
     * @param headers additional headers
     * @return the HTTP response from the GET request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse get(String path, List<Header> headers) throws BSyncedException {

        HttpGet getRequest = new HttpGet(path);
        if (headers != null) {
            getRequest.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        getRequest.setHeader(HTTP_HEADER_CONTENT_TYPE, APPLICATION_JSON);
        assignCrenditialToHttpRequest(getRequest);
        try {
            CloseableHttpResponse response = httpClient.execute(getRequest);
            return response;
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * Wrapper for HTTP PUT method.
     *
     * It adds the authentication credential automatically.
     *
     * @param path        URL of the HTTP request
     * @param entity      entity to update
     * @param contentType content type of the entity
     * @param headers     additional headers
     * @return the HTTP response from the POST request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse put(String path, HttpEntity entity, String contentType, List<Header> headers)
            throws BSyncedException {

        HttpPut putRequest = new HttpPut(path);
        putRequest.setEntity(entity);
        if (headers != null) {
            putRequest.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        putRequest.setHeader(HTTP_HEADER_CONTENT_TYPE, contentType);
        assignCrenditialToHttpRequest(putRequest);

        try {
            CloseableHttpResponse response = httpClient.execute(putRequest);
            return response;
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * Get the base path of the b-synced server
     *
     * @return base path of b-synced
     */
    public String getPath() {
        return this.protocol + "://" + host + ":" + port + "/";
    }

    /**
     * This takes {@link HttpPost} request as a parameter.
     *
     * @param postRequest A custom POST request
     * @return {@link CloseableHttpResponse} response
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse executePost(HttpPost postRequest) throws BSyncedException {
        try {
            return httpClient.execute(postRequest);
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * This takes {@link HttpGet} request as a parameter.
     *
     * @param getRequest A custom GET request
     * @return {@link CloseableHttpResponse} response
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse executeGet(HttpGet getRequest) throws BSyncedException {
        try {
            return httpClient.execute(getRequest);
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    public void close() throws IOException {
        this.httpClient.close();
        this.connectionManager.close();
    }

    /**
     * Set headers of {@link HttpRequest} with credential
     * @param request HTTP request to be sent
     */
    public void assignCrenditialToHttpRequest(HttpRequest request) {
        request.setHeader(HTTP_HEADER_API_TOKEN, this.apiToken);
    }
}
