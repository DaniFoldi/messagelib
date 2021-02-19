package com.danifoldi.messagelib.messageprovider;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryMessageProvider implements MessageProvider<String> {
    private final Map<String, String> values = new HashMap<>();

    public InMemoryMessageProvider() { }

    public void addMessage(final @NotNull String id, final @NotNull String message) {
        if (values.containsKey(requireNonNull(id))) {
            throw new IllegalArgumentException("Message with id " + id + " already exists!");
        }

        values.put(id, requireNonNull(message));
    }

    public void addMessages(final @NotNull Map<String, String> messages) {
        requireNonNull(messages).forEach(this::addMessage);
    }

    @Override
    public @NotNull String getMessageBase(final @NotNull String id) {
        return values.getOrDefault(id, "");
    }
}
