package org.dropwizard.core;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.JerseyViolationException;

public class JerseryValidationsExceptionMapper implements ExceptionMapper<JerseyViolationException> {

    @Override
    public Response toResponse(JerseyViolationException exception) {
        //FIXME: Get error message as in JerseyViolationExceptionMapper
        return Response
        .status(Status.BAD_REQUEST)
        .entity(
            new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), exception.getLocalizedMessage())
            )
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
    }
    
}
