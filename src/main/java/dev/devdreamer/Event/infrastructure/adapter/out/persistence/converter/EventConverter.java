package dev.devdreamer.Event.infrastructure.adapter.out.persistence.converter;

import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventJpaRepository;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.mapper.EventJpaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EventConverter implements EventRepository {
private final EventJpaMapper mapper;
private final EventJpaRepository repository;

    public EventConverter(EventJpaMapper mapper,  EventJpaRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Event save(Event event) {
    var entity = mapper.toEntity(event);
    repository.save(entity);
    return mapper.toDomain(entity);
    }

    @Override
    public List<Event> findAll() {
        return List.of();
    }

    @Override
    public List<Event> findEventById() {
        return List.of();
    }

    @Override
    public List<Event> findEventByStatus(String name) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return null;
    }
}
