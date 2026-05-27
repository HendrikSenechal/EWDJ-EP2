package com.example.ewdj_ep3.utils;

import static com.example.ewdj_ep3.utils.DateTimeFormats.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>{

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        String valueAsString = p.getValueAsString();
        if (valueAsString == null || valueAsString.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(valueAsString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid date format: %s".formatted(valueAsString), e);
        }
    }
}
