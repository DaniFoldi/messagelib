package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SimpleMessageBuilder<K> extends MessageBuilder<String, K> {

    public SimpleMessageBuilder(final @NotNull MessageProvider<K> provider,
                                final @NotNull TemplateProcessor templateProcessor) {
        super(provider, templateProcessor, Function.identity());
    }
}