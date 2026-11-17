package dev.devdreamer.Event.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() { super("Email or password is incorrect"); }
}
