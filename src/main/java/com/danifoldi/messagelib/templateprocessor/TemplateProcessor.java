package com.danifoldi.messagelib.templateprocessor;

@FunctionalInterface
public interface TemplateProcessor {

    String toTemplate(String from);

    @Deprecated
    AbstractTemplateProcessor percentProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "%" + from + "%";
        }
    };

    @Deprecated
    AbstractTemplateProcessor bracketProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "{" + from + "}";
        }
    };

    @Deprecated
    AbstractTemplateProcessor literalProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "${" + from + "}";
        }
    };

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
