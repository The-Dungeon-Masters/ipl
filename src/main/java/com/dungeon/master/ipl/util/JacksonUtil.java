package com.dungeon.master.ipl.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T fromString(final String string, final Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (final IOException e) {
            throw new IllegalArgumentException(
                "The given string value: " + string + " cannot be transformed to Json object");
        }
    }

    public static String toString(final Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(
                "The given Json object value: " + value + " cannot be transformed to a String");
        }
    }

    public static JsonNode toJsonNode(final String value) {
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(final T value) {
        return fromString(toString(value), (Class<T>)value.getClass());
    }
}