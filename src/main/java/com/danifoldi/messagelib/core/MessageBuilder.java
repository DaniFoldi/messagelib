package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class MessageBuilder<I> {
    private final MessageProvider<I> provider;
    private final TemplateProcessor processor;

    // TODO make this private? Maybe?
    public MessageBuilder(final @NotNull MessageProvider<I> provider, final @NotNull TemplateProcessor processor) {
        this.provider = requireNonNull(provider);
        this.processor = requireNonNull(processor);
    }

    @Deprecated
    MessageBuilder<I> usingProvider(MessageProvider<I> provider) {
        return new MessageBuilder<>(provider, processor);
    }

    @Deprecated
    MessageBuilder<I> usingProcessor(TemplateProcessor processor) {
        return new MessageBuilder<>(provider, processor);
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    public static class Builder<I> {
        private MessageProvider<I> provider;
        private TemplateProcessor processor;

        private Builder() {}

        public @NotNull Builder<I> usingProvider(final @NotNull MessageProvider<I> provider) {
            this.provider = provider;
            return this;
        }

        public @NotNull Builder<I> usingProcessor(final @NotNull TemplateProcessor processor) {
            this.processor = processor;
            return this;
        }

        public @NotNull MessageBuilder<I> build() {
            return new MessageBuilder<>(provider, processor);
        }
    }

    public class BaseMessageBuilder {

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

        public @NotNull BaseMessageBuilder usingTemplate(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to, -1));
            return this;
        }

        public @NotNull BaseMessageBuilder usingTemplateOnce(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to, 1));
            return this;
        }

        public @NotNull BaseMessageBuilder usingTemplateLimited(final @NotNull String from,
                                                                final @NotNull String to,
                                                                int maxCount) {
            replacements.add(new Replacement(from, to, maxCount));
            return this;
        }

        public @NotNull String execute() {
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

            return result;
        }
    }

    public @NotNull BaseMessageBuilder getBase(final @NotNull I id) {
        return new BaseMessageBuilder(provider.getMessageBase(id));
    }
}
