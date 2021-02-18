package com.danifoldi.messagelib.messageprovider;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMessageProvider implements MessageProvider<String> {
    private final Map<String, String> values = new HashMap<>();

    public InMemoryMessageProvider() { }

    public void addMessage(String id, String message) {
        if (values.containsKey(id)) {
            throw new IllegalArgumentException("Message with id " + id + " already exists!");
        }

        values.put(id, message);
    }

    public void addMessages(Map<String, String> messages) {
        messages.forEach(this::addMessage);
    }

    @Override
    public String getMessageBase(String id) {
        return values.getOrDefault(id, "");
    }
}
