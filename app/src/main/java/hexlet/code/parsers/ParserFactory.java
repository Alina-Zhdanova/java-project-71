package hexlet.code.parsers;

import java.io.IOException;

public class ParserFactory {
    public static ParserInterface getParser(String filePath) throws IOException {

        var extension = filePath.substring(filePath.lastIndexOf(".") + 1)
                .toLowerCase();

        return switch (extension) {
            case "json" -> new JsonParser();
            case "yaml", "yml" -> new YamlParser();
            default -> throw new IOException();
        };
    }
}
