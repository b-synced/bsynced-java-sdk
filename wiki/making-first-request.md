# Making first request

You can follow the following procedure to make your first request.

1. Log in to b-synced [Dashboard](https://app.b-synced.io/user) and create your API Token

```java
// Create and fetch API Token from b-synced Dashboard
String API_TOKEN = "VVLNhyuMGvE4o5nj5yqc";
```

2. Create a corresponding `BSyncedAPIContext` object, which is the base for every API resource

```java
BSyncedAPIContext apiContext = new BSyncedAPIContext(API_TOKEN);
```

3.  Use a API Resource object you are interested in e.g. `BSyncedMessagesAPI`

```java
BSyncedMessagesAPI messagesAPI = new BSyncedMessagesAPI(apiContext);
```

4. Make your first call!

```java
// Test that the corresponding API methods can be used, e.g. messages count
CloseableHttpResponse countResponse =  messagesAPI.getMessageCount("inbound",
                                                                    "2019-01-30",
                                                                    "2019-10-31");

String responseBody = IOUtils.toString(countResponse.getEntity().getContent(), StandardCharsets.UTF_8);

countResponse.close();

// For debug
System.out.println(responseBody); // {"status":"OK","count":0}

JSONObject responseJSON = new JSONObject(responseBody);

assert responseJSON.getInt("count") >= 0;
```

Full code:

```java
package de.bctechnologies.b_synced.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.junit.Test;

import de.bctechnologies.b_synced.exception.BSyncedException;

public class FullExample {

    @Test
    public void example() throws UnsupportedOperationException, IOException {
        // 1) Create and fetch API Token from b-synced Dashboard
        String API_TOKEN = "VVLNhyuMGvE4o5nj5yqc";

        try {
            // 2) Create a BSyncedAPIContext object
            BSyncedAPIContext apiContext = new BSyncedAPIContext(API_TOKEN);

            // 3) Use an API Resource object you are interested in e.g. BSyncedMessagesAPI
            BSyncedMessagesAPI messagesAPI = new BSyncedMessagesAPI(apiContext);

            // 4) Test that the corresponding API methods can be used, e.g. messages count
            CloseableHttpResponse countResponse =  messagesAPI.getMessageCount("inbound", "2019-01-30", "2019-10-31");

            String responseBody = IOUtils.toString(countResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            countResponse.close();

            System.out.println(responseBody);

            JSONObject responseJSON = new JSONObject(responseBody);

            assert responseJSON.getInt("count") >= 0;
        } catch (BSyncedException e) {
            e.printStackTrace();
        }

    }

}

```
