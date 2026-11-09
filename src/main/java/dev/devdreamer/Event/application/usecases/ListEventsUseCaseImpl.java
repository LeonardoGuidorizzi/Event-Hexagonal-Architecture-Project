package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.ListEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;

import java.util.List;

public class ListEventsUseCaseImpl implements ListEventsUseCase {
    private final EventRepository repository;
    public ListEventsUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Event> execute() {
        return repository.findAll();
    }
}
