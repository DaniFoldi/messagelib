package com.danifoldi.messagelib.templateprocessor;

public interface TemplateProcessor {
    String toTemplate(String from);

    AbstractTemplateProcessor percentProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "%" + from + "%";
        }
    };

    AbstractTemplateProcessor bracketProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "{" + from + "}";
        }
    };

    AbstractTemplateProcessor literalProcessor = new AbstractTemplateProcessor() {
        @Override
        public String toTemplate(String from) {
            return "${" + from + "}";
        }
    };
}
