package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.API_PATH_USER;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 * BSyncedAuthAPI represents the API resource for authentication.
 *
 * Example usage:
 *
 * <pre>
 * {@code
 *
 *     BSyncedAPIContext apiContext = new BSyncedAPIContext(API_TOKEN);
 *
 *     BSyncedAuthAPI authApi = new BSyncedAuthAPI(apiContext);
 *
 *     CloseableHttpResponse response = authApi.validateApiToken();
 *
 *     assert response.getStatusLine().getStatusCode() == 200;
 *
 *     response.close();
 *
 * }
 * </pre>
 */
public class BSyncedAuthAPI {

    private BSyncedAPIContext apiContext;

    /**
     * Default constructor for auth API resource
     * @param apiContext - underlying {@link BSyncedAPIContext}
     */
    public BSyncedAuthAPI(BSyncedAPIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * Validate current API Token
     *
     * @return HTTP response from validate API token request
     * @throws BSyncedException Any exceptions encountered
     */
    public HttpResponse validateApiToken() throws BSyncedException {
        HttpGet getRequest = new HttpGet(this.apiContext.getPath() + API_PATH_USER + "myself");
        this.apiContext.assignCrenditialToHttpRequest(getRequest);

        CloseableHttpResponse response = this.apiContext.executeGet(getRequest);
        try {
            response.close();
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
        return response;

    }
}
