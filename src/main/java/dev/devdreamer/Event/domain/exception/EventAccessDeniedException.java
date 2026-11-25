package dev.devdreamer.Event.domain.exception;

public class EventAccessDeniedException extends RuntimeException {
    public EventAccessDeniedException() {
        super("Only the event creator can perform this action");
    }
}
