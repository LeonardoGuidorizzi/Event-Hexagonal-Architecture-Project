package dev.devdreamer.Event.infrastructure.adapter.out.persistence.converter;

import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventJpaRepository;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.mapper.EventJpaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Event> findEventById(String id) {
        return repository.findById(UUID.fromString(id)).map(mapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return List.of();
    }



    @Override
    public Event findEventByStatus(String status) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return null;
    }
}
