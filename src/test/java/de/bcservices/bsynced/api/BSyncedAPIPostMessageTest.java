package de.bcservices.bsynced.api;

import java.io.File;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedMessageAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPIPostMessageTest {

    BSyncedAPIContext apiContext;
    BSyncedMessageAPI messageApi;

    @Before
    public void init() throws BSyncedException {
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        messageApi = new BSyncedMessageAPI(apiContext);

    }

    @Test
    public void testPostGdsnMessage() throws BSyncedException {
        HttpResponse cinResponse = messageApi.postGdsnMessage(new File("src/test/cin-001.xml"));
        assert cinResponse.getStatusLine().getStatusCode() == 201;
    }
}
