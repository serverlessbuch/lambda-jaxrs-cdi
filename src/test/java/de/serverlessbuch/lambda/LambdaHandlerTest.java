package de.serverlessbuch.lambda;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.serverlessbuch.lambda.jaxrs.books.Book;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class LambdaHandlerTest {

    private static LambdaHandler handler = new LambdaHandler();
    private Context context = new MockLambdaContext();

    @Test
    public void testHello() {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/hello", "GET").build();
        AwsProxyResponse response = handler.handleRequest(request, context);
        assertEquals(200, response.getStatusCode());
        assertEquals("Hello Java EE from AWS Lambda!", response.getBody());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetBooks() throws IOException {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/books", "GET").build();
        AwsProxyResponse response = handler.handleRequest(request, context);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> books = objectMapper.readValue(response.getBody(), List.class);
        assertEquals(200, response.getStatusCode());
        assertEquals(2, books.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddInvalidBook() throws IOException {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/books", "POST").build();
        request.setBody("{\"author\":\"foo\"}");
        request.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
        AwsProxyResponse response = handler.handleRequest(request, context);
        System.out.println(response.getBody());
    }

}
