package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            // -1 if all occurences should be replaced
            private final int maxCount;

            private Replacement(final @NotNull String from, final @NotNull String to, final int maxCount) {
                this.from = requireNonNull(from);
                this.to = requireNonNull(to);
                this.maxCount = maxCount;
            }
        }

        private final String base;
        private final List<Replacement> replacements;

        private BaseMessageBuilder(final @NotNull String base) {
            this.base = requireNonNull(base);
            this.replacements = new ArrayList<>();
        }

        public @NotNull BaseMessageBuilder<T> usingTemplate(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to, -1));
            return this;
        }

        public @NotNull BaseMessageBuilder<T> usingTemplateOnce(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to, 1));
            return this;
        }

        public @NotNull BaseMessageBuilder<T> usingTemplateLimited(final @NotNull String from,
                                                                final @NotNull String to,
                                                                int maxCount) {
            replacements.add(new Replacement(from, to, maxCount));
            return this;
        }

        public @NotNull M execute() {
            String result = base;

            for (Replacement r: replacements) {
                final String safeFrom = processor.toTemplate(r.from).chars()
                        .mapToObj(c -> (char)c)
                        .map(c -> "[" + c + "]")
                        .collect(Collectors.joining());
                if (r.maxCount < 0) {
                    result = result.replaceAll(safeFrom, r.to);
                } else if (r.maxCount == 1) {
                    result = result.replaceFirst(safeFrom, r.to);
                } else {
                    for (int i = 0; i < r.maxCount; i++) {
                        result = result.replaceFirst(safeFrom, r.to);
                    }
                }
            }

            return messageConverter.apply(result);
        }
    }

    public @NotNull BaseMessageBuilder<M> getBase(final @NotNull K id) {
        return new BaseMessageBuilder<>(provider.getMessageBase(id));
    }
}
