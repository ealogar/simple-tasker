package org.dropwizard.core;

import java.lang.StackWalker.Option;
import java.util.Date;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.dropwizard.api.Task;

import io.dropwizard.jersey.errors.ErrorMessage;

public class JacksonExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    public static final String DEFAULT_ERROR_MESSAGE = "Invalid JSON format";

    private String decideErrorMessage(JacksonException ex) {

        switch (ex.getClass().getSimpleName()) {
            case "InvalidFormatException":
                return Optional.of(ex)
                        .map(InvalidFormatException.class::cast)
                        .map(InvalidFormatException::getLocalizedMessage)
                        .filter(m -> {
                            if (m.contains("due_date")) {
                                return true;
                            }
                            return false;
                        })
                        .map(m -> "Invalid date format at due_date, expected " + Task.DATE_FORMAT_PATTERN)
                        .orElse(DEFAULT_ERROR_MESSAGE);
            case "JsonParseException":
                return "Invalid JSON format";

            default:
                return "";
        }

    }

    @Override
    public Response toResponse(JsonProcessingException exception) {

        String errorMessage = decideErrorMessage(exception);

        return Response
                .status(Status.BAD_REQUEST)
                .entity(
                        new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), errorMessage))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
