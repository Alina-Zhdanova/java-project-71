package formatters;

import java.io.IOException;

public class FormatterFactory {
    public static FormatterInterface getFormatter(String formatName) throws IOException {

        var format = formatName.toLowerCase();

        return switch (format) {
            case "stylish" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            case "json" -> new JsonFormatter();
            default -> throw new IOException();
        };
    }
}
