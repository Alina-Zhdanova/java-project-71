package hexlet.code.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

public class ObjectMapperFactory {
    public static ObjectMapper getObjectMapper(String fileExtension) throws IOException {

        return switch (fileExtension) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new YAMLMapper();
            default -> throw new IOException();
        };
    }
}
