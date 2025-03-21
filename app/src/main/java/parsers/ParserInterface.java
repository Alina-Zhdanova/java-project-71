package parsers;

import java.io.IOException;
import java.util.Map;

public interface ParserInterface {
    Map<String, Object> parse(String filePath) throws IOException;
}
