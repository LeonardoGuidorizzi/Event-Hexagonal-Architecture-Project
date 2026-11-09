package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.application.port.in.ListEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;

public class ListEventsUseCaseImpl implements ListEventsUseCase {
    private final EventRepository repository;
    public ListEventsUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }
    @Override
    public PageResult<Event> execute(PageRequest request) {
        return repository.findAll(request);
    }
}
