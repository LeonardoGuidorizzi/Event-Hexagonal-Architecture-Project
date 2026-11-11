package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.DeleteExpiredCancelledEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;

import java.time.Clock;
import java.time.LocalDateTime;

public class DeleteExpiredCancelledEventsImpl implements DeleteExpiredCancelledEventsUseCase {
    private final EventRepository repository;
    private Clock clock;// --> it will help in the tests
    private static final long RETENTION_MINUTES = 2;

    public DeleteExpiredCancelledEventsImpl(EventRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }
    @Override
    public int execute() {
        LocalDateTime cutoff = LocalDateTime.now(clock).minusMinutes(RETENTION_MINUTES);
        return repository.deleteCancelledBefore(cutoff);
    }
}
