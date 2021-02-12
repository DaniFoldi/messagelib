package com.danifoldi.messagelib.messageprovider;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMessageProvider implements MessageProvider {
    Map<String, String> values = new HashMap<>();

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
    public String getMessageBase(Object id) {
        if (!(id instanceof String)) {
            return "";
        }

        return values.getOrDefault(id, "");
    }
}
