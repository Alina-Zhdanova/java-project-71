package hexlet.code;

import hexlet.code.formatters.FormatterFactory;
import hexlet.code.parsers.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    private static String getExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1)
            .toLowerCase();
    }

    private static String readFile(String filePath) throws IOException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        return Files.readString(path).trim();
    }

    private static Map<String, Object> parseFile(String readFile, String fileExtension) throws IOException {
        return Parser.parse(readFile, fileExtension);
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws IOException {

        var readFile1 = readFile(filePath1);
        var fileExtension1 = getExtension(filePath1);

        var readFile2 = readFile(filePath2);
        var fileExtension2 = getExtension(filePath2);

        var mapFile1 = parseFile(readFile1, fileExtension1);
        var mapFile2 = parseFile(readFile2, fileExtension2);

        var changes = ListOfChanges.getListOfChanges(mapFile1, mapFile2);

        var format = FormatterFactory.getFormatter(formatName);
        return format.formatter(changes);

    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }
}
