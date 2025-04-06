package hexlet.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Change {
    private final String change;
    private final String key;
    private final Object pastValue;
    private final Object presentValue;
}
