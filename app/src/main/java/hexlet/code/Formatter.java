package hexlet.code;

import java.util.List;

public class Formatter {
    public static String stylish(List<Change> changes) {

        var result = new StringBuilder("{\n");

        changes.forEach((change) -> {
            result.append("  ");
            result.append(change.getChange());
            result.append(" ");
            result.append(change.getKey());
            result.append(": ");
            result.append(change.getPastValue());
            result.append("\n");
        });
        result.append("}");

        return result.toString();
    }
}
