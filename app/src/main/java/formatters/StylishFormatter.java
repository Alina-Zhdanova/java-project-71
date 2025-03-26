package formatters;

import hexlet.code.Change;

import java.util.List;

public class StylishFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

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
