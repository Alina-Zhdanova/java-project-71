import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenerateTest {

    private static String getPath(String fileName) {
        return String.valueOf(Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize());
    }

    @Test
    public void testNoSuchFileJson() {
        var filePath1 = getPath("file2.json");
        var filePath2 = getPath("file3.json");
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2));
    }

    @Test
    public void testNoSuchFileYaml() {
        var filePath1 = getPath("file2.yaml");
        var filePath2 = getPath("file3.yaml");
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2));
    }

    @Test
    public void testGenerateJson() throws IOException {

        var actual = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }""";

        var filePath1 = getPath("file1.json");
        var filePath2 = getPath("file2.json");
        var expected = Differ.generate(filePath1, filePath2);

        assertEquals(actual, expected);
    }

    @Test
    public void testGenerateYaml() throws IOException {

        var actual = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }""";

        var filePath1 = getPath("file1.yaml");
        var filePath2 = getPath("file2.yaml");
        var expected = Differ.generate(filePath1, filePath2);

        assertEquals(actual, expected);
    }
}
