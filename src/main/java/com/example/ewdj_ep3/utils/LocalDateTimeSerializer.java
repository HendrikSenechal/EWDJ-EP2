package com.example.ewdj_ep3.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import static com.example.ewdj_ep3.utils.DateTimeFormats.*;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(value.format(FORMATTER));
    }
}