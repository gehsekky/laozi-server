package com.iamchung.laozi.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "document")
@Produces(MediaType.APPLICATION_JSON)
@NamedNativeQueries({
        @NamedNativeQuery(name = "Document.findAllByTags", query = "select d.* from document d join document_tag dt on d.document_id = dt.document_id where dt.tag_id in :tagIds", resultClass = Document.class)
})
public class Document {
    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    public int getDocumentId() {
        return documentId;
    }

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    @Column(name = "path")
    private String path;

    public String getPath() {
        return path;
    }

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Column(name = "created_by")
    private int createdBy;


    public int getCreatedBy() {
        return createdBy;
    }

    @Column(name = "updated_by")
    private int updatedBy;

    public int getUpdatedBy() {
        return updatedBy;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "document")
    private List<DocumentTag> documentTags;

    public List<DocumentTag> getDocumentTags() {
        return documentTags;
    }

    public Document() {

    }

    public Document(int documentId, String name, String path, LocalDateTime createdAt, LocalDateTime updatedAt, int createdBy, int updatedBy) {
        this.documentId = documentId;
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Document(String name, String path, LocalDateTime createdAt, LocalDateTime updatedAt, int createdBy, int updatedBy) {
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
