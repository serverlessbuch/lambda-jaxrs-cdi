package de.serverlessbuch.lambda;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.environment.se.Weld;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private boolean isInitialized = false;

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (!isInitialized) {
            Weld weld = new Weld();
            weld.initialize();

            ResourceConfig jerseyApplication = new ResourceConfig()
                    .packages("de.serverlessbuch.lambda.jaxrs")
                    .register(JacksonFeature.class);
            handler = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

            isInitialized = true;
        }
        return handler.proxy(awsProxyRequest, context);
    }

}
