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

public class YamlMessageProvider implements MessageProvider<String> {
    private final YamlMapping yaml;

    public YamlMessageProvider(final @NotNull Path messageFile) throws IOException {
        requireNonNull(messageFile);
        yaml = Yaml.createYamlInput(messageFile.toFile()).readYamlMapping();
    }

    @Override
    public @NotNull String getMessageBase(final @NotNull String id) {
        requireNonNull(id);
        final List<String> path = Arrays.stream(id.split("\\.")).collect(Collectors.toList());
        final String last = path.remove(path.size() - 1);
        YamlMapping p = yaml;

        for (String pathPart: path) {
            p = p.yamlMapping(pathPart);
        }

        return p.string(last);
    }
}
