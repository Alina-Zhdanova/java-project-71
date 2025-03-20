import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenerateTest {

    @Test
    public void testNoSuchFile() {
        var path1 = "/Users/alinazdanova/java-project-71/app/src/test/resources/file2.json";
        var path2 = "/Users/alinazdanova/java-project-71/app/src/test/resources/file3.json";
        assertThrows(IOException.class, () -> Differ.generate(path1, path2));
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

        var path1 = "/Users/alinazdanova/java-project-71/app/src/test/resources/file1.json";
        var path2 = "/Users/alinazdanova/java-project-71/app/src/test/resources/file2.json";
        var expected = Differ.generate(path1, path2);

        assertEquals(actual, expected);
    }
}
