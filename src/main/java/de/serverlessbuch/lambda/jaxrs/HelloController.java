package de.serverlessbuch.lambda.jaxrs;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Path("hello")
@ApplicationScoped
public class HelloController {

    @GET
    public String sayHello() {
        return "Hello Java EE from AWS Lambda!";
    }

}
