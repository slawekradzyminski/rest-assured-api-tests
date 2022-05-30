package com.awesome.testing.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class YmlParser {

    public static LocalConfig getConfig() {
        Yaml yaml = new Yaml(new Constructor(LocalConfig.class));
        InputStream inputStream = YmlParser.class.getClassLoader().getResourceAsStream("properties.yml");
        return yaml.load(inputStream);
    }

}
