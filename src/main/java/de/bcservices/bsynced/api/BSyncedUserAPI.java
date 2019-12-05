package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.API_PATH_USER;
import static de.bcservices.bsynced.api.Constants.APPLICATION_JSON;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 * BSyncedUserAPI represents the API resource for current API user.
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
 *     BSyncedUserAPI userApi = new BSyncedUserAPI(apiContext);
 *
 *     CloseableHttpResponse response = userApi.getUser();
 *
 *     assert response.getStatusLine().getStatusCode() == 200;
 *
 *     response.close();
 *
 * }
 * </pre>
 *
 */
public class BSyncedUserAPI {

    private BSyncedAPIContext apiContext;

    public BSyncedUserAPI(BSyncedAPIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * This method is used to retrieve current user
     *
     * @return HTTP response with the body containing the information of current user
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getUser() throws BSyncedException  {
        return this.apiContext.get(this.apiContext.getPath() + API_PATH_USER + "/myself", new ArrayList<Header>());
    }

    /**
     * This method is used to update the name of current user
     *
     * @param name A new name to be udpated
     * @return HTTP response with PUT request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse updateUserName(String name) throws BSyncedException  {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        StringEntity entity;
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
        return this.apiContext.put(this.apiContext.getPath() + API_PATH_USER + "/names", entity, APPLICATION_JSON, new ArrayList<Header>());
    }
}
