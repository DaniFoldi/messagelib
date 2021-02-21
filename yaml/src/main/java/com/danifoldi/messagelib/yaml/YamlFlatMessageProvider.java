package com.danifoldi.messagelib.yaml;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.danifoldi.messagelib.messageprovider.MessageProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class YamlFlatMessageProvider implements MessageProvider<String> {
    private final YamlMapping yaml;

    public YamlFlatMessageProvider(final @NotNull Path messageFile) throws IOException {
        requireNonNull(messageFile);
        yaml = Yaml.createYamlInput(messageFile.toFile()).readYamlMapping();
    }

    @Override
    public @NotNull String getMessageBase(final @NotNull String id) {
        requireNonNull(id);
        return yaml.string(id);
    }
}
