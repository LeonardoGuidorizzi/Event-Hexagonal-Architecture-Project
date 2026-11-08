package dev.devdreamer.Event.core.enums;

public enum EventType {
    WORKSHOP,
    MUSIC,
    LECTURE,
    SEMINAR;
    public static EventType fromString(String value) {
        if (value == null) {
            return null;
        }
        for (EventType status : EventType.values()) {
            if (status.name().equalsIgnoreCase(value.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

}
