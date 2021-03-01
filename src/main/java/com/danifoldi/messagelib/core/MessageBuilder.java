package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class MessageBuilder<M, K> {
    private final MessageProvider<K> provider;
    private final TemplateProcessor processor;
    private final Function<String, M> messageConverter;

    public MessageBuilder(final @NotNull MessageProvider<K> provider,
                          final @NotNull TemplateProcessor processor,
                          final @NotNull Function<String, @NotNull M> messageConverter) {
        this.provider = requireNonNull(provider);
        this.processor = requireNonNull(processor);
        this.messageConverter = requireNonNull(messageConverter);
    }

    public class BaseMessageBuilder<T> {

        private class Replacement {
            private final String from;
            private final String to;

            private Replacement(final @NotNull String from, final @NotNull String to) {
                this.from = requireNonNull(from);
                this.to = requireNonNull(to);
            }
        }

        private final String base;
        private final List<Replacement> replacements;

        private BaseMessageBuilder(final @NotNull String base) {
            this.base = requireNonNull(base);
            this.replacements = new ArrayList<>();
        }

        public @NotNull BaseMessageBuilder<T> usingTemplate(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to));
            return this;
        }

        public @NotNull M execute() {
            String result = base;

            for (Replacement r: replacements) {
                result = result.replace(processor.toTemplate(r.from), r.to);
            }

            return messageConverter.apply(result);
        }
    }

    public @NotNull BaseMessageBuilder<M> getBase(final @NotNull K id) {
        return new BaseMessageBuilder<>(provider.getMessageBase(id));
    }
}
