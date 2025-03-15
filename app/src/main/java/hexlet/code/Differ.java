package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Differ {

    private static Path pathFile(String filePath) {
        return Paths.get(filePath) //?
                    .toAbsolutePath()
                    .normalize();
    }

    private static String readFile(String filePath) throws IOException {
        var path = pathFile(filePath);
        return Files.readString(path).trim();
    }

    private static Map<String, Object> parseFile(String fileName) throws IOException {
        var file = readFile(fileName);
        var mapper = new ObjectMapper();
        var typeRef = new TypeReference<HashMap<String, Object>>() {};
        return mapper.readValue(file, typeRef);
    }


//    public static String generate(String filePath1, String filePath2) throws IOException {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, Object> map = mapper.readValue(new File(filePath1), Map.class);
//    }
//
//    public static String readFilePath1(String fileName) throws Exception {
//        var path = Paths.get("src", "test", "resources", fileName)
//                .toAbsolutePath().normalize();
//        return Files.readString(path).trim();
//    }
}
