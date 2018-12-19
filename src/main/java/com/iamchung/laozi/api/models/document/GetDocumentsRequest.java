package com.iamchung.laozi.api.models.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableGetDocumentsRequest.class)
@JsonDeserialize(as = ImmutableGetDocumentsRequest.class)
public interface GetDocumentsRequest {

    List<Integer> getTagIds();

}
