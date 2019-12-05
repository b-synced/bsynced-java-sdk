package de.bcservices.bsynced.api;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.bcservices.bsynced.api.BSyncedAPIContext;
import de.bcservices.bsynced.api.BSyncedMessageAPI;
import de.bcservices.bsynced.exception.BSyncedException;

public class BSyncedAPIMessagesTest {

    BSyncedAPIContext apiContext;
    String direction;
    String messageType;
    int page;
    int perpage;
    String from;
    String to;
    String order;
    String by;
    String query;
    String messageid;
    BSyncedMessageAPI messageApi;

    @Before
    public void init() throws BSyncedException {
        direction = System.getProperty("direction") != null ? System.getProperty("direction") : "outbound";
        messageType = System.getProperty("messageType");
        page = (System.getProperty("page") != null && !"".equalsIgnoreCase(System.getProperty("page")))
                ? Integer.parseInt(System.getProperty("page"))
                : 1;
        perpage = (System.getProperty("perpage") != null && !"".equalsIgnoreCase(System.getProperty("perpage")))
                ? Integer.parseInt(System.getProperty("perpage"))
                : 20;
        from = System.getProperty("from");
        to = System.getProperty("to");
        order = System.getProperty("order") != null ? System.getProperty("order") : "submitted_at";
        by = System.getProperty("by") != null ? System.getProperty("by") : "DESC";
        query = System.getProperty("query");
        messageid = System.getProperty("id");
        apiContext = new BSyncedAPIContext("localhost", "3000", "VVLNhyuMGvE4o5nj5yqc");
        messageApi = new BSyncedMessageAPI(apiContext);
    }

    @Test
    public void testGetMessages() throws BSyncedException, UnsupportedOperationException, IOException {

        HttpResponse getResponse = messageApi.getMessageCollection(direction, messageType, page, perpage, from, to,
                order, by, query);

        assert getResponse.getStatusLine().getStatusCode() == 200;
        String bodyInString = IOUtils.toString(getResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject jsonBody = new JSONObject(bodyInString);
        JSONArray messageArray = jsonBody.getJSONArray("data");
        assert messageArray.length() >= 0;
        System.out.println("Total Messages : " + messageArray.length());
        System.out.println("data: [");
        int count = 0;
        for (Object entry : messageArray) {
            count++;
            System.out.println("{");
            JSONObject jSONObject = (JSONObject) entry;
            System.out.println("id: " + jSONObject.getString("id"));
            System.out.println("type: " + jSONObject.getString("kind"));
            System.out.println("sender: " + jSONObject.getString("sender"));
            System.out.println("receiver: " + jSONObject.getString("receiver"));
            System.out.println("submittedAt: " + jSONObject.getString("submittedAt"));
            // direction,generatedAt are not returned in response when -Dquery=LOGOCOS
            /*
             * if(query != null) {
             * System.out.println("snippet: "+jSONObject.getString("snippet")); } else {
             * System.out.println("direction: "+jSONObject.getString("direction"));
             * System.out.println("generatedAt: "+jSONObject.getString("generatedAt")); }
             */
            if (count < messageArray.length()) {
                System.out.println("},");
            } else {
                System.out.println("}");
            }
        }
        System.out.println("]");
    }

    @Test
    public void testGetMessageByID() throws Exception {
        if (messageid == null) {
            HttpResponse getResponse = messageApi.getMessageCollection("outbound", null, 1, 20, null, null,
                    "submitted_at", "DESC", null);
            assert getResponse.getStatusLine().getStatusCode() == 200;
            String bodyInString = IOUtils.toString(getResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            JSONObject jsonBody = new JSONObject(bodyInString);
            JSONArray messageArray = jsonBody.getJSONArray("data");
            assert messageArray.length() > 0;
            for (Object entry : messageArray) {
                JSONObject jSONObject = (JSONObject) entry;
                System.out.println(jSONObject.getString("id"));
            }
            JSONObject entry = messageArray.getJSONObject(0);
            messageid = entry.getString("id");
        }
        HttpResponse obj = messageApi.getMessageByID(messageid);
        String msgbodyInString = IOUtils.toString(obj.getEntity().getContent(), StandardCharsets.UTF_8);
        System.out.println("***********GS1Respone Message ***********");
        System.out.println(msgbodyInString);
        assertNotNull(msgbodyInString);
    }

}
