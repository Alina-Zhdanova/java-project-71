package hexlet.code.parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class YamlParser implements ParserInterface {

    @Override
    public Map<String, Object> parse(String filePath) throws IOException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        var file = Files.readString(path).trim();

        var mapper = new YAMLMapper();
        var typeRef = new TypeReference<HashMap<String, Object>>() { };
        return mapper.readValue(file, typeRef);
    }
}

