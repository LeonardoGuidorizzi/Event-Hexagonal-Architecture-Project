package dev.devdreamer.Event.application.port.out;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.domain.Event;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository {
    Event save (Event event);

    Optional<Event> findEventById(String id);

    PageResult<Event> findAll(String status, String name, PageRequest request);


    boolean deleteById(String id);

    int deleteCancelledBefore(LocalDateTime cutoff);
}
