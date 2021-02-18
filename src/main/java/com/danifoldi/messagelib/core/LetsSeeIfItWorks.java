package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.InMemoryMessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;

import java.util.Map;

public class LetsSeeIfItWorks {
    public static void main(String[] args) {
        InMemoryMessageProvider provider = new InMemoryMessageProvider();
        TemplateProcessor processor = TemplateProcessor.bracket();

        MessageBuilder<String> messageBuilder = new MessageBuilder<>(provider, processor);

        provider.addMessages(Map.of("test", "This is a test {message} {test}", "random", "this is a random {message}", "message", "This is a {message}"));

        String m = messageBuilder.getBase("test").usingTemplate("message", "MESSAGE REPLACED").usingTemplateOnce("test", "TEST REPLACED").execute();
        System.out.println(m);
    }
}
