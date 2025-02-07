package helloworld;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.req.LoginRequest;
import helloworld.response.LoginResponse;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        var logger = context.getLogger();

        try {
            logger.log("Request received - " + input.getBody());
            var loginRequest = mapper.readValue(input.getBody(), LoginRequest.class);

            boolean isAuthorized = loginRequest.username().equalsIgnoreCase("admin")
                    && loginRequest.password().equalsIgnoreCase("123");

            var response = new LoginResponse(isAuthorized);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(mapper.writeValueAsString(response))
                    .withIsBase64Encoded(false);

        } catch (IOException e) {
            logger.log("Error: "+ e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}
