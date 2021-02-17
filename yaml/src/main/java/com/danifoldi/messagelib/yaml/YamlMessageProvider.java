package com.danifoldi.messagelib.yaml;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.danifoldi.messagelib.messageprovider.MessageProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class YamlMessageProvider implements MessageProvider {

    final YamlMapping yaml;

    public YamlMessageProvider(Path messageFile) throws IOException {
        yaml = Yaml.createYamlInput(messageFile.toFile()).readYamlMapping();
    }

    @Override
    public String getMessageBase(Object id) {
        if (!(id instanceof String)) {
            throw new IllegalArgumentException();
        }

        List<String> path = Arrays.stream(((String)id).split("\\.")).collect(Collectors.toList());
        String last = path.remove(path.size() - 1);
        YamlMapping p = yaml;

        for (String pathPart: path) {
            p = p.yamlMapping(pathPart);
        }

        return p.string(last);
    }
}
