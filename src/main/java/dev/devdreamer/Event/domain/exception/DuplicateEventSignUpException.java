package dev.devdreamer.Event.domain.exception;

public class DuplicateEventSignUpException extends RuntimeException {
    public DuplicateEventSignUpException() { super("User has already signed up for this event"); }
}
