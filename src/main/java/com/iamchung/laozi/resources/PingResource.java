package com.iamchung.laozi.resources;

import com.codahale.metrics.annotation.Timed;
import com.iamchung.laozi.api.models.GenericMessage;
import com.iamchung.laozi.api.models.ImmutableGenericMessage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @GET
    @Timed
    public Response ping() {
        GenericMessage genericMessage = ImmutableGenericMessage.builder()
                .message("pong")
                .build();
        return Response.ok(genericMessage).build();
    }
}
