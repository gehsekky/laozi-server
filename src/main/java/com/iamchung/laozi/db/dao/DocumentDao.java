package com.iamchung.laozi.db.dao;

import com.iamchung.laozi.db.models.Document;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class DocumentDao extends AbstractDAO<Document> {

    public DocumentDao(SessionFactory factory) {
        super(factory);
    }

    public List<Document> findAllByTags(List<Integer> tagIds) {
        return list(namedQuery("Document.findAllByTags").setParameter("tagIds", tagIds));
    }

    public Document findById(int documentId) {
        return get(documentId);
    }

    public int create(Document document) {
        return persist(document).getDocumentId();
    }
}
