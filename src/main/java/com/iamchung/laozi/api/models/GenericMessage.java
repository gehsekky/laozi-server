package com.iamchung.laozi.api.models;

import org.immutables.value.Value;

@Value.Immutable
public interface GenericMessage {

    String getMessage();

}
