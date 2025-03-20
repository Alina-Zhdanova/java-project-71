import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenerateTest {

    private static String getPath(String fileName) {
        return String.valueOf(Paths.get("src", "test", "resources", fileName)
                .toAbsolutePath().normalize());
    }

    @Test
    public void testNoSuchFile() {
        var filePath1 = getPath("file2.json");
        var filePath2 = getPath("file3.json");
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2));
    }

    @Test
    public void testGenerate() throws IOException {

        var actual = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        var filePath1 = getPath("file1.json");
        var filePath2 = getPath("file2.json");
        var expected = Differ.generate(filePath1, filePath2);

        assertEquals(actual, expected);
    }
}
