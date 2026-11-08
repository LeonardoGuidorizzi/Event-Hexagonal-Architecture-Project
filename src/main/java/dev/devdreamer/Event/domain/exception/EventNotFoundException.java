package dev.devdreamer.Event.domain.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String id) {
        super("Evento não encontrado com ID: " + id);
    }
}
