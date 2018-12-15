package com.iamchung.laozi.resources;

import com.codahale.metrics.annotation.Timed;
import com.iamchung.laozi.LaoziServerConfiguration;
import com.iamchung.laozi.api.models.GenericMessage;
import com.iamchung.laozi.api.models.ImmutableGenericMessage;
import com.iamchung.laozi.db.dao.DocumentDao;
import com.iamchung.laozi.db.dao.DocumentTagDao;
import com.iamchung.laozi.db.dao.TagDao;
import com.iamchung.laozi.db.models.Document;
import com.iamchung.laozi.db.models.DocumentTag;
import com.iamchung.laozi.db.models.Tag;
import io.dropwizard.hibernate.UnitOfWork;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * resource for admin functions
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    final static Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    final LaoziServerConfiguration laoziServerConfiguration;
    final DocumentDao documentDao;
    final TagDao tagDao;
    final DocumentTagDao documentTagDao;

    private List<String> tags;

    public AdminResource(
            LaoziServerConfiguration laoziServerConfiguration,
            DocumentDao documentDao,
            TagDao tagDao,
            DocumentTagDao documentTagDao
    ) {
        this.laoziServerConfiguration = laoziServerConfiguration;
        this.documentDao = documentDao;
        this.tagDao = tagDao;
        this.documentTagDao = documentTagDao;
    }

    @GET
    @Timed
    @Path("/scan")
    @UnitOfWork
    public Response scan() {

        try {
            // reset seen tags list
            this.tags = new ArrayList<>();

            // scan directory
            walkFileTree(this.laoziServerConfiguration.getDocumentRoot(), new ArrayList<>());
            GenericMessage genericMessage = ImmutableGenericMessage.builder()
                    .message("initializing scan")
                    .build();
            return Response.ok(genericMessage).build();
        } catch (Exception e) {
            LOGGER.error("Error during admin file scan", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }

    }

    /**
     * walk file tree and create records
     * @param rootPath
     * @param currentTags
     * @throws Exception
     */
    private void walkFileTree(String rootPath, List<Tag> currentTags) throws Exception {
        DirectoryStream directoryStream = Files.newDirectoryStream(Paths.get(rootPath));
        directoryStream.forEach(streamPath -> {
            String path = streamPath.toString();
            String name = path.substring(path.lastIndexOf("\\") + 1);
            // check if path is dir
            if (Files.isDirectory(Paths.get(path))) {
                // make tag
                if (!tags.contains(name)) {
                    Tag tag = new Tag(name, LocalDateTime.now(), LocalDateTime.now(), 1, 1);
                    tag.setTagId(tagDao.create(tag));
                    tags.add(name);
                    currentTags.add(tag);
                }

                // recurse
                try {
                    walkFileTree(path, currentTags);
                } catch (Exception e) {
                    LOGGER.error("Something went wrong during tree walk", e);
                }
            } else {
                // make file
                Document document = new Document(name, path, LocalDateTime.now(), LocalDateTime.now(), 1, 1);
                int newDocId = documentDao.create(document);

                // make file tags
                currentTags.forEach(tag -> {
                    DocumentTag documentTag = new DocumentTag(newDocId, tag.getTagId());
                    documentTagDao.create(documentTag);
                });
            }
        });
    }

}
