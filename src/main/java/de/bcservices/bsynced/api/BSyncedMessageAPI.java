package de.bcservices.bsynced.api;

import static de.bcservices.bsynced.api.Constants.API_PATH_GDSN;
import static de.bcservices.bsynced.api.Constants.API_PATH_GDSN_MESSAGE;
import static de.bcservices.bsynced.api.Constants.APPLICATION_XML;
import static de.bcservices.bsynced.api.Constants.CHARSET_UTF_8;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;

import de.bcservices.bsynced.exception.BSyncedException;

/**
 * BSyncedMessageAPI represents the API resource for GDSN Messages.
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
 *     BSyncedMessageAPI messageApi = new BSyncedMessageAPI(apiContext);
 *
 *     CloseableHttpResponse response = messageApi.postGdsnMessage(GDSN_MESSAGE_XML);
 *
 *     assert response.getStatusLine().getStatusCode() == 201;
 *
 *     response.close();
 *
 * }
 * </pre>
 *
 */
public class BSyncedMessageAPI {

    private BSyncedAPIContext apiContext;

    /**
     * Default constructor for message API resource
     * @param apiContext - underlying {@link BSyncedAPIContext}
     */
    public BSyncedMessageAPI(BSyncedAPIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * This method is used to retrieve a message with the given message ID.
     *
     * @param messageID ID of the message to be retrieved
     * @return the HTTP response which contains the XML message in the body
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getMessageByID(String messageID) throws BSyncedException {
        return this.apiContext.get(this.apiContext.getPath() + API_PATH_GDSN_MESSAGE + "/" + messageID,
                new ArrayList<Header>());
    }

    /**
     * This method is used to retrieve message collection with the given parameters.
     *
     * @param direction Either "inbound" or "outbound"
     * @param messageType One of the GDSN messages type
     * @param page Page number
     * @param perPage Limit of entries per page
     * @param from The timestamp from which the messages are included
     * @param to The timestamp to which the messages are included
     * @param order Either "sender", "receiver" or "submitted_at"
     * @param orderBy Either "ASC" or "DESC"
     * @param query The text to be searched within the XML messages
     * @return the HTTP response from GET message collection request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getMessageCollection(String direction, String messageType, int page, int perPage,
            String from, String to, String order, String orderBy, String query)
            throws BSyncedException {
        String path = this.apiContext.getPath() + API_PATH_GDSN + direction + "/messages";

        path += "/?page=" + page + "&perPage=" + perPage;

        if (messageType != null && !messageType.isEmpty()) {
            path += "/?messageType=" + messageType;
        }
        if (from != null && !from.isEmpty()) {
            path += "&from=" + from;
        }
        if (to != null && !to.isEmpty()) {
            path = path + "&to=" + to;
        }
        if (order != null && !order.isEmpty()) {
            path = path + "&order=" + order;
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            path = path + "&by=" + orderBy;
        }
        if (query != null && !query.isEmpty()) {
            try {
                path = path + "&q=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new BSyncedException(e.getMessage(), e);
            }
        }
        return this.apiContext.get(path, new ArrayList<Header>());
    }

    /**
     * Use HTTP POST to create GDSN Message to messages API of b-synced.
     *
     * @param xml XML message in String
     * @return the HTTP response from POST message request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse postGdsnMessage(String xml) throws BSyncedException {
        StringEntity entity = new StringEntity(xml, Charset.forName(CHARSET_UTF_8));

        CloseableHttpResponse response = this.apiContext.post(this.apiContext.getPath() + API_PATH_GDSN_MESSAGE, entity,
                APPLICATION_XML, new ArrayList<Header>());
        return response;
    }

    /**
     * Use HTTP POST to create GDSN Message to messages API of b-synced, it
     * internally calls {@link #postGdsnMessage(String) postGdsnMessage}.
     *
     * @param file XML message in File
     * @return the HTTP response from the POST message request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse postGdsnMessage(File file) throws BSyncedException {
        String body;
        try {
            body = FileUtils.readFileToString(file, Charset.forName(CHARSET_UTF_8));
            return postGdsnMessage(body);
        } catch (IOException e) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }

    /**
     * This method is used to retrieve the messages count. Retrieve the count of
     * inbound/outbound messages for the company of the current user. If both
     * fromTimestamp and toTimestamp are not included, the total inbound/outbound
     * message count is returned
     * @param direction Either "inbound" or "outbound"
     * @param from The timestamp from which the messages are included
     * @param to The timestamp to which the messages are included
     * @return the HTTP response from the GET request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getMessageCount(String direction, String from, String to)
            throws BSyncedException {
        String messagespath = this.apiContext.getPath() + API_PATH_GDSN + direction + "/messages/count/?";

        if (from != null && !from.isBlank()) {
            messagespath += "from" + from + "&";
        }
        if (to != null && !to.isBlank()) {
            messagespath += "to" + to + "&";
        }
        CloseableHttpResponse response = this.apiContext.get(messagespath, new ArrayList<Header>());
        return response;
    }

    /**
     * This method is used to retrieve the current processing status of a specific message, given a message ID.
     * @param msgId - ID of the message which the processing status is requested
     * @return the HTTP response from the GET request
     * @throws BSyncedException Any exceptions encountered
     */
    public CloseableHttpResponse getMessageProcessingStatus(String msgId) throws BSyncedException {
        try {
            String messagespath = this.apiContext.getPath() + API_PATH_GDSN_MESSAGE + "/processing_status/?id=" + URLEncoder.encode(msgId, StandardCharsets.UTF_8.toString());
            CloseableHttpResponse response = this.apiContext.get(messagespath, new ArrayList<Header>());
            return response;
        } catch (UnsupportedEncodingException e ) {
            throw new BSyncedException(e.getMessage(), e);
        }
    }
}
