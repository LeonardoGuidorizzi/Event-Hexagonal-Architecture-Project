package dev.devdreamer.Event.application.model;

public record PageRequest(int page, int size) {
    public PageRequest {
        if(page < 0 ) throw new IllegalArgumentException("page must be greater than 0");
        if(size < 1 || size > 100) throw new IllegalArgumentException("size must be between 1 and 100");
    }
}
