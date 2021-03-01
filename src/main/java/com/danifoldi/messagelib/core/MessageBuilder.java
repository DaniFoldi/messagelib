package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class MessageBuilder<I> {
    private final MessageProvider<I> provider;
    private final TemplateProcessor processor;

    public MessageBuilder(final @NotNull MessageProvider<I> provider, final @NotNull TemplateProcessor processor) {
        this.provider = requireNonNull(provider);
        this.processor = requireNonNull(processor);
    }

    public class BaseMessageBuilder {

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

        public @NotNull BaseMessageBuilder usingTemplate(final @NotNull String from, final @NotNull String to) {
            replacements.add(new Replacement(from, to));
            return this;
        }

        public @NotNull String execute() {
            String result = base;

            for (Replacement r: replacements) {
                result = result.replace(processor.toTemplate(r.from), r.to);
            }

            return result;
        }
    }

    public @NotNull BaseMessageBuilder getBase(final @NotNull I id) {
        return new BaseMessageBuilder(provider.getMessageBase(id));
    }
}
