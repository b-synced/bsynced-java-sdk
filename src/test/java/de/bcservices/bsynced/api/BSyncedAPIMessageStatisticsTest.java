package de.bcservices.bsynced.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedMessageAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPIMessageStatisticsTest {

    BSyncedAPIContext apiContext;
    BSyncedMessageAPI messageApi;

    @Before
    public void init() throws BSyncedException {
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        messageApi = new BSyncedMessageAPI(apiContext);
    }

    @Test
    public void testGetMessageCount() throws BSyncedException, UnsupportedOperationException, IOException {

        CloseableHttpResponse obj = messageApi.getMessageCount("inbound", null, null);
        String bodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject responseJSON = new JSONObject(bodyInString);
        System.out.println("Response: " + responseJSON);
        assert responseJSON.getInt("count") >= 0;
    }
}
