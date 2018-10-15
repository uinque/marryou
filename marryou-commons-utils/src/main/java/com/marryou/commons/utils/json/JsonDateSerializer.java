package com.marryou.commons.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.marryou.commons.utils.time.FormatConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by linhy on 2018/6/14.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    private static final DateFormat dateFormat = FormatConstants.DATE_TIME_FORMAT;

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String formattedDate = dateFormat.format(date);

        jsonGenerator.writeString(formattedDate);
    }
}
