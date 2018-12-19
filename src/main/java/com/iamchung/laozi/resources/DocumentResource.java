package com.iamchung.laozi.resources;

import com.iamchung.laozi.api.models.document.GetDocumentsRequest;
import com.iamchung.laozi.db.dao.DocumentDao;
import com.iamchung.laozi.db.models.Document;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {

    final static Logger LOGGER = LoggerFactory.getLogger(DocumentResource.class);

    final DocumentDao documentDao;

    public DocumentResource(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @POST
    @Path("/tag")
    @UnitOfWork
    public Response getDocumentsByTag(@Valid GetDocumentsRequest getDocumentsRequest) {
        List<Document> documents = documentDao.findAllByTags(getDocumentsRequest.getTagIds());
        return Response.ok(documents).build();
    }

    @GET
    @Path("/{documentId}")
    @UnitOfWork
    public Response getDocument(@PathParam("documentId") int documentId) {
        Document document = documentDao.findById(documentId);
        return Response.ok(document).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/{documentId}/download")
    @UnitOfWork
    public Response downloadDocument(@PathParam("documentId") int documentId) throws Exception {
        Document document = documentDao.findById(documentId);
        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                try {
                    byte[] data = Files.readAllBytes(Paths.get(document.getPath()));
                    outputStream.write(data);
                    outputStream.flush();
                } catch (Exception e) {
                    throw new WebApplicationException("file not found");
                }
            }
        };

        return Response.ok(streamingOutput, MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .build();
    }
}
