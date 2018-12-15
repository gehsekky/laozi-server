package com.iamchung.laozi.db.dao;

import com.iamchung.laozi.db.models.DocumentTag;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class DocumentTagDao extends AbstractDAO<DocumentTag> {

    public DocumentTagDao(SessionFactory factory) {
        super(factory);
    }

    public DocumentTag findById(int documentTagId) {
        return get(documentTagId);
    }

    public int create(DocumentTag documentTag) {
        return persist(documentTag).getDocumentTagId();
    }

}
