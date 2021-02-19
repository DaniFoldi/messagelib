package com.danifoldi.messagelib.templateprocessor;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TemplateProcessor {

    @NotNull String toTemplate(final @NotNull String from);

    static TemplateProcessor percent() {
        return TemplateProcessors.PERCENT_PROCESSOR;
    }

    static TemplateProcessor bracket() {
        return TemplateProcessors.BRACKET_PROCESSOR;
    }

    static TemplateProcessor literal() {
        return TemplateProcessors.LITERAL_PROCESSOR;
    }
}
