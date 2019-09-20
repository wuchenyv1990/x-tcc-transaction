package com.wcyv90.x.tcc.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.function.Supplier;

public final class JsonMapper {

    private static final ObjectMapper MAPPER = JacksonConfig.commonObjectMapper();

    public static String dumps(Object o) {
        if (o == null) {
            return null;
        } else {
            try {
                return MAPPER.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Convert to Json failed.", e);
            }
        }
    }

    public static <T> T load(
        String data,
        Class<T> type
    ) {
        return invokeLoad(data, () -> {
            try {
                return MAPPER.readValue(data, type);
            } catch (IOException e) {
                throw new RuntimeException("Convert to Json failed.", e);
            }
        });
    }

    public static <T> T loadGeneric(
        String data,
        TypeReference<T> type
    ) {
        return invokeLoad(data, () -> {
            try {
                return MAPPER.readValue(data, type);
            } catch (IOException e) {
                throw new RuntimeException("Convert to Json failed.", e);
            }
        });
    }

    private static <T> T invokeLoad(
        String data,
        Supplier<T> supplier
    ) {
        if (data == null) {
            return null;
        } else {
            return supplier.get();
        }
    }
    
}
