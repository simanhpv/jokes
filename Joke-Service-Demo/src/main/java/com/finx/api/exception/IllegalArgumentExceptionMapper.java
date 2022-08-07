package com.finx.api.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import io.dropwizard.jersey.errors.ErrorMessage;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    private final Meter exceptions;
    public IllegalArgumentExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter("exceptions");
    }
    @Override
    public Response toResponse(IllegalArgumentException e) {
        exceptions.mark();
        return Response.status(Status.BAD_REQUEST)
                .header("x-bad-request", "true")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(Status.BAD_REQUEST.getStatusCode(),
                        "You passed an illegal argument!"))
                .build();
    }
}