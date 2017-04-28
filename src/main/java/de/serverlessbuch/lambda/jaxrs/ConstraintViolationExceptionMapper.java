package de.serverlessbuch.lambda.jaxrs;

import org.glassfish.jersey.server.validation.internal.ValidationHelper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException violation) {

        List<String> messages = violation.getConstraintViolations().stream()
                .map(v -> String.format("%s (path: %s)", v.getMessage(), v.getPropertyPath()))
                .collect(Collectors.toList());
        int statusCode = ValidationHelper.getResponseStatus(violation).getStatusCode();

        return Response.status(statusCode)
                .entity(messages)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
