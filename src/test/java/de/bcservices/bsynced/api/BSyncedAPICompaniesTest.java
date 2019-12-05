package de.bcservices.bsynced.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedCompanyAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPICompaniesTest  {

    BSyncedAPIContext apiContext;
    BSyncedCompanyAPI companyApi;

    @Before
    public void init() throws BSyncedException {
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        companyApi = new BSyncedCompanyAPI(apiContext);
    }

    @Test
    public void testGetCompanyByID() throws BSyncedException, UnsupportedOperationException, IOException {

        HttpResponse obj = companyApi.getCompany();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject=responseJSON.getJSONObject("data");
        System.out.println("Comapny Id: " +jSONObject.getString("id"));
        System.out.println("Comapny Name: " +jSONObject.getString("name"));
        System.out.println("Comapny Address: " +jSONObject.getString("address"));
        System.out.println("Comapny GLN: " +jSONObject.getString("gln"));
        assertNotNull(jSONObject);
    }

    @Test
    public void testGetCompanyNameByID() throws BSyncedException, UnsupportedOperationException, IOException {

        HttpResponse obj = companyApi.getCompany();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject=responseJSON.getJSONObject("data");
        System.out.println("Comapny Id: " +jSONObject.getString("id"));
        System.out.println("Comapny Name: " +jSONObject.getString("name"));
        assertNotNull(jSONObject);
    }

    @Test
    public void testGetCompanyAddressByID() throws BSyncedException, UnsupportedOperationException, IOException {

        HttpResponse obj = companyApi.getCompany();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject=responseJSON.getJSONObject("data");
        System.out.println("Comapny Id: " +jSONObject.getString("id"));
        System.out.println("Comapny Address: " +jSONObject.getString("address"));
        assertNotNull(jSONObject);
    }

    @Test
    public void testUpdateCompanyName() throws BSyncedException, UnsupportedOperationException, IOException {
        HttpResponse obj = companyApi.getCompany();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject=responseJSON.getJSONObject("data");
        String companyId = jSONObject.getString("id");
        CloseableHttpResponse updateCompanyName = companyApi.updateCompanyName(companyId, "xyz");
        assertEquals(204, updateCompanyName.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateCompanyAddress() throws BSyncedException, UnsupportedOperationException, IOException {
        HttpResponse obj = companyApi.getCompany();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject=responseJSON.getJSONObject("data");
        String companyId = jSONObject.getString("id");
        CloseableHttpResponse updateCompanyName = companyApi.updateCompanyAddress(companyId, "Germany");

        assertEquals(204, updateCompanyName.getStatusLine().getStatusCode());
    }
}
