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
import de.bcservices.bsynced.api.BSyncedUserAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPIUserTest {

    BSyncedAPIContext apiContext;
    BSyncedUserAPI userApi;

    @Before
    public void init() throws BSyncedException {
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        userApi = new BSyncedUserAPI(apiContext);
    }

    @Test
    public void testGetUserById() throws BSyncedException, UnsupportedOperationException, IOException {
        HttpResponse obj = userApi.getUser();
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        System.out.println(bodyInString);
        JSONObject responseJSON = new JSONObject(bodyInString);
        JSONObject jSONObject = responseJSON.getJSONObject("data");
        System.out.println("Comapny Id: " + jSONObject.getString("uid"));
        assertNotNull(jSONObject);
    }

    @Test
    public void testUpdateUserName() throws BSyncedException {
        CloseableHttpResponse obj = userApi.updateUserName("Bayard");
        assertEquals(204, obj.getStatusLine().getStatusCode());
    }
}
