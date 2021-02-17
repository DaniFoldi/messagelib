package com.danifoldi.messagelib.json;

import com.danifoldi.messagelib.messageprovider.MessageProvider;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonMessageProvider implements MessageProvider {

    final JsonObject json;

    public JsonMessageProvider(Path messageFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(messageFile);
             JsonReader jsonReader = Json.createReader(reader)) {
            json = jsonReader.readObject();
        }
    }

    @Override
    public String getMessageBase(Object id) {
        if (!(id instanceof String)) {
            throw new IllegalArgumentException();
        }

        List<String> path = Arrays.stream(((String)id).split("\\.")).collect(Collectors.toList());
        String last = path.remove(path.size() - 1);
        JsonObject p = json;

        for (String pathPart: path) {
            p = p.getJsonObject(pathPart);
        }

        return p.getString(last, id.toString());
    }
}
