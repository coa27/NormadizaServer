package com.coa.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class FechaDeserializer extends JsonDeserializer<LocalDate> {


    private DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        String fecha = p.getText();

        return LocalDate.parse(fecha, format);
    }
}
