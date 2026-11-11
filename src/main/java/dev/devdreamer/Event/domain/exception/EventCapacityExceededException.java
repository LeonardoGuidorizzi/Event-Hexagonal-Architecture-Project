package dev.devdreamer.Event.domain.exception;

public class EventCapacityExceededException extends RuntimeException {
    public EventCapacityExceededException() { super("Event capacity has been reached"); }
}
