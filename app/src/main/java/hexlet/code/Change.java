package hexlet.code;

public record Change(Status status, String key, Object pastValue, Object presentValue) {
    public enum Status {
        NOT_CHANGED,
        CHANGED,
        DELETED,
        ADDED
    }
}
