
# Integrating the b-synced-api-client into the source project.

Have to download the source code for b-synced-api-client from the  https://bitbucket-id/bctechnologies/b-synced-java-api-client and have to build using maven that will give us a JAR file.

Maven command to build b-synced-api-client code
```
	mvn clean install
```

### After that have to run the following command

```
mvn install:install-file -Dfile=<jar file path>/api-1.0.0.jar -DgroupId=de.bctechnologies -DartifactId=api -Dversion=1.0.0 -Dpackaging=jar

```

The above command will add jar file to our local maven repository

Then add the following dependency to our source project

```
	<dependency>
		<groupId>de.bctechnologies</groupId>
		<artifactId>api</artifactId>
		<version>1.0.0</version>
	</dependency>
```

The above dependency will get downloaded into the our project and from here we can start working with the b-synced-client.

## Usage of the BsyncedAPIClient jar

## Code snippet of BSyncedAPIClient

```
	public class BSyncedAPIClient {
		/**
	     * Initialise {@link BSyncedAPIClient} with URL and user credentials.
	     * {@link BSyncedAPIClient#auth()} should be called before making the first HTTP request.
	     *
	     * @param host default is "app.b-synced.io"
	     * @param port default is "80"
	     * @param uid in the form of "user1@examp1e.com"
	     * @param password in plain text
	     * @throws NoSuchAlgorithmException
	     * @throws KeyStoreException
	     * @throws KeyManagementException
	     */
	    public BSyncedAPIClient(String host, String port, String uid, String password) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		this.apiContext = new BSyncedAPIContext(host, port, uid, password);

	    }
	}

```
### For creating object for BSyncedAPIClient

we have to pass the user credentials for the b-synced and host and port

```
	BSyncedAPIClient client = new BSyncedAPIClient('host','port','uid','password');

	access-token: efjvoJSNEYShYlBhRUdBTA
	client: Hg8m7NzQEFGVkiUgLAIVXkNokg
	uid: username
	password: password
```

### If we want to pulish CIN message to the b-synced

##### Endpoints to the b-synced for posting a CIN message.

```
	. Production environment: https://app.b-synced.io/api/v1/gdsn/messages/
	. Test environment: https://test.b-synced.io/api/v1/gdsn/messages/
```

##### Method type
```
	[POST]
```

##### Headers

```
	access-token: <access-token>
	client: <client>
	uid: <uid>
	Content-type: applciation/xml
```

##### Request body

```
	Request Body: <?xml version="1.0"...> in UTF-8 encoding
```

##### Code snippet.

```
	/**
     * Wrapper for HTTP postGdsnMessage method.
     * It validates and store the credentials automatically
     *
     * @param path URL of the HTTP request
     * @param entity entity to post
     * @param contentType content type of the entity
     * @param headers additional headers
     * @return the HTTP response from the POST request
     * @throws ClientProtocolException in case of an HTTP protocol error
     * @throws IOException in case of a problem or the connection was aborted
     */
	public CloseableHttpResponse postGdsnMessage(String path, HttpEntity entity, String contentType, List<Header> headers) throws ClientProtocolException, IOException {
		HttpPost postRequest = new HttpPost(path);
		postRequest.setEntity(entity);
		if (headers != null) {
		    postRequest.setHeaders(headers.toArray(new Header[headers.size()]));
		}
		postRequest.setHeader(HTTP_HEADER_CONTENT_TYPE, contentType);
		assignCrenditialToHttpRequest(postRequest);

		CloseableHttpResponse response = httpClient.execute(postRequest);
		credential.setParamsFromHTTPResponse(response);
		return response;
        }
```
##### Response for the post GDSN message.

```
	Upon succesful we will receive 201 as status code.
	As a response body we will receive payload
	{
	  "status": "Created",
	  "id": "4260431039925/385bdfe4-f1b7-4ce6-94a7-dfahnf"
	}

```

### If we want to get the CIN message from b-synced

##### Endpoints to the b-synced for getting a CIN message.

```
	. Production environment: https://app.b-synced.io/api/v1/gdsn/messages/{id}
	. Test environment: https://test.b-synced.io/api/v1/gdsn/messages/{id}
```

##### Method type
```
	[GET]
```

##### Headers

```
	access-token: <access-token>
	client: <client>
	uid: <uid>
	Content-type: applciation/xml
```
##### Code snippet.

```
	/**
     * Wrapper for HTTP getGdsnMessage method
     *
     * It validates and store the credentials automatically
     *
     * @param path URL of the HTTP request
     * @param headers additional headers
     * @return the HTTP response from the GET request
     * @throws ClientProtocolException in case of an HTTP protocol error
     * @throws IOException in case of a problem or the connection was aborted
     */
    public CloseableHttpResponse getGdsnMessage(String path, List<Header> headers) throws ClientProtocolException, IOException {

        HttpGet getRequest = new HttpGet(path);
        if (headers != null) {
            getRequest.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        getRequest.setHeader(HTTP_HEADER_CONTENT_TYPE, APPLICATION_JSON);
        assignCrenditialToHttpRequest(getRequest);

        CloseableHttpResponse response = httpClient.execute(getRequest);
        credential.setParamsFromHTTPResponse(response);
        return response;
    }
```

##### Response for the get GDSN message from the b-synced.

```
	Upon succesful we will receive 200 as status code.
	As a response body we will receive payload
	<?xml version="1.0" encoding="UTF-8"?>
<catalogue_item_notification:catalogueItemNotificationMessage xmlns:sh="http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader"
                                                              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                              xmlns:catalogue_item_notification="urn:gs1:gdsn:catalogue_item_notification:xsd:3"
                                                              xsi:schemaLocation="http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader http://www.gs1globalregistry.net/3.1/schemas/sbdh/StandardBusinessDocumentHeader.xsd urn:gs1:gdsn:catalogue_item_notification:xsd:3 http://www.gs1globalregistry.net/3.1/schemas/gs1/gdsn/CatalogueItemNotification.xsd">
   <sh:StandardBusinessDocumentHeader>
...


```


##### Like wise BSyncedAPIClient is offering so many services we can use them. The BSyncedAPIClient is offering the following services.

```
	1) postCustomState
	2) getCustomState
	3) putCustomState
	4) getCompanyByID
	5) getUserByID
	6) updateCompanyName
	7) updateCompanyAddress
	8) updateUserName
	9) getMessageCollection
	10) getMessageCount

```
