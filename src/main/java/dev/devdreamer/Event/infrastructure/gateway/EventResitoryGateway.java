package dev.devdreamer.Event.infrastructure.gateway;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.gateway.EventGateway;
import dev.devdreamer.Event.infrastructure.mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.persistence.EventEntity;
import dev.devdreamer.Event.infrastructure.persistence.EventRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResitoryGateway implements EventGateway {
    private final EventRepository repository;
    private final EventMapper mapper;

    public EventResitoryGateway(EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Event createEvent(Event event) {
        EventEntity entity = mapper.toEntity(event);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Event> findEvent() {
        List<EventEntity> entities = repository.findAll();
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
