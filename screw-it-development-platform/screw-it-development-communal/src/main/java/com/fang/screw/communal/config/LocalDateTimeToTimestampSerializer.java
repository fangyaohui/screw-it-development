package com.fang.screw.communal.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @FileName LocalDateTimeToTimestampSerializer
 * @Description
 * @Author yaoHui
 * @date 2024-08-02
 **/
public class LocalDateTimeToTimestampSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        long epochMilli = value.atZone(getDefaultTimeZone()).toInstant().toEpochMilli();
        gen.writeString(String.valueOf(epochMilli));
    }

    private static ZoneId getDefaultTimeZone() {
        return ZoneId.of("GMT+8");
    }
}
