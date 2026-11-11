package dev.devdreamer.Event.domain.exception;

public class InvalidEventStateException extends RuntimeException {
    public InvalidEventStateException(String message) { super(message); }
}
