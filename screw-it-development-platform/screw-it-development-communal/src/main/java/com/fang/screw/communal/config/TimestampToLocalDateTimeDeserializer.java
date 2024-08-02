package com.fang.screw.communal.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @FileName TimestampToLocalDateTimeDeserializer
 * @Description
 * @Author yaoHui
 * @date 2024-08-02
 **/
public class TimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String timestampStr = p.getText();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestampStr)), getDefaultTimeZone());
    }

    private static ZoneId getDefaultTimeZone() {
        return ZoneId.of("GMT+8");
    }
}
