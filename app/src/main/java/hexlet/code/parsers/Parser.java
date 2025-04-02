package hexlet.code.parsers;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Parser {

    public static Map<String, Object> parse(String readFile, String fileExtension) throws IOException {
        var mapper = ObjectMapperFactory.getObjectMapper(fileExtension);
        var typeRef = new TypeReference<HashMap<String, Object>>() { };
        return mapper.readValue(readFile, typeRef);
    }
}
