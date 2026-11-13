package dev.devdreamer.Event.infrastructure.adapter.out.persistence.converter;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventJpaRepository;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.mapper.EventJpaMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public PageResult<Event> findAll(String status, String name, PageRequest request) {
        var pageable = org.springframework.data.domain.PageRequest.of(
                request.page(),
                request.size()
        );
        var page = repository.findAllFiltered(status, name, pageable);
        return new PageResult<>(
                page.getContent().stream().map(mapper::toDomain).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }



    @Override
    public Event findEventByStatus(String status) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return null;
    }

    @Override
    public int deleteCancelledBefore(LocalDateTime cutoff) {
        return repository.deleteCancelledBefore(cutoff);
    }
}
