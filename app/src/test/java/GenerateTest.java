import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class GenerateTest {

    private static String getPath(String fileName) {
        return String.valueOf(Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize());
    }

    private static String getActual(String formatName) throws IOException {
        var path = Paths.get("src", "test", "resources", "fixtures", formatName)
            .toAbsolutePath().normalize();
        return Files.readString(path).trim();
    }

    @ParameterizedTest
    @CsvSource ({
        "stylish, file1.json, file2.json",
        "stylish, file1.yaml, file2.yaml",
        "stylish, file1.yml, file2.yml",
        "plain, file1.json, file2.json",
        "plain, file1.yaml, file2.yaml",
        "plain, file1.yml, file2.yml",
        "json, file1.json, file2.json",
        "json, file1.yaml, file2.yaml",
        "json, file1.yml, file2.yml"
    })
    public void differTest(String formatName, String fileName1, String fileName2) throws IOException {
        var actual = getActual(formatName);
        var filePath1 = getPath(fileName1);
        var filePath2 = getPath(fileName2);
        var expected = Differ.generate(filePath1, filePath2, formatName);

        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @CsvSource ({
        "stylish, file2.json, file3.json",
        "stylish, file2.yaml, file3.yaml",
        "stylish, file2.yml, file3.yml",
        "plain, file2.json, file3.json",
        "plain, file2.yaml, file3.yaml",
        "plain, file2.yml, file3.yml",
        "json, file2.json, file3.json",
        "json, file2.yaml, file3.yaml",
        "json, file2.yml, file3.yml"
    })
    public void noSuchFileTest(String formatName, String fileName1, String fileName2) {
        var filePath1 = getPath(fileName1);
        var filePath2 = getPath(fileName2);
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2, formatName));
    }

    @ParameterizedTest
    @CsvSource ({
        "file1.json, file2.json, stylish",
        "file1.yaml, file2.yaml, stylish",
        "file1.yml, file2.yml, stylish"
    })
    public void defaultTest(String fileName1, String fileName2, String actualName) throws IOException {
        var actual = getActual(actualName);
        var filePath1 = getPath(fileName1);
        var filePath2 = getPath(fileName2);
        var expected = Differ.generate(filePath1, filePath2);

        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @CsvSource ({
        "stylish, file1.json, file2.yaml",
        "stylish, file1.json, file2.yml",
        "plain, file1.json, file2.yaml",
        "plain, file1.json, file2.yml",
        "json, file1.json, file2.yaml",
        "json, file1.json, file2.yml"
    })
    public void diffExtensionsTest(String formatName, String fileName1, String fileName2) throws IOException {
        var actual = getActual(formatName);
        var filePath1 = getPath(fileName1);
        var filePath2 = getPath(fileName2);
        var expected = Differ.generate(filePath1, filePath2, formatName);

        assertEquals(actual, expected);
    }
}
