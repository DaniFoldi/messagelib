package com.danifoldi.messagelib.core;

import com.danifoldi.messagelib.messageprovider.MessageProvider;
import com.danifoldi.messagelib.templateprocessor.TemplateProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageBuilder<I> {
    private MessageProvider<I> provider;
    private TemplateProcessor processor;

    public MessageBuilder(MessageProvider<I> provider, TemplateProcessor processor) {
        this.provider = provider;
        this.processor = processor;
    }

    MessageBuilder<I> usingProvider(MessageProvider<I> provider) {
        return new MessageBuilder<>(provider, processor);
    }

    MessageBuilder<I> usingProcessor(TemplateProcessor processor) {
        return new MessageBuilder<>(provider, processor);
    }

    public class BaseMessageBuilder {

        private class Replacement {
            String from;
            String to;
            // -1 if all occurences should be replaced
            int maxCount;

            private Replacement(String from, String to, int maxCount) {
                this.from = from;
                this.to = to;
                this.maxCount = maxCount;
            }
        }

        private String base;
        private List<Replacement> replacements;

        private BaseMessageBuilder(String base) {
            this.base = base;
            this.replacements = new ArrayList<>();
        }

        BaseMessageBuilder usingTemplate(String from, String to) {
            replacements.add(new Replacement(from, to, -1));
            return this;
        }

        BaseMessageBuilder usingTemplateOnce(String from, String to) {
            replacements.add(new Replacement(from, to, 1));
            return this;
        }

        BaseMessageBuilder usingTemplateLimited(String from, String to, int maxCount) {
            replacements.add(new Replacement(from, to, maxCount));
            return this;
        }

        String execute() {
            String result = base;

            for (Replacement r: replacements) {
                String safeFrom = processor.toTemplate(r.from).chars().mapToObj(c -> (char)c).map(c -> "\\" + c).collect(Collectors.joining());
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

    public BaseMessageBuilder getBase(I id) {
        return new BaseMessageBuilder(provider.getMessageBase(id));
    }
}
