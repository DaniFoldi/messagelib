package com.danifoldi.messagelibtest;

import com.danifoldi.messagelib.messageprovider.InMemoryMessageProvider;
import com.danifoldi.messagelib.messageprovider.MessageProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageLibTest {

    @Test
    @DisplayName("In-memory message provider unchanged storage test")
    public static void inMemoryMessageProvider_unchangedStorage() {
        MessageProvider provider = new InMemoryMessageProvider();

        assertEquals("", provider.getMessageBase("test"), "The default value for a message is not an empty string");
    }

    @Test
    @DisplayName("In-memory message provider add single message")
    public static void inMemoryMessageProvider_addSingleMessage() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        provider.addMessage("key", "value");

        assertEquals("value", provider.getMessageBase("key"), "Message for 'key' was not saved or incorrect");
    }

    @Test
    @DisplayName("In-memory message provider add single existing message")
    public static void inMemoryMessageProvider_addSingleExistingMessage() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        provider.addMessage("key", "value");

        assertThrows(IllegalArgumentException.class, () -> {
            provider.addMessage("key", "value2");
        }, "Adding an already existing message id did not throw an error");
    }

    @Test
    @DisplayName("In-memory message provider add multiple messages")
    public static void inMemoryMessageProvider_addMultipleMessages() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        Map<String, String> messages = Map.of("key1", "value1", "key2", "value2", "key3", "value3");
        provider.addMessages(messages);

        assertEquals(provider.getMessageBase("key1"), "value1", "Message for 'key1' was not saved or incorrect");
        assertEquals(provider.getMessageBase("key2"), "value2", "Message for 'key2' was not saved or incorrect");
        assertEquals(provider.getMessageBase("key3"), "value3", "Message for 'key3' was not saved or incorrect");
    }

    @Test
    @DisplayName("In-memory message provider add multiple messages with already existing")
    public static void inMemoryMessageProvider_addMultipleMessagesWithAlreadyExisting() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        provider.addMessage("key2", "value");

        Map<String, String> messages = Map.of("key1", "value1", "key2", "value2", "key3", "value3");
        assertThrows(IllegalArgumentException.class, () -> {
            provider.addMessages(messages);
        }, "Adding an already existing message id did not throw an error");
    }

    @Test
    @DisplayName("In-memory message provider get nonextistent message")
    public static void inMemoryMessageProvider_getNonexistentMessage() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        Map<String, String> messages = Map.of("key1", "value1", "key2", "value2", "key3", "value3");
        provider.addMessages(messages);

        assertEquals("", provider.getMessageBase("test"), "The default value for a nonexistent id is not an empty string");
    }

    @Test
    @DisplayName("In-memory message provider get message")
    public static void inMemoryMessageProvider_getMessage() {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        Map<String, String> messages = Map.of("key1", "value1", "key2", "value2", "key3", "value3");
        provider.addMessages(messages);

        assertEquals("value1", provider.getMessageBase("key1"), "Message for 'key1' was not saved or incorrect");
        assertEquals("value1", provider.getMessageBase("key1"), "Message for 'key1' could not be retrieved for a second time");
    }
}