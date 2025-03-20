import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateTest {

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

        var expected = Differ.generate("/Users/alinazdanova/java-project-71/app/src/test/resources/file1.json",
                                       "/Users/alinazdanova/java-project-71/app/src/test/resources/file2.json");

        assertEquals(actual, expected);
    }
}
