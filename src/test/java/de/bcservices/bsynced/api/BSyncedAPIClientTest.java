package de.bcservices.bsynced.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.routing.HttpRoute;
import org.json.JSONObject;
import org.junit.Before;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedCustomStateAPI;
import de.bcservices.bsynced.api.BSyncedMessageAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPIClientTest {

    BSyncedAPIContext apiContext;
    BSyncedMessageAPI messageApi;
    BSyncedCustomStateAPI customStateApi;

    @Before
    public void init() throws BSyncedException {
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        messageApi = new BSyncedMessageAPI(apiContext);
        customStateApi = new BSyncedCustomStateAPI(apiContext);
    }

    //@Test
    public void testPostMessages() throws BSyncedException, IOException {
        for (int i = 0; i <= 20; i++) {
            CloseableHttpResponse response = messageApi.postGdsnMessage(new File("src/test/cin-001.xml"));
            for (HttpRoute route : apiContext.getConnectionManager().getRoutes()) {
                System.out.println(route.getTunnelType() + " " + apiContext.getConnectionManager().getStats(route));
            }
            System.out.println(response.getEntity().toString());
            System.out.println(i + " : " + response.getStatusLine().getStatusCode());
            response.close();
            assert response.getStatusLine().getStatusCode() == 422;
        }
    }
}
