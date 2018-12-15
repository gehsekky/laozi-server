package com.iamchung.laozi.db.dao;

import com.iamchung.laozi.db.models.Tag;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class TagDao extends AbstractDAO<Tag> {

    public TagDao(SessionFactory factory) {
        super(factory);
    }

    public Tag findById(int tagId) {
        return get(tagId);
    }

    public int create(Tag tag) {
        return persist(tag).getTagId();
    }
}
