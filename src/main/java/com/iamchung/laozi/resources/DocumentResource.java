package com.iamchung.laozi.resources;

import com.iamchung.laozi.db.dao.DocumentDao;
import com.iamchung.laozi.db.models.Document;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {

    final static Logger LOGGER = LoggerFactory.getLogger(DocumentResource.class);

    final DocumentDao documentDao;

    public DocumentResource(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @GET
    @Path("/{documentId}")
    @UnitOfWork
    public Response getDocument(@PathParam("documentId") int documentId) {
        Document document = documentDao.findById(documentId);
        return Response.ok(document).build();
    }
}
