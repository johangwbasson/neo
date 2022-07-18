package net.johanbasson.neo.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record Envelope(
        String className,
        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class") Object payload) {

}
