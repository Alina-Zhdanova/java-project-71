package hexlet.code;

import hexlet.code.parsers.Status;

public record Change(Status status, String key, Object pastValue, Object presentValue) { }
