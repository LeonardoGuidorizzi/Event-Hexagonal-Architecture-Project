package dev.devdreamer.Event.infrastructure.gateway;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.port.out.EventRepository;
import dev.devdreamer.utils.mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResitoryRepository implements EventRepository {
    private final dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventRepository repository;
    private final EventMapper mapper;

    public EventResitoryRepository(dev.devdreamer.Event.infrastructure.adapter.out.persistence.EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Event createEvent(Event event) {
        EventEntity entity = mapper.toEntity(event);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Event> findEventById() {
        List<EventEntity> entities = repository.findAll();
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Event updateEvent(Long id, Event event) {

    }


}
