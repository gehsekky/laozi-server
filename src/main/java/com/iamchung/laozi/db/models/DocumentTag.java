package com.iamchung.laozi.db.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "document_tag")
public class DocumentTag {

    @Id
    @Column(name = "document_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentTagId;

    public int getDocumentTagId() {
        return documentTagId;
    }

    @Column(name = "document_id")
    private int documentId;

    @Column(name = "tag_id")
    private int tagId;

    public DocumentTag() {

    }

    public DocumentTag(int documentTagId, int documentId, int tagId) {
        this.documentTagId = documentTagId;
        this.documentId = documentId;
        this.tagId = tagId;
    }

    public DocumentTag(int documentId, int tagId) {
        this.documentId = documentId;
        this.tagId = tagId;
    }

}
