package hexlet.code;

import lombok.Getter;

@Getter
public class Change {
    private final String change;
    private final String key;
    private final Object pastValue;

    public Change(String change, String key, Object pastValue) {
        this.change = change;
        this.key = key;
        this.pastValue = pastValue;
    }
}
