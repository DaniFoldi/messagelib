package com.danifoldi.messagelib.templateprocessor;

final class TemplateProcessors {
    static final TemplateProcessor PERCENT_PROCESSOR = from -> "%" + from + "%";
    static final TemplateProcessor BRACKET_PROCESSOR = from -> "{" + from + "}";
    static final TemplateProcessor LITERAL_PROCESSOR = from -> "${" + from + "}";

    private TemplateProcessors() {
        throw new UnsupportedOperationException();
    }
}