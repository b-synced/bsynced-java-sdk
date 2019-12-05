package de.bcservices.bsynced.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.junit.Test;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedMessageAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class FullExample {

    @Test
    public void example() throws UnsupportedOperationException, IOException {
        String B_SYNCED_HOST = "localhost";
        String B_SYNCED_PORT = "3000";

        // 1) Create and fetch API Token from b-synced Dashboard
        String API_TOKEN = "VVLNhyuMGvE4o5nj5yqc";

        try {
            // 2) Create a BSyncedAPIContext object
            BSyncedAPIContext apiContext = new BSyncedAPIContext(B_SYNCED_HOST, B_SYNCED_PORT, API_TOKEN);

            // 3) Use an API Resource object you are interested in e.g. BSyncedMessagesAPI
            BSyncedMessageAPI messagesAPI = new BSyncedMessageAPI(apiContext);

            // 4) Test that the corresponding API methods can be used, e.g. messages count
            CloseableHttpResponse countResponse = messagesAPI.getMessageCount("inbound", "2019-01-30", "2019-10-31");

            String responseBody = IOUtils.toString(countResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            countResponse.close();

            // {"status":"OK","count":0}
            System.out.println(responseBody);

            JSONObject responseJSON = new JSONObject(responseBody);

            assert responseJSON.getInt("count") >= 0;
        } catch (BSyncedException e) {
            e.printStackTrace();
        }

    }

}
