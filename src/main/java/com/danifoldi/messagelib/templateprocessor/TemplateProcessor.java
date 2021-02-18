package com.danifoldi.messagelib.templateprocessor;

@FunctionalInterface
public interface TemplateProcessor {

    String toTemplate(String from);

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
