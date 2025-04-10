package hexlet.code;

import hexlet.code.formatters.FormatterFactory;
import hexlet.code.parsers.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Differ {

    private static String getExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1)
            .toLowerCase();
    }

    private static String readFile(String filePath) throws IOException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        return Files.readString(path).trim();
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws IOException {

        var readFile1 = readFile(filePath1);
        var fileExtension1 = getExtension(filePath1);

        var readFile2 = readFile(filePath2);
        var fileExtension2 = getExtension(filePath2);

        var mapFile1 = Parser.parse(readFile1, fileExtension1);
        var mapFile2 = Parser.parse(readFile2, fileExtension2);

        var changes = ChangesGenerator.getListOfChanges(mapFile1, mapFile2);

        var formatter = FormatterFactory.getFormatter(formatName);
        return formatter.formatter(changes);

    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }
}
