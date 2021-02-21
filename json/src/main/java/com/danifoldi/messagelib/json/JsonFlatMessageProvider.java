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

import static java.util.Objects.requireNonNull;

public class JsonFlatMessageProvider implements MessageProvider<String> {
    private final JsonObject json;

    public JsonFlatMessageProvider(final @NotNull Path messageFile) throws IOException {
        requireNonNull(messageFile);
        try (BufferedReader reader = Files.newBufferedReader(messageFile);
             JsonReader jsonReader = Json.createReader(reader)) {
            json = jsonReader.readObject();
        }
    }

    @Override
    public @NotNull String getMessageBase(final @NotNull String id) {
        requireNonNull(id);
        return json.getString(id, id);
    }
}
