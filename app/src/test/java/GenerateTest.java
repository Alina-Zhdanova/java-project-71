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
        var formatName = "stylish";
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2, formatName));
    }

    @Test
    public void testNoSuchFileYaml() {
        var filePath1 = getPath("file2.yaml");
        var filePath2 = getPath("file3.yaml");
        var formatName = "stylish";
        assertThrows(IOException.class, () -> Differ.generate(filePath1, filePath2, formatName));
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
        var formatName = "stylish";
        var expected = Differ.generate(filePath1, filePath2, formatName);

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
        var formatName = "stylish";
        var expected = Differ.generate(filePath1, filePath2, formatName);

        assertEquals(actual, expected);
    }

    @Test
    public void testGenerateJsonPlain() throws IOException {

        var actual = """
            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'""";

        var filePath1 = getPath("file1.json");
        var filePath2 = getPath("file2.json");
        var formatName = "plain";
        var expected = Differ.generate(filePath1, filePath2, formatName);

        assertEquals(actual, expected);
    }
}
