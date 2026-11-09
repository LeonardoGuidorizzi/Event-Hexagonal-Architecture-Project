package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.application.port.in.FindPublishedEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;

public class FindPublishedEventsUseCaseImpl implements FindPublishedEventsUseCase {
    private final EventRepository repository;

    public FindPublishedEventsUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResult<Event> execute(String name, PageRequest request) {
        return repository.findPublishedEvents(name, request);
    }
}
