package de.serverlessbuch.lambda;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.serverlessbuch.lambda.jaxrs.ValidationExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.jboss.weld.environment.se.Weld;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private boolean isInitialized = false;

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (!isInitialized) {
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();

            Weld weld = new Weld();
            weld.initialize();

            ResourceConfig jerseyApplication = new ResourceConfig()
                    .packages("de.serverlessbuch.lambda.jaxrs")
                    .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                    .register(ValidationExceptionMapper.class)
                    .register(JacksonFeature.class);
            handler = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

            isInitialized = true;
        }
        return handler.proxy(awsProxyRequest, context);
    }

}
