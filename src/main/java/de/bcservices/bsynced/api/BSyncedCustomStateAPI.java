package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.API_PATH_CUSTOM_STATES;
import static de.bcservices.bsynced.api.Constants.APPLICATION_JSON;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 * BSyncedCustomStateAPI represents the API resource for custom state.
 *
 * Note: it is necessary to close the {@link CloseableHttpResponse} manually.
 *
 * Example usage:
 *
 * <pre>
 * {@code
 *
 *     BSyncedAPIContext apiContext = new BSyncedAPIContext(API_TOKEN);
 *
 *     BSyncedCustomStateAPI customStateApi = new BSyncedCustomStateAPI(apiContext);
 *
 *     CloseableHttpResponse response = customStateAPI.getCustomState("LAST_REQUESTED_TIME");
 *
 *     assert response.getStatusLine().getStatusCode() == 200;
 *
 *     response.close();
 *
 * }
 * </pre>
 */
public class BSyncedCustomStateAPI {

    private BSyncedAPIContext apiContext;

    /**
     *
     * Default constructor for custom state API resource
     *
     * @param apiContext - underlying {@link BSyncedAPIContext}
     */
    public BSyncedCustomStateAPI(BSyncedAPIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * Use HTTP PUT to update given custom state in b-synced
     *
     * @param kind  - key to be updated
     * @param value - new value to update
     * @return the HTTP response from PUT request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse putCustomState(String kind, String value) throws BSyncedException {
        try {
            StringEntity entity = new StringEntity(
                    "{" + "\"kind\": \"" + kind + "\"," + "\"data\": \"" + value + "\"" + "}");
            return this.apiContext.put(this.apiContext.getPath() + API_PATH_CUSTOM_STATES, entity, APPLICATION_JSON,
                    new ArrayList<Header>());
        } catch (UnsupportedEncodingException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * Use HTTP GET to get the requested custom state in b-synced
     *
     * @param kind - key of the custom state
     * @return the HTTP response from GET request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getCustomState(String kind) throws BSyncedException {
        return this.apiContext.get(this.apiContext.getPath() + API_PATH_CUSTOM_STATES + "?kind=" + kind,
                new ArrayList<Header>());
    }

    /**
     * Use HTTP POST to create custom state in b-synced
     *
     * @param kind  Key
     * @param value Value to be stored associated with the given key
     * @return the HTTP response from POST message request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse postCustomState(String kind, String value) throws BSyncedException {

        try {
            StringEntity entity = new StringEntity(
                    "{" + "\"kind\": \"" + kind + "\"," + "\"data\": \"" + value + "\"" + "}");
            return this.apiContext.post(this.apiContext.getPath() + API_PATH_CUSTOM_STATES, entity, APPLICATION_JSON,
                    new ArrayList<Header>());
        } catch (UnsupportedEncodingException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

}
