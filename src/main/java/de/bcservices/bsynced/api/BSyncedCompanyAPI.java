package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.API_PATH_COMPANY;
import static de.bcservices.bsynced.api.Constants.APPLICATION_JSON;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 * BSyncedCompanyAPI represents the API resource for company.
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
 *     BSyncedCompanyAPI companyApi = new BSyncedCompanyAPI(apiContext);
 *
 *     CloseableHttpResponse response = companyApi.getCompany();
 *
 *     assert response.getStatusLine().getStatusCode() == 200;
 *
 *     response.close();
 *
 * }
 * </pre>
 */
public class BSyncedCompanyAPI {

    private BSyncedAPIContext apiContext;

    /**
     * Default constructor for company API resource
     * @param apiContext - underlying {@link BSyncedAPIContext}}
     */
    public BSyncedCompanyAPI(BSyncedAPIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * This method is used to retrieve current company
     *
     * @return the HTTP response containing current company in the body
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getCompany() throws BSyncedException {
        return this.apiContext.get(this.apiContext.getPath() + API_PATH_COMPANY + "/myself", new ArrayList<Header>());
    }

    /**
     * This method is used for updating company name
     *
     * @param companyId Current company ID
     * @param companyName New name to be updated
     * @return the HTTP response from PUT request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse updateCompanyName(String companyId, String companyName) throws BSyncedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("new_name", companyName);
        StringEntity entity;
        try {
            entity = new StringEntity(jsonObject.toString());
            return this.apiContext.put(this.apiContext.getPath() + API_PATH_COMPANY + companyId + "/names", entity,
                    APPLICATION_JSON, new ArrayList<Header>());
        } catch (UnsupportedEncodingException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * This method is used for updating company address
     *
     * @param companyId Current company ID
     * @param companyAddress New address to be updated
     * @return the HTTP response from PUT request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse updateCompanyAddress(String companyId, String companyAddress) throws BSyncedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("new_address", companyAddress);
        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            return this.apiContext.put(this.apiContext.getPath() + API_PATH_COMPANY + companyId + "/addresses", entity,
                    APPLICATION_JSON, new ArrayList<Header>());
        } catch (UnsupportedEncodingException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }
}
