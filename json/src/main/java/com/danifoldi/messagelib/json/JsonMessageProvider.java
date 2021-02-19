package com.danifoldi.messagelib.json;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import org.jetbrains.annotations.NotNull;

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

import static java.util.Objects.requireNonNull;

public class JsonMessageProvider implements MessageProvider<String> {
    private final JsonObject json;

    public JsonMessageProvider(final @NotNull Path messageFile) throws IOException {
        requireNonNull(messageFile);
        try (BufferedReader reader = Files.newBufferedReader(messageFile);
             JsonReader jsonReader = Json.createReader(reader)) {
            json = jsonReader.readObject();
        }
    }

    @Override
    public @NotNull String getMessageBase(final @NotNull String id) {
        requireNonNull(id);
        final List<String> path = Arrays.stream(id.split("\\.")).collect(Collectors.toList());
        final String last = path.remove(path.size() - 1);
        JsonObject p = json;

        for (String pathPart: path) {
            p = p.getJsonObject(pathPart);
        }

        return p.getString(last, id);
    }
}
